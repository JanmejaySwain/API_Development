package com.myblogrestapi.exception;

import com.myblogrestapi.payload.ErrorDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    //handel specific exception
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDetails> handelResourceNotFoundException(ResourceNotFoundException exception,
                                                                        WebRequest webRequest)
    {
        ErrorDetails errorDetails=new ErrorDetails(new Date(),exception.getMessage(), webRequest.getDescription(false));
      return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(BlogApiException.class)
    public ResponseEntity<ErrorDetails> handelBlogApiException(BlogApiException exception,
                                                               WebRequest webRequest)
    {
        ErrorDetails errorDetails=new ErrorDetails(new Date(), exception.getMessage(), webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetails,HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> handelAllException(Exception exception,
                                                           WebRequest webRequest)
    {
        ErrorDetails errorDetails=new ErrorDetails(new Date(),exception.getMessage(), webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetails,HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
