package mega.market.server.model;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;
import java.time.Instant;
import java.util.UUID;

/**
 * ShopUnitStatisticUnit
 */
@Validated
@Data
public class ShopUnitStatisticUnit   {
  @Valid
  @NotNull
  private UUID id;

  @Valid
  @NotNull
  private String name;

  @Valid
  private UUID parentId;

  @Valid
  @NotNull
  private ShopUnitType type;

  @Valid
  private Long price;

  @Valid
  @NotNull
  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private Instant date;

}
