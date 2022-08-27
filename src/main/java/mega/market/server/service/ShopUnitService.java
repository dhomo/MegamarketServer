package mega.market.server.service;

import lombok.RequiredArgsConstructor;
import mega.market.server.dao.ShopUnitRepository;
import mega.market.server.exception.AppException;
import mega.market.server.model.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.*;

import static mega.market.server.exception.AppException.*;

@Service
@RequiredArgsConstructor
public class ShopUnitService {

    private final ShopUnitRepository shopUnitRepository;


    public Set<ShopUnitStatisticUnit> sales(Instant date){
        return shopUnitRepository.sales(date);
    }

    @Transactional
    public void deleteShopUnit(UUID id) {
        ShopUnit shopUnit = shopUnitRepository.findById(id).orElseThrow(appExceptionNotFound());

        if (shopUnit.getParentId() != null) {
            ShopUnit parent = shopUnitRepository.findById(shopUnit.getParentId()).orElseThrow(appExceptionValidationFailed());
            parent.getChildren().remove(shopUnit);
            recalcPrice(parent, null);
            shopUnitRepository.deleteById(id);
        } else {
            shopUnitRepository.deleteById(id);
        }
    }

    public ShopUnit getShopUnit(UUID id) {
        return shopUnitRepository.findById(id).orElseThrow(appExceptionNotFound());
    }

    @Transactional
    public void imports(ShopUnitImportRequest shopUnitImportRequest) throws Exception {
        Instant updateDate = shopUnitImportRequest.getUpdateDate();

        // сначала пачкой добавляем/обновляем все что получили, не указывая parendId
        // иначе можно нарваться на добавление чилдрена раньше парента
        Map<UUID, ShopUnit> shopUnitSet = new HashMap<>();
        Map<UUID, UUID> oldParentUUIDs = new HashMap<>();
        for (ShopUnitImport shopUnitImport : shopUnitImportRequest.getItems()) {
            ShopUnit shopUnit = shopUnitRepository.findById(shopUnitImport.getId()).orElse(new ShopUnit());
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
