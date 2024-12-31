package bitcopark.library.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class BookTitleNotFoundException extends RuntimeException {

    public BookTitleNotFoundException() {
        super();
    }

    public BookTitleNotFoundException(String message) {
        super(message);
    }

    public BookTitleNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public BookTitleNotFoundException(Throwable cause) {
        super(cause);
    }
}
