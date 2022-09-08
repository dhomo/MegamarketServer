package mega.market.server.dao;

import mega.market.server.domain.ShopUnit;
import mega.market.server.DTO.ShopUnitType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.history.RevisionRepository;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

public interface ShopUnitRepository extends JpaRepository<ShopUnit, UUID>, RevisionRepository<ShopUnit, UUID, Long> {

    @Query(value = """
            WITH RECURSIVE children AS(
                select id, price, type from shop_unit where id = ?1
                UNION select e.id, e.price, e.type from shop_unit e inner join children s on s.id = e.parent_id
            ) select avg(price) from children where id <> ?1 AND type = 0
            """, nativeQuery = true)
    Long findAverage(UUID id);

    Set<ShopUnit> findByDateBetweenAndType(Instant start, Instant end, ShopUnitType type);
}
