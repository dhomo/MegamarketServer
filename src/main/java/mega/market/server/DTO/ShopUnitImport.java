package mega.market.server.DTO;

import lombok.Data;
import mega.market.server.validators.ValidShopUnitImport;

import javax.validation.constraints.*;
import java.util.UUID;

@Data
@ValidShopUnitImport
public class ShopUnitImport {

    @NotNull
    private UUID id;

    @NotNull
    private String name;

    private UUID parentId;

    @NotNull
    private ShopUnitType type;

    private Long price;
}
