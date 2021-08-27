package com.crumbs.PaymentService.service;

import com.crumbs.PaymentService.dto.CreatePayment;
import com.crumbs.PaymentService.dto.CreatePaymentResponse;
import com.crumbs.lib.entity.Payment;
import com.crumbs.lib.repository.PaymentRepository;
import com.google.gson.JsonSyntaxException;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Event;
import com.stripe.model.EventDataObjectDeserializer;
import com.stripe.model.PaymentIntent;
import com.stripe.model.StripeObject;
import com.stripe.net.ApiResource;
import com.stripe.param.PaymentIntentCreateParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class StripePaymentService {

    private final PaymentRepository paymentRepository;

    @Autowired
    public StripePaymentService(PaymentRepository paymentRepository){
        this.paymentRepository = paymentRepository;
    }

    public CreatePaymentResponse createPaymentIntent(CreatePayment createPayment) throws StripeException {

        Stripe.apiKey = "sk_test_51JNmSeBoRXU1dvNXj8tijwpwJzNjk5kiSDNGbTvadbU2fuXKYvGgPzINOF1RUmmqh15uq3HcXXhNeGTrsG1h8FmL00S1Et9dFu";

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
