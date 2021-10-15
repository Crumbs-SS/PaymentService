package com.crumbs.paymentservice.service;

import com.crumbs.paymentservice.dto.CreatePayment;
import com.crumbs.paymentservice.dto.CreatePaymentResponse;
import com.crumbs.lib.entity.Payment;
import com.crumbs.lib.repository.PaymentRepository;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import static com.stripe.Stripe.apiKey;
@Service
@RequiredArgsConstructor
public class StripePaymentService {

    private final PaymentRepository paymentRepository;

    public CreatePaymentResponse createPaymentIntent(CreatePayment createPayment) throws StripeException {

        apiKey = System.getenv("STRIPE_API_KEY");
        PaymentIntentCreateParams createParams = new PaymentIntentCreateParams.Builder()
                .setCurrency("usd")
                .setAmount((long) ( createPayment.getCartTotal() * 100L))
                .build();
        PaymentIntent intent = PaymentIntent.create(createParams);

        Payment payment = Payment.builder()
                .stripeID(intent.getId())
                .amount(intent.getAmount().toString())
                .clientSecret(intent.getClientSecret().split("secret_")[1])
                .status(intent.getStatus())
                .build();

        paymentRepository.save(payment);

        return new CreatePaymentResponse(intent.getClientSecret());
    }
}
