package mega.market.server.configuration;

import mega.market.server.exception.AppException;
import mega.market.server.model.ErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.web.HttpMediaTypeException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;



@RestControllerAdvice
public class ErrorHandlingControllerAdvice {

    @ExceptionHandler({MethodArgumentNotValidException.class, HttpMessageConversionException.class, HttpMediaTypeException.class})
    public ResponseEntity handleException400(Exception exception) {
        return handleException(new AppException(HttpStatus.BAD_REQUEST, "Validation Failed"));
    }

    @ExceptionHandler({AppException.class})
    public ResponseEntity handleException(AppException exception) {
        ErrorDto error = new ErrorDto(exception.getCode().value(), exception.getMessage());
        return new ResponseEntity(error, exception.getCode());
    }
}
