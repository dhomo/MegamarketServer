package mega.market.server.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.time.Instant;
import java.util.UUID;

@Data
@Builder
public class ShopUnitStatisticUnit {

    @NotNull
    private UUID id;

    @NotNull
    private String name;

    private UUID parentId;

    @NotNull
    private ShopUnitType type;

    private Long price;

    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Instant date;

}
