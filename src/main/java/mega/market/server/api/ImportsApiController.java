package mega.market.server.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import mega.market.server.model.ShopUnitImportRequest;
import mega.market.server.service.ShopUnitService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.constraints.*;


@RestController
@RequiredArgsConstructor
public class ImportsApiController implements ImportsApi {

    private static final Logger log = LoggerFactory.getLogger(ImportsApiController.class);
    private final ObjectMapper objectMapper;
    private final HttpServletRequest request;
    private final ShopUnitService shopUnitService;


    public ResponseEntity importsPost(@Valid @RequestBody ShopUnitImportRequest body) {
        try {
            shopUnitService.imports(body);
        } catch (Exception e){
            return new ResponseEntity("{\n  \"code\": 400,\n  \"message\": \"Validation Failed\"\n}", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(HttpStatus.OK);
    }

}
