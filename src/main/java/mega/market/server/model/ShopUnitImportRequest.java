package mega.market.server.model;

import lombok.Data;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.time.OffsetDateTime;
import java.util.*;

/**
 * ShopUnitImportRequest
 */

@Data
public class ShopUnitImportRequest {
    @Valid
    private Set<ShopUnitImport> items;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @NotNull // почему-то не работает
    @Valid
    private OffsetDateTime updateDate;
}
