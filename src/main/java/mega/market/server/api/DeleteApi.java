package mega.market.server.api;


import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.constraints.*;

@Validated
public interface DeleteApi {

    @RequestMapping(value = "/delete/{id}", produces = {"application/json"}, method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteIdDelete(@PathVariable("id") String id);

}

