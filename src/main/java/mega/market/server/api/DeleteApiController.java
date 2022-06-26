package mega.market.server.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import mega.market.server.service.ShopUnitService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2022-06-22T11:11:10.456Z[GMT]")
@RestController
public class DeleteApiController implements DeleteApi {

    private static final Logger log = LoggerFactory.getLogger(DeleteApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;
    private final ShopUnitService shopUnitService;

    @Autowired
    public DeleteApiController(ObjectMapper objectMapper, HttpServletRequest request, ShopUnitService shopUnitService) {
        this.objectMapper = objectMapper;
        this.request = request;
        this.shopUnitService = shopUnitService;
    }

    public ResponseEntity<Void> deleteIdDelete(String id) {
        try {
            UUID uuid = UUID.fromString(id);
            shopUnitService.deleteShopUnit(uuid);
        } catch (UnsupportedOperationException ex) {
            return new ResponseEntity("{\n  \"code\": 404,\n  \"message\": \"Item not found\"\n}", HttpStatus.BAD_REQUEST);
        }
        catch (Exception e) {
            return new ResponseEntity("{\n  \"code\": 400,\n  \"message\": \"Validation Failed\"\n}", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(HttpStatus.OK);
    }

}
