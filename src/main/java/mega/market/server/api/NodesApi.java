package mega.market.server.api;

import mega.market.server.model.ShopUnit;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Validated
public interface NodesApi {

    @RequestMapping(value = "/nodes/{id}",
            produces = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<ShopUnit> nodesIdGet(@PathVariable("id") String id);

}

