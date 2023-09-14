package Demo.CRUDoperations.erros;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestControllerAdvice
public class ExceptionHandlers {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NoSuchElementException.class)
    public String noData(Exception e){
        return "id doesn't exists";

    }
}
