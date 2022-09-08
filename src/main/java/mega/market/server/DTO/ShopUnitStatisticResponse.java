package mega.market.server.DTO;

import lombok.Data;
import java.util.Set;

@Data
public class ShopUnitStatisticResponse {
    private Set<ShopUnitStatisticUnit> items;
}
