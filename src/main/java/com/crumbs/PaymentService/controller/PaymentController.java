package com.crumbs.PaymentService.controller;

import com.crumbs.PaymentService.dto.CreatePayment;
import com.crumbs.PaymentService.dto.CreatePaymentResponse;
import com.crumbs.PaymentService.service.StripePaymentService;
import com.crumbs.lib.entity.Payment;
import com.crumbs.lib.repository.OrderRepository;
import com.crumbs.lib.repository.PaymentRepository;
import com.stripe.exception.StripeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.google.gson.Gson;
import com.stripe.Stripe;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;


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
