package mega.market.server.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import mega.market.server.model.ShopUnit;
import mega.market.server.service.ShopUnitService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.UUID;

@RestController
public class NodesApiController implements NodesApi {

    private static final Logger log = LoggerFactory.getLogger(NodesApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    private final ShopUnitService shopUnitService;

    @org.springframework.beans.factory.annotation.Autowired
    public NodesApiController(ObjectMapper objectMapper, HttpServletRequest request, ShopUnitService shopUnitService) {
        this.objectMapper = objectMapper;
        this.request = request;
        this.shopUnitService = shopUnitService;
    }

    public ResponseEntity<ShopUnit> nodesIdGet(@PathVariable("id") String id) {
        try {
            UUID uuid = UUID.fromString(id);
            ShopUnit shopUnit = shopUnitService.getShopUnit(uuid);

            if (shopUnit != null) {
                return new ResponseEntity<ShopUnit>(shopUnit, HttpStatus.OK);
            } else {
                return new ResponseEntity("{\n  \"code\": 404,\n  \"message\": \"Item not found\"\n}", HttpStatus.NOT_FOUND);
            }
        }
        catch (Exception e) {
            return new ResponseEntity("{\n  \"code\": 400,\n  \"message\": \"Validation Failed\"\n}", HttpStatus.BAD_REQUEST);
        }
    }

}
