package mega.market.server.dao;

import mega.market.server.model.ShopUnit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface ShopUnitRepository extends JpaRepository<ShopUnit, UUID> {
    @Query(value = "WITH RECURSIVE children AS(\n" +
            "    select id, price, type from shop_unit where id = ?1\n" +
            "    UNION select e.id, e.price, e.type from shop_unit e inner join children s on s.id = e.parent_id\n" +
            ") select avg(price) from children where id <> ?1 AND type = 0",
            nativeQuery = true)
    long findAverage(UUID id);
}
