package mega.market.server.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import mega.market.server.model.ShopUnitStatisticResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.time.Instant;
import java.util.Set;
import java.util.UUID;

import static java.util.stream.Collectors.toSet;

@RestController
@RequiredArgsConstructor
public class NodeApiController {

    private static final Logger log = LoggerFactory.getLogger(NodeApiController.class);
    private final ObjectMapper objectMapper;
    private final HttpServletRequest request;
    private final ShopUnitService shopUnitService;


    @GetMapping(path = "/node/{id}/statistic",
            produces = {"application/json"})
    public ResponseEntity<ShopUnitStatisticResponse> nodeIdStatisticGet(@PathVariable("id") UUID id,
                                                                        @Valid @RequestParam(value = "dateStart", required = false) Instant dateStart,
                                                                        @Valid @RequestParam(value = "dateEnd", required = false) Instant dateEnd) {

        Set<ShopUnit> items = shopUnitService.nodeIdStatisticGet(id, dateStart, dateEnd);

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

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
