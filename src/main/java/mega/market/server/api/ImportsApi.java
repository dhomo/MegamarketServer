package mega.market.server.api;

import mega.market.server.model.ShopUnitImportRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

@Validated
public interface ImportsApi {

    @RequestMapping(value = "/imports",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.POST)
    ResponseEntity<Void> importsPost(@Valid @RequestBody ShopUnitImportRequest body);

}

