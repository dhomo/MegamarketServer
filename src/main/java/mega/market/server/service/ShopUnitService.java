package mega.market.server.service;

import mega.market.server.dao.ShopUnitRepository;
import mega.market.server.model.ShopUnit;
import mega.market.server.model.ShopUnitImport;
import mega.market.server.model.ShopUnitImportRequest;
import mega.market.server.model.ShopUnitType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ShopUnitService {

    private final ShopUnitRepository shopUnitRepository;

    public ShopUnitService(ShopUnitRepository shopUnitRepository) {
        this.shopUnitRepository = shopUnitRepository;
    }

    @Transactional
    public void deleteShopUnit(UUID id) {
        ShopUnit shopUnit = shopUnitRepository.findById(id).orElseThrow(UnsupportedOperationException::new);

        if (shopUnit.getParentId() != null) {
            ShopUnit parent = shopUnitRepository.findById(shopUnit.getParentId()).orElseThrow();
            parent.getChildren().remove(shopUnit);
            recalcPrice(parent, null);
            shopUnitRepository.deleteById(id);
        } else {
            shopUnitRepository.deleteById(id);
        }
    }

    public ShopUnit getShopUnit(UUID id) {
        return shopUnitRepository.findById(id).orElse(null);
    }

    @Transactional
    public void imports(ShopUnitImportRequest shopUnitImportRequest) throws Exception {
        // пока не починю валидацию даты NOT_NULL пусть будет так
        Instant updateDate = Optional.of(shopUnitImportRequest.getUpdateDate()).orElseThrow();

        // сначала пачкой добавляем и обновляем все что получили, не вникая в структуру
        // иначе можно нарваться на добавление чилдрена раньше парента
        Map<UUID, ShopUnit> shopUnitSet = new HashMap<>();
        Map<UUID, UUID> oldParentUUIDs = new HashMap<>();
        for (ShopUnitImport shopUnitImport : shopUnitImportRequest.getItems()) {
            ShopUnit shopUnit = shopUnitRepository.findById(shopUnitImport.getId()).orElse(new ShopUnit());
            if (shopUnitImport.getType() == ShopUnitType.CATEGORY && shopUnitImport.getPrice() != null) {
                throw new Exception("у категорий поле price должно содержать null");
            }
            if (shopUnitImport.getType() == ShopUnitType.OFFER && (shopUnitImport.getPrice() == null || shopUnitImport.getPrice() < 0)) {
                throw new Exception("цена товара не может быть null и должна быть больше либо равна нулю.");
            }
            if (shopUnit.getType() != null && shopUnit.getType() != shopUnitImport.getType()) {
                throw new Exception("Изменение типа элемента с товара на категорию или с категории на товар не допускается.");
            }
            shopUnit.setPrice(shopUnitImport.getPrice());
            shopUnit.setId(shopUnitImport.getId());
            shopUnit.setType(shopUnitImport.getType());
            shopUnit.setName(shopUnitImport.getName());
            shopUnit.setDate(updateDate);
            // сохраняем UUID старого парента
            oldParentUUIDs.put(shopUnitImport.getId(), shopUnit.getParentId());
            shopUnit.setParentId(shopUnitImport.getParentId());

            shopUnitSet.put(shopUnitImport.getId(), shopUnit);
        }
        shopUnitRepository.saveAll(shopUnitSet.values());

        // а сейчас начинаем разираться со cтруктурой
        for (ShopUnit localshopUnit : shopUnitSet.values()) {
            ShopUnit shopUnit = shopUnitRepository.findById(localshopUnit.getId()).orElseThrow();

            if (isParentEquals(oldParentUUIDs.get(shopUnit.getId()), shopUnit.getParentId())) {
//              парент не изменился
                if (shopUnit.getParentId() != null) {
                    ShopUnit oldParent = shopUnitRepository.findById(shopUnit.getParentId()).orElseThrow();
                    recalcPrice(oldParent, updateDate);
                }
            } else {
//              обрабатываем изменение парента
                if (oldParentUUIDs.get(shopUnit.getId()) != null) {
                    ShopUnit oldParent = shopUnitRepository.findById(oldParentUUIDs.get(shopUnit.getId())).orElseThrow();
                    oldParent.getChildren().remove(shopUnit);
                    recalcPrice(oldParent, updateDate);
                }

                if (shopUnit.getParentId() != null) {
                    ShopUnit newParent = shopUnitRepository.findById(shopUnit.getParentId()).orElseThrow();
                    if (newParent.getType() == ShopUnitType.OFFER) {
                        throw new RuntimeException("родителем товара или категории может быть только категория");
                    }
                    newParent.addChildrenItem(shopUnit);
                    recalcPrice(newParent, updateDate);
                }
            }
        }
    }

    // взвращает самого верхнего в иерархии
    public void recalcPrice(ShopUnit category, Instant updateDate) {
        if (category == null) return;

        // напролом и бурелом, разбиратсья в магии гибернейта некогда
        // очень очень грязный хак что времени осталось пару часов
        long avg = 0;
        // от пустой катгории прилетает null
        try {
             avg = shopUnitRepository.findAverage(category.getId());
        } catch (Exception e){}        

        category.setPrice(avg);

        if (updateDate != null) {
            category.setDate(updateDate);
        }
        if (category.getParentId() != null) {
            recalcPrice(shopUnitRepository.findById(category.getParentId()).orElseThrow(), updateDate);
        }
        shopUnitRepository.save(category);
    }

    private boolean isParentEquals(UUID oldParent, UUID newParent) {
        return (oldParent == null && newParent == null) ||
                (oldParent != null && oldParent.equals(newParent));
    }
}
