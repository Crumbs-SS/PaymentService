package com.crumbs.PaymentService.service;

import com.crumbs.PaymentService.MockUtil;
import com.crumbs.PaymentService.dto.CreatePaymentResponse;
import com.crumbs.lib.entity.Payment;
import com.crumbs.lib.repository.PaymentRepository;
import com.stripe.exception.StripeException;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;


@SpringBootTest
class StripePaymentServiceTest {

    @Autowired
    StripePaymentService stripePaymentService;

    @MockBean
    PaymentRepository paymentRepository;

    @Test
    void createPaymentIntent() throws StripeException {
        Mockito.when(paymentRepository.save(ArgumentMatchers.any(Payment.class)))
                .thenReturn(MockUtil.getPayment());
        stripePaymentService.createPaymentIntent(MockUtil.validCreatePayment());
        verify(paymentRepository).save(any(Payment.class));
    }
}