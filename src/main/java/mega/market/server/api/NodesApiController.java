package mega.market.server.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import mega.market.server.model.ShopUnit;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
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

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2022-06-22T11:11:10.456Z[GMT]")
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

    public ResponseEntity<ShopUnit> nodesIdGet(@Parameter(in = ParameterIn.PATH, description = "Идентификатор элемента", required = true, schema = @Schema()) @PathVariable("id") UUID id) {
        String accept = request.getHeader("Accept");
        try {
            if (accept == null || !accept.contains("application/json")) throw new RuntimeException();

        ShopUnit shopUnit = shopUnitService.getShopUnit(id);

            if (shopUnit != null) {
                return new ResponseEntity<ShopUnit>(shopUnit, HttpStatus.OK);
            } else {
                return new ResponseEntity("{\n  \"code\": 404,\n  \"message\": \"Item not found\"\n}", HttpStatus.NOT_FOUND);
            }
        }
    }

}
