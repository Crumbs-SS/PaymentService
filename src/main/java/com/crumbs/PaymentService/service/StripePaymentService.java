package com.crumbs.PaymentService.service;

import com.crumbs.PaymentService.dto.CreatePayment;
import com.crumbs.PaymentService.dto.CreatePaymentResponse;
import com.crumbs.lib.entity.Payment;
import com.crumbs.lib.repository.PaymentRepository;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Service
@RequiredArgsConstructor
public class StripePaymentService {

    @Autowired
    private static PaymentRepository paymentRepository;

    public static CreatePaymentResponse createPaymentIntent(CreatePayment createPayment) throws StripeException {

        Stripe.apiKey = System.getenv("STRIPE_API_KEY");

        PaymentIntentCreateParams createParams = new PaymentIntentCreateParams.Builder()
                .setCurrency("usd")
                .setAmount((long) ( createPayment.getCartTotal() * 100L))
                .build();
        PaymentIntent intent = PaymentIntent.create(createParams);

        Payment payment = new Payment().builder()
                .stripeID(intent.getId().toString())
                .amount(intent.getAmount().toString())
                .clientSecret(intent.getClientSecret().toString().split("secret_")[1])
                .status(intent.getStatus().toString())
                .build();

        paymentRepository.save(payment);

        return new CreatePaymentResponse(intent.getClientSecret());
    }
}
