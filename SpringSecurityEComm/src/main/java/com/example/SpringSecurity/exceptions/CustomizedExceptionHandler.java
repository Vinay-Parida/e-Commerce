package com.example.SpringSecurity.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@ControllerAdvice
@RestController
public class CustomizedExceptionHandler extends ResponseEntityExceptionHandler {

//    @ExceptionHandler(Exception.class)
//    public final ResponseEntity<Object> handleAllException(Exception e, WebRequest webRequest){
//        ExceptionResponse exceptionResponse=
//                new ExceptionResponse(new Date(),e.getMessage(),webRequest.getDescription(false));
//        return new ResponseEntity(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
//    }
    @ExceptionHandler(EmailException.class)
    public final ResponseEntity<Object> handleEmailAlreadyException(Exception ex,WebRequest request){
        ExceptionResponse exceptionResponse=new ExceptionResponse(new Date(),ex.getMessage(),request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse,HttpStatus.CONFLICT);
    }
    @ExceptionHandler(GstException.class)
    public final ResponseEntity<Object> handleGstAlreadyException(Exception ex,WebRequest request){
        ExceptionResponse exceptionResponse=new ExceptionResponse(new Date(),ex.getMessage(),request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse,HttpStatus.CONFLICT);
    }
    @ExceptionHandler(TokenInvalidException.class)
    public final ResponseEntity<Object> handleInvalidTokenException(Exception ex,WebRequest request){
        ExceptionResponse exceptionResponse=new ExceptionResponse(new Date(),ex.getMessage(),request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse,HttpStatus.UNAUTHORIZED);
    }
    @ExceptionHandler(UserNotFoundException.class)
    public final ResponseEntity<Object> userNotFoundException(Exception ex,WebRequest request){
        ExceptionResponse exceptionResponse=new ExceptionResponse(new Date(),ex.getMessage(),request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse,HttpStatus.UNAUTHORIZED);
    }
    @ExceptionHandler(PasswordException.class)
    public final ResponseEntity<Object> invalidPasswordException(Exception ex,WebRequest request){
        ExceptionResponse exceptionResponse=new ExceptionResponse(new Date(),ex.getMessage(),request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse,HttpStatus.CONFLICT);
    }
    @ExceptionHandler(CategoryException.class)
    public final ResponseEntity<Object> categoryException(Exception ex,WebRequest request){
        ExceptionResponse exceptionResponse=new ExceptionResponse(new Date(),ex.getMessage(),request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse,HttpStatus.CONFLICT);
    }
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), "Validation Failed", ex.getBindingResult().toString());
        return new ResponseEntity(exceptionResponse, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(ProductException.class)
    public final ResponseEntity<Object> productException(Exception ex,WebRequest request){
        ExceptionResponse exceptionResponse=new ExceptionResponse(new Date(),ex.getMessage(),request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse,HttpStatus.CONFLICT);
    }


}
