package mega.market.server.dao;

import mega.market.server.model.ShopUnit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface shopUnitRepository extends JpaRepository<ShopUnit, UUID> {

}
