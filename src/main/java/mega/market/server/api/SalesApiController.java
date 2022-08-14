package mega.market.server.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import mega.market.server.model.ShopUnitStatisticResponse;
import mega.market.server.model.ShopUnitStatisticUnit;
import mega.market.server.service.ShopUnitService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.*;
import java.io.IOException;
import java.time.Instant;
import java.util.Set;

@RestController
public class SalesApiController implements SalesApi {

    private static final Logger log = LoggerFactory.getLogger(SalesApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;
    private final ShopUnitService shopUnitService;

    @org.springframework.beans.factory.annotation.Autowired
    public SalesApiController(ObjectMapper objectMapper, HttpServletRequest request, ShopUnitService shopUnitService) {
        this.objectMapper = objectMapper;
        this.request = request;
        this.shopUnitService = shopUnitService;
    }

    public ResponseEntity<ShopUnitStatisticResponse> salesGet(@NotNull @Valid @RequestParam(value = "date", required = true) Instant date) {
        try {
            Set<ShopUnitStatisticUnit> items = shopUnitService.sales(date);
            ShopUnitStatisticResponse response = new ShopUnitStatisticResponse();
            response.setItems(items);

            return new ResponseEntity<ShopUnitStatisticResponse>(objectMapper.readValue("{\n  \"items\" : [ {\n    \"id\" : \"3fa85f64-5717-4562-b3fc-2c963f66a444\",\n    \"name\" : \"Оффер\",\n    \"date\" : \"2022-05-28T21:12:01.000Z\",\n    \"parentId\" : \"3fa85f64-5717-4562-b3fc-2c963f66a333\",\n    \"price\" : 234,\n    \"type\" : \"OFFER\"\n  }, {\n    \"id\" : \"3fa85f64-5717-4562-b3fc-2c963f66a444\",\n    \"name\" : \"Оффер\",\n    \"date\" : \"2022-05-28T21:12:01.000Z\",\n    \"parentId\" : \"3fa85f64-5717-4562-b3fc-2c963f66a333\",\n    \"price\" : 234,\n    \"type\" : \"OFFER\"\n  } ]\n}", ShopUnitStatisticResponse.class), HttpStatus.NOT_IMPLEMENTED);
        } catch (IOException e) {
            return new ResponseEntity("{\n  \"code\": 400,\n  \"message\": \"Validation Failed\"\n}", HttpStatus.BAD_REQUEST);
        }
    }

}
