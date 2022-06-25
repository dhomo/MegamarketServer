package mega.market.server.service;

import mega.market.server.dao.ShopUnitRepository;
import mega.market.server.model.ShopUnit;
import mega.market.server.model.ShopUnitImport;
import mega.market.server.model.ShopUnitImportRequest;
import mega.market.server.model.ShopUnitType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ShopUnitService {

    private final ShopUnitRepository shopUnitRepository;

    public ShopUnitService(ShopUnitRepository shopUnitRepository) {
        this.shopUnitRepository = shopUnitRepository;
    }

    public void deleteShopUnit(UUID id) {
        shopUnitRepository.deleteById(id);
    }

    ;

    public ShopUnit getShopUnit(UUID id) {
        shopUnitRepository.findById(id);
        return null;
    }

    //    @Transactional
    public boolean imports(ShopUnitImportRequest shopUnitImportRequest) {
        try {
            OffsetDateTime updateDate = shopUnitImportRequest.getUpdateDate();
            // сначала пачкой добавляем и обновляем все что получили, не вникая в структуру
            // иначе можно нарваться на добавление чилдрена раньше парента
            Set<ShopUnit> shopUnitSet = new HashSet<ShopUnit>();
            for (ShopUnitImport shopUnitImport : shopUnitImportRequest.getItems()) {
                ShopUnit shopUnit = shopUnitRepository.findById(shopUnitImport.getId()).orElse(new ShopUnit());
//                у категорий поле price должно содержать null
                if (shopUnitImport.getType() == ShopUnitType.CATEGORY && shopUnitImport.getPrice() != null){
                    throw new Exception();
                }
//                цена товара не может быть null и должна быть больше либо равна нулю.
                if (shopUnitImport.getType() == ShopUnitType.OFFER && (shopUnitImport.getPrice() == null || shopUnitImport.getPrice() < 0)){
                    throw new Exception();
                }
                //  Изменение типа элемента с товара на категорию или с категории на товар не допускается.
                if (shopUnit.getType() != null && shopUnit.getType() != shopUnitImport.getType()) {
                    throw new Exception();
                }
                shopUnit.setPrice(shopUnitImport.getPrice());
                shopUnit.setId(shopUnitImport.getId());
                shopUnit.setType(shopUnitImport.getType());
                shopUnit.setName(shopUnitImport.getName());
                shopUnit.setDate(updateDate);

                shopUnitSet.add(shopUnit);
            }
            shopUnitRepository.saveAllAndFlush(shopUnitSet);


            // а сейчас начинаем разираться со cтруктурой
            Set<ShopUnit> shopRootCategorySet = new HashSet<ShopUnit>();
            for (ShopUnitImport shopUnitImport : shopUnitImportRequest.getItems()) {
                // TODO: возможно для скорости лучше shopUnitSet превратить в Map и искать в нем
                ShopUnit shopUnit = shopUnitRepository.findById(shopUnitImport.getId()).orElseThrow();

                // обрабатываем изменение парента
                ShopUnit oldParent = shopUnit.getParent();
                ShopUnit newParent = null;
                if (shopUnitImport.getParentId() != null) {
                    newParent = shopUnitRepository.findById(shopUnitImport.getParentId()).orElseThrow();
                }

                if (isParentEquals(oldParent, newParent)) {
                    shopRootCategorySet.add(recalcPrice(oldParent, updateDate));
                } else {
                    shopUnit.setParent(newParent);
                    if (newParent != null) {
                        //TODO проверить, возможно это лишнее
                        newParent.addChildrenItem(shopUnit);
                    }
                    if (oldParent != null) {
                        oldParent.getChildren().remove(shopUnit);
                    }
                    shopRootCategorySet.add(recalcPrice(newParent, updateDate));
                    shopRootCategorySet.add(recalcPrice(oldParent, updateDate));
                }
            }
            // может попасть null , убираем его
            shopRootCategorySet.remove(null);
            shopUnitRepository.saveAll(shopRootCategorySet);

        } catch (Exception e) {
            return false;
        }
        return true;
    }

    // взвращает самого верхнего в иерархии
    public ShopUnit recalcPrice(ShopUnit category, OffsetDateTime updateDate) {
        if (category == null) return null;

//      отфильтруем пустые категории
        Set<ShopUnit> nullPriceFilteredSet = category.getChildren().stream().
                filter((s) -> s.getPrice() != null).collect(Collectors.toSet());
        if (nullPriceFilteredSet.size() > 0) {
            long sum = 0;
            for (ShopUnit children : nullPriceFilteredSet) {
                sum += children.getPrice();
            }
            category.setPrice(sum / nullPriceFilteredSet.size());
        }
        category.setDate(updateDate);
        if (category.getParent() != null) {
            return recalcPrice(category.getParent(), updateDate);
        } else {
            return category;
        }
    }

    private boolean isParentEquals(ShopUnit oldParent, ShopUnit newParent) {
        return (oldParent == null && newParent == null) ||
                (oldParent != null && oldParent.equals(newParent));
    }
}
