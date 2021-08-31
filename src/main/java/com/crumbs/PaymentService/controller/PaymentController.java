package com.crumbs.PaymentService.controller;

import com.crumbs.PaymentService.dto.CreatePayment;
import com.crumbs.PaymentService.service.StripePaymentService;
import com.stripe.exception.StripeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@PreAuthorize("isAuthenticated()")
public class PaymentController {

    private final StripePaymentService paymentService;

    PaymentController(StripePaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PreAuthorize("hasAuthority('CUSTOMER')")
    @PostMapping(value="/create-payment-intent", produces = "application/json")
    public ResponseEntity<Object> createPaymentIntent(@Validated @RequestBody CreatePayment createPayment) throws StripeException {
      return new ResponseEntity<>(paymentService.createPaymentIntent(createPayment), HttpStatus.OK);
    }

}
