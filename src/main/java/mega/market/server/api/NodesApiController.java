package mega.market.server.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import mega.market.server.model.ShopUnit;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.*;
import java.io.IOException;
import java.util.UUID;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2022-06-22T11:11:10.456Z[GMT]")
@RestController
public class NodesApiController implements NodesApi {

    private static final Logger log = LoggerFactory.getLogger(NodesApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @org.springframework.beans.factory.annotation.Autowired
    public NodesApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<ShopUnit> nodesIdGet(@Parameter(in = ParameterIn.PATH, description = "Идентификатор элемента", required=true, schema=@Schema()) @PathVariable("id") UUID id) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<ShopUnit>(objectMapper.readValue("{\n  \"id\" : \"3fa85f64-5717-4562-b3fc-2c963f66a111\",\n  \"name\" : \"Категория\",\n  \"type\" : \"CATEGORY\",\n  \"date\" : \"2022-05-28T21:12:01.000Z\",\n  \"price\" : 6,\n  \"children\" : [ {\n    \"name\" : \"Оффер 1\",\n    \"id\" : \"3fa85f64-5717-4562-b3fc-2c963f66a222\",\n    \"price\" : 4,\n    \"date\" : \"2022-05-28T21:12:01.000Z\",\n    \"type\" : \"OFFER\",\n    \"parentId\" : \"3fa85f64-5717-4562-b3fc-2c963f66a111\"\n  }, {\n    \"name\" : \"Подкатегория\",\n    \"type\" : \"CATEGORY\",\n    \"id\" : \"3fa85f64-5717-4562-b3fc-2c963f66a333\",\n    \"date\" : \"2022-05-26T21:12:01.000Z\",\n    \"parentId\" : \"3fa85f64-5717-4562-b3fc-2c963f66a111\",\n    \"price\" : 8,\n    \"children\" : [ {\n      \"name\" : \"Оффер 2\",\n      \"id\" : \"3fa85f64-5717-4562-b3fc-2c963f66a444\",\n      \"parentId\" : \"3fa85f64-5717-4562-b3fc-2c963f66a333\",\n      \"date\" : \"2022-05-26T21:12:01.000Z\",\n      \"price\" : 8,\n      \"type\" : \"OFFER\"\n    } ]\n  } ]\n}", ShopUnit.class), HttpStatus.OK);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<ShopUnit>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<ShopUnit>(HttpStatus.NOT_IMPLEMENTED);
    }

}
