package mega.market.server.model;

import lombok.Data;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import java.util.Set;


/**
 * ShopUnitStatisticResponse
 */
@Validated
@Data
public class ShopUnitStatisticResponse {
    @Valid
    private Set<ShopUnitStatisticUnit> items;
}
