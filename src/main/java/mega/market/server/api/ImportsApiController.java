package mega.market.server.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import mega.market.server.exception.AppException;
import mega.market.server.model.ShopUnitImportRequest;
import mega.market.server.service.ShopUnitService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;


@RestController
@RequiredArgsConstructor
public class ImportsApiController {

    private static final Logger log = LoggerFactory.getLogger(ImportsApiController.class);
    private final ObjectMapper objectMapper;
    private final HttpServletRequest request;
    private final ShopUnitService shopUnitService;

    @PostMapping("/imports")
    public ResponseEntity importsPost(@Valid @RequestBody ShopUnitImportRequest body) throws Exception {
        try {
            shopUnitService.imports(body);
        } catch (Exception e) {
            throw new AppException(HttpStatus.BAD_REQUEST, "Validation Failed");
        }
        return new ResponseEntity(HttpStatus.OK);
    }

}
