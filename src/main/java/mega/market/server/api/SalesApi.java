package mega.market.server.api;

import mega.market.server.model.ShopUnitStatisticResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


import javax.validation.Valid;
import javax.validation.constraints.*;
import java.time.Instant;

@Validated
public interface SalesApi {

    @RequestMapping(value = "/sales", produces = {"application/json"}, method = RequestMethod.GET)
    ResponseEntity<ShopUnitStatisticResponse> salesGet(@NotNull @Valid @RequestParam(value = "date", required = true) Instant date);

}

