package com.crumbs.PaymentService.controller;

import com.crumbs.PaymentService.dto.CreatePayment;
import com.crumbs.PaymentService.dto.CreatePaymentResponse;
import com.crumbs.PaymentService.dto.OrderTotal;
import com.stripe.exception.StripeException;
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
    private static Gson gson = new Gson();

    @PostMapping("/create-payment-intent")
    public String createPaymentIntent(@RequestBody Float orderTotal) throws StripeException {
       Stripe.apiKey = "sk_test_51JNmSeBoRXU1dvNXj8tijwpwJzNjk5kiSDNGbTvadbU2fuXKYvGgPzINOF1RUmmqh15uq3HcXXhNeGTrsG1h8FmL00S1Et9dFu";

        PaymentIntentCreateParams createParams = new PaymentIntentCreateParams.Builder()
                .setCurrency("usd")
                .setAmount((long) (orderTotal * 100L)) //createPayment use here
                .build();

        // Create a PaymentIntent with the order amount and currency
        PaymentIntent intent = PaymentIntent.create(createParams);
        CreatePaymentResponse paymentResponse = new CreatePaymentResponse(intent.getClientSecret());
        return gson.toJson(paymentResponse);
    }
}
