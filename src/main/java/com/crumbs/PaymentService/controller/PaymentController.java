package com.crumbs.PaymentService.controller;

import com.crumbs.PaymentService.dto.CreatePayment;
import com.crumbs.PaymentService.service.StripePaymentService;
import com.stripe.exception.StripeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class PaymentController {

    private final StripePaymentService paymentService;

    PaymentController(StripePaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping(value="/create-payment-intent", produces = "application/json")
    public ResponseEntity<Object> createPaymentIntent(@Validated  @RequestBody CreatePayment createPayment) throws StripeException {
      return new ResponseEntity<>(paymentService.createPaymentIntent(createPayment), HttpStatus.OK);
    }

}
