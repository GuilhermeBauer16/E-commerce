package com.github.GuilhermeBauer.Ecommerce.handler;


import com.github.GuilhermeBauer.Ecommerce.exceptions.CategoryNotFound;
import com.github.GuilhermeBauer.Ecommerce.exceptions.ExceptionResponse;
import com.github.GuilhermeBauer.Ecommerce.exceptions.ProductNotAvailable;
import com.github.GuilhermeBauer.Ecommerce.exceptions.ProductNotFound;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@ControllerAdvice
@RestController
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({CategoryNotFound.class,
            ProductNotFound.class,
    ProductNotAvailable.class})
    public final ResponseEntity<ExceptionResponse> handlerNotFoundException(
            Exception ex,
            WebRequest webRequest){
        ExceptionResponse exceptionResponse = new ExceptionResponse(
                ex.getMessage(),
                webRequest.getDescription(false),
                new Date()

        );

        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);

    }

}
