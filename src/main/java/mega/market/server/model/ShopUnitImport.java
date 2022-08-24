package mega.market.server.model;

import lombok.Data;
import javax.validation.constraints.*;
import java.util.UUID;

@Data
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
