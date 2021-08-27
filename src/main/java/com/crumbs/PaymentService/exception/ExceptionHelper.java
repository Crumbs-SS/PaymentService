package com.crumbs.PaymentService.exception;

import com.stripe.exception.StripeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.NoSuchElementException;

@ControllerAdvice
public class ExceptionHelper {

    @ExceptionHandler(StripeException.class)
    protected ResponseEntity<Object> handleException(){
        return new ResponseEntity<>("Error with Stripe server. Choose another payment method or try again later", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
