//package com.example.SpringSecurity.exceptions;
//
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
//import org.springframework.web.bind.MethodArgumentNotValidException;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.context.request.WebRequest;
//import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
//
//import java.util.Date;
//
//@ControllerAdvice
//@RestController
//public class CustomizedExceptionHandler extends ResponseEntityExceptionHandler {
//
//    @ExceptionHandler(Exception.class)
//    public final ResponseEntity<Object> handleAllException(Exception ex, WebRequest request) throws Exception {
//        ExceptionResponse exceptionResponse = new ExceptionResponse
//                (new Date(), ex.getMessage(), request.getDescription(false));
//
//        return new ResponseEntity(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
//    }
//
//
//    @Override
//    protected ResponseEntity<Object> handleMethodArgumentNotValid(
//            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
//        ExceptionResponse exceptionResponse= new ExceptionResponse(
//                new Date(),"Validation failed",ex.getBindingResult().toString());
//        return new ResponseEntity(exceptionResponse,HttpStatus.BAD_REQUEST);
//    }
//
//    @ExceptionHandler(EmailException.class)
//    public final ResponseEntity<Object> emailAlreadyExistException(Exception ex, WebRequest request) throws Exception {
//        ExceptionResponse exceptionResponse = new ExceptionResponse
//                (new Date(), ex.getMessage(), request.getDescription(false));
//
//        return new ResponseEntity(exceptionResponse, HttpStatus.CONFLICT);
//    }
//
//    @ExceptionHandler(InvalidTokenException.class)
//    public final ResponseEntity<Object> invalidTokenException(Exception ex, WebRequest request) throws Exception {
//        ExceptionResponse exceptionResponse = new ExceptionResponse
//                (new Date(), ex.getMessage(), request.getDescription(false));
//
//        return new ResponseEntity(exceptionResponse, HttpStatus.UNAUTHORIZED);
//    }
//    @ExceptionHandler(UserNotFoundException.class)
//    public final ResponseEntity<Object> userNotFoundException(Exception ex, WebRequest request) throws Exception {
//        ExceptionResponse exceptionResponse = new ExceptionResponse
//                (new Date(), ex.getMessage(), request.getDescription(false));
//
//        return new ResponseEntity(exceptionResponse, HttpStatus.NOT_FOUND);
//    }
//    @ExceptionHandler(PasswordException.class)
//    public final ResponseEntity<Object> passwordException(Exception ex, WebRequest request) throws Exception {
//        ExceptionResponse exceptionResponse = new ExceptionResponse
//                (new Date(), ex.getMessage(), request.getDescription(false));
//        return new ResponseEntity(exceptionResponse, HttpStatus.CONFLICT);
//    }
//}
