package com.crumbs.PaymentService.service;

import com.crumbs.PaymentService.MockUtil;
import com.crumbs.PaymentService.dto.CreatePaymentResponse;
import com.crumbs.lib.entity.Payment;
import com.crumbs.lib.repository.PaymentRepository;
import com.stripe.exception.StripeException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import static org.mockito.Mockito.verify;
import static org.mockito.ArgumentMatchers.*;

@SpringBootTest
public class StripePaymentServiceTests {

    @Autowired
    StripePaymentService stripePaymentService;

    @MockBean
    PaymentRepository paymentRepository;

    @Test
    public void createPaymentIntent() throws StripeException {
        Mockito.when(paymentRepository.save(ArgumentMatchers.any(Payment.class)))
                .thenReturn(MockUtil.getPayment());
        CreatePaymentResponse paymentResponse = stripePaymentService.createPaymentIntent(MockUtil.validCreatePayment());
        verify(paymentRepository).save(any(Payment.class));
    }
}
