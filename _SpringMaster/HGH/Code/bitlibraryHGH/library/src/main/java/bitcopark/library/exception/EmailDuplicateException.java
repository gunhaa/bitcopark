package bitcopark.library.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class EmailDuplicateException extends RuntimeException {

    public EmailDuplicateException() {
        super();
    }

    public EmailDuplicateException(String message) {
        super(message);
    }

    public EmailDuplicateException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmailDuplicateException(Throwable cause) {
        super(cause);
    }
}
