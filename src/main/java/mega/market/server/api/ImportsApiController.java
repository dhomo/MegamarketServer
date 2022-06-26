package mega.market.server.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import mega.market.server.model.ShopUnitImportRequest;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import mega.market.server.service.ShopUnitService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.*;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2022-06-22T11:11:10.456Z[GMT]")
@RestController
public class ImportsApiController implements ImportsApi {

    private static final Logger log = LoggerFactory.getLogger(ImportsApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    private final ShopUnitService shopUnitService;

    @org.springframework.beans.factory.annotation.Autowired
    public ImportsApiController(ObjectMapper objectMapper, HttpServletRequest request, ShopUnitService shopUnitService) {
        this.objectMapper = objectMapper;
        this.request = request;
        this.shopUnitService = shopUnitService;
    }

    public ResponseEntity importsPost(@Parameter(in = ParameterIn.DEFAULT, description = "", schema=@Schema()) @Valid @RequestBody ShopUnitImportRequest body) {
        String accept = request.getHeader("Accept");
        try {
            shopUnitService.imports(body);
        } catch (Exception e){
            return new ResponseEntity("{\n  \"code\": 400,\n  \"message\": \"Validation Failed\"\n}", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(HttpStatus.OK);
    }

}
