package esempio_security.esempio_security.exc;


import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice

public class ToDoAppExceptionHadler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String,String> handlerException(MethodArgumentNotValidException ex){
        Map<String,String> errorMap = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(fieldError -> errorMap.put(fieldError.getField(),
                fieldError.getDefaultMessage()));
        return errorMap;
    }

    @ExceptionHandler({DataIntegrityViolationException.class})
    public String handlerDuplicateEntryException(DataIntegrityViolationException ex){
        return "Dati già presenti nel database";
    }

}
