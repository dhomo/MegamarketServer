package mega.market.server.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import mega.market.server.domain.ShopUnit;
import mega.market.server.DTO.ShopUnitStatisticResponse;
import mega.market.server.DTO.ShopUnitStatisticUnit;
import mega.market.server.service.ShopUnitService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.*;
import java.time.Instant;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

@RestController
@RequiredArgsConstructor
public class SalesApiController {

    private static final Logger log = LoggerFactory.getLogger(SalesApiController.class);
    private final ObjectMapper objectMapper;
    private final HttpServletRequest request;
    private final ShopUnitService shopUnitService;


    @GetMapping(path = "/sales",
            produces = {"application/json"})
    public ResponseEntity<ShopUnitStatisticResponse> salesGet(@NotNull @Valid @RequestParam(value = "date", required = true) Instant date) {

        Set<ShopUnit> items = shopUnitService.sales(date);

        ShopUnitStatisticResponse response = new ShopUnitStatisticResponse();
        response.setItems(items.stream().map(s -> ShopUnitStatisticUnit.builder()
                        .id(s.getId())
                        .name(s.getName())
                        .parentId(s.getParentId())
                        .type(s.getType())
                        .price(s.getPrice())
                        .date(s.getDate())
                        .build())
                .collect(toSet()));

        return new ResponseEntity<ShopUnitStatisticResponse>(response, HttpStatus.OK);
    }

}
