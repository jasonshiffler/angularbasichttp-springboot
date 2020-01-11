package com.shiffler.simplerestapi.controlleradvice;

import com.shiffler.simplerestapi.exceptions.BookNotFoundException;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {


    /**
     * Creates a response entity based on bad data being passed in to create an object
      * @param ex
     * @param headers
     * @param status
     * @param request
     * @return A response entity containing the error message for passing in bad data
     */
    @Override
        protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                      HttpHeaders headers, HttpStatus status,
                                                                      WebRequest request) {
            ErrorDetails errorDetails = new ErrorDetails(new Date(), "Validation Failed",
                    ex.getBindingResult().toString());
            return new ResponseEntity(errorDetails, HttpStatus.BAD_REQUEST);
        }

    @ExceptionHandler(BookNotFoundException.class)
    public final ResponseEntity<Object> handleBookNotFoundException(BookNotFoundException ex, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(),
                request.getDescription(false));
        log.info("********************************************");
        return new ResponseEntity<Object>(errorDetails, HttpStatus.NOT_FOUND);
    }

    }
