package mega.market.server.api;

import mega.market.server.model.ShopUnitStatisticResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.time.Instant;
import java.util.UUID;

@Validated
public interface NodeApi {

    @RequestMapping(value = "/node/{id}/statistic",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    ResponseEntity<ShopUnitStatisticResponse> nodeIdStatisticGet(@PathVariable("id") UUID id,
                                                                 @Valid @RequestParam(value = "dateStart", required = false) Instant dateStart,
                                                                 @Valid @RequestParam(value = "dateEnd", required = false) Instant dateEnd);

}

