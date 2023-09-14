package Demo.CRUDoperations.erros;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.NoSuchElementException;

@ControllerAdvice
public class ExceptionHandlers {
    @ExceptionHandler(NoSuchElementException.class)
    public String noData(Exception e){
        return "id doesn't exists";

    }
}
"