package mega.market.server.service;

import lombok.RequiredArgsConstructor;
import mega.market.server.dao.ShopUnitRepository;
import mega.market.server.model.*;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

import static mega.market.server.exception.AppException.*;

@Service
@RequiredArgsConstructor
public class ShopUnitService {

    private final ShopUnitRepository shopUnitRepository;


    public Set<ShopUnit> sales(Instant date) {
        return shopUnitRepository.findByDateBetween(date.minus(24, ChronoUnit.HOURS), date);
    }

    @Transactional
    public void deleteShopUnit(UUID id) {
        ShopUnit shopUnit = shopUnitRepository.findById(id).orElseThrow(appExceptionNotFound());

        if (shopUnit.getParentId() != null) {
            ShopUnit parent = shopUnitRepository.findById(shopUnit.getParentId()).orElseThrow(appExceptionValidationFailed());
            parent.getChildren().remove(shopUnit);
            updateParentsRecursively(parent.getParentId(), null);
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

        Map<UUID, ShopUnitImport> importMap = shopUnitImportRequest.getItems().stream()
                .collect(Collectors.toMap(ShopUnitImport::getId, c -> c));

        while (!importMap.isEmpty()) {
            UUID id = importMap.keySet().stream().findFirst().get();
            id = findUpperShopUnit(importMap, id);
            var shopUnitImport = importMap.get(id);
            importMap.remove(id);

            // начинаем импорт
            var shopUnit = shopUnitRepository.findById(id).orElse(new ShopUnit());
            shopUnit.setId(shopUnitImport.getId());
            shopUnit.setPrice(shopUnitImport.getPrice());
            shopUnit.setName(shopUnitImport.getName());
            shopUnit.setDate(updateDate);
            if (shopUnit.getType() != null && shopUnit.getType() != shopUnitImport.getType()) {
                throw new Exception("Изменение типа элемента с товара на категорию или с категории на товар не допускается.");
            }
            shopUnit.setType(shopUnitImport.getType());
            var oldParentId = shopUnit.getParentId();
            shopUnit.setParentId(shopUnitImport.getParentId());
            shopUnitRepository.saveAndFlush(shopUnit);

            updateParentsRecursively(shopUnit.getParentId(), updateDate);
            // если парент изменился, то старого тоже обрабатываем
            if (oldParentId != null && !oldParentId.equals(shopUnit.getParentId())) {
                updateParentsRecursively(oldParentId, updateDate);
            }
        }
    }

    /**
     * Рекурсивно пройтись по всем родительским категориям и обновить дату и среднюю цену
     *
     * @param parentId   Id родителя
     * @param updateDate дата обновления, если null то не меняем её (например при удалении менять не надо)
     */
    private void updateParentsRecursively(@Nullable UUID parentId, @Nullable Instant updateDate) {
        if (parentId == null) return;
        var parent = shopUnitRepository.findById(parentId).orElseThrow();

        if (parent.getType() != ShopUnitType.CATEGORY)
            throw new RuntimeException("родителем товара или категории может быть только категория");

        Long price = shopUnitRepository.findAverage(parent.getId());
        parent.setPrice(price);
        if (updateDate != null) parent.setDate(updateDate);
        shopUnitRepository.saveAndFlush(parent);

        updateParentsRecursively(parent.getParentId(), updateDate);
    }

    /**
     * ищем parentid во входных данных, если есть то для него делаем то же самое
     * если уперлись вверх то возвращаем его id;
     *
     * @param items Map всех оставшихся юнитов
     * @param id    id который мы пытаемся обработать, если у него есть родитель то переходим к нему
     * @return UUID фактически обработаного юнита
     */
    private UUID findUpperShopUnit(Map<UUID, ShopUnitImport> items, UUID id) {
        UUID parentId = items.get(id).getParentId();
        if (items.get(parentId) == null) return id; // родитель null или его нет в списке для импорта
        else return findUpperShopUnit(items, parentId); // переходим к родительскому юниту
    }

}
