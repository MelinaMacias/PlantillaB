package ec.sasf.sasfpruebamelinamacias.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class HandlerException {
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(BadRequestException.class)
    public ErrorMessage handlerBadRequestException(HttpServletRequest request, Exception exception){
        return new ErrorMessage(exception.getMessage(),null, HttpStatus.NOT_FOUND.value(), request.getRequestURI());
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(BadCredentialsException.class)
    public ErrorMessage handlerBadCredentials(HttpServletRequest request, Exception exception){
        return new ErrorMessage(exception.getMessage(),null, HttpStatus.FORBIDDEN.value(), request.getRequestURI());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(
            org.springframework.web.bind.MethodArgumentNotValidException.class
    )

    public ErrorMessage handlerMethodArgumentNotValidException(HttpServletRequest request,
                                                               MethodArgumentNotValidException exception){

        BindingResult bindingResult = exception.getBindingResult();

        String message = "Ha ocurrido un error al validar los campos de "+ bindingResult.getObjectName();
        List<FieldError> errors = bindingResult.getFieldErrors();
        List<String> listErrors = new ArrayList<String>();

        errors.forEach(e -> listErrors.add(e.getDefaultMessage()));

        return new ErrorMessage(message,listErrors, HttpStatus.BAD_REQUEST.value(), request.getRequestURI());
    }


}
