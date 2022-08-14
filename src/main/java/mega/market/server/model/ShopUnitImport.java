package mega.market.server.model;

import lombok.Data;
import org.springframework.validation.annotation.Validated;
import javax.validation.constraints.*;
import java.util.UUID;

/**
 * ShopUnitImport
 */
@Validated
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
