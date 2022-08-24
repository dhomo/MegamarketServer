package mega.market.server.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import mega.market.server.exception.AppException;
import mega.market.server.model.ShopUnit;
import mega.market.server.service.ShopUnitService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class NodesApiController {

    private static final Logger log = LoggerFactory.getLogger(NodesApiController.class);
    private final ObjectMapper objectMapper;
    private final HttpServletRequest request;
    private final ShopUnitService shopUnitService;


    @GetMapping(path = "/nodes/{id}",
            produces = {"application/json"})
    public ResponseEntity<ShopUnit> nodesIdGet(@PathVariable("id") UUID id) {
        return new ResponseEntity<ShopUnit>(shopUnitService.getShopUnit(id), HttpStatus.OK);
    }

}
