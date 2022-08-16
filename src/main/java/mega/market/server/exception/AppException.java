package mega.market.server.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.function.Supplier;


public class AppException extends RuntimeException {

    @Getter private HttpStatus code;

    public AppException(HttpStatus code, String message) {
        super(message);
        this.code = code;
    }

    public static Supplier<AppException> appException(HttpStatus code, String message) {
        return () -> new AppException(code, message);
    }

}
