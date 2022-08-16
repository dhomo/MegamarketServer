package mega.market.server.model;

import lombok.Data;

import org.springframework.format.annotation.DateTimeFormat;
import javax.validation.Valid;
import javax.validation.constraints.*;
import java.time.Instant;
import java.util.*;

/**
 * ShopUnitImportRequest
 */

@Data
public class ShopUnitImportRequest {

    @Valid
    @NotNull
    private Set<ShopUnitImport> items;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @NotNull
    private Instant updateDate;
}
