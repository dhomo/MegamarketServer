package mega.market.server.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import mega.market.server.exception.AppException;
import mega.market.server.service.ShopUnitService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class DeleteApiController
{

    private static final Logger log = LoggerFactory.getLogger(DeleteApiController.class);
    private final ObjectMapper objectMapper;
    private final HttpServletRequest request;
    private final ShopUnitService shopUnitService;

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteIdDelete(@PathVariable("id") UUID id) {
        shopUnitService.deleteShopUnit(id);
        return new ResponseEntity(HttpStatus.OK);
    }

}
