package Demo.CRUDoperations.error;

import Demo.CRUDoperations.apiresponse.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;


@RestControllerAdvice
public class ExceptionHandlers {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NoSuchElementException.class)
    public String noData(RuntimeException e) {
        return "id doesn't exists";
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiResponse validation(MethodArgumentNotValidException exception) {
        Map<String, String> errors = new HashMap<>();
        exception.getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
        return new ApiResponse(HttpStatus.BAD_REQUEST.value(), errors, HttpStatus.BAD_REQUEST.getReasonPhrase(), false);
    }
}

