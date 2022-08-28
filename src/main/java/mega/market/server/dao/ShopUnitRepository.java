package mega.market.server.dao;

import mega.market.server.model.ShopUnit;
import mega.market.server.model.ShopUnitStatisticUnit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.Instant;
import java.util.Set;
import java.util.UUID;

public interface ShopUnitRepository extends JpaRepository<ShopUnit, UUID> {
    @Query(value = "WITH RECURSIVE children AS(\n" +
            "    select id, price, type from shop_unit where id = ?1\n" +
            "    UNION select e.id, e.price, e.type from shop_unit e inner join children s on s.id = e.parent_id\n" +
            ") select avg(price) from children where id <> ?1 AND type = 0",
            nativeQuery = true)
    Long findAverage(UUID id);

    @Query(value = "select id, name, parent_id, type, price, date from shop_unit where type = 0 and date between (date :date - interval '24 hour') and :date",
            nativeQuery = true)
    Set<ShopUnitStatisticUnit> sales(@Param("date") Instant date);
}
