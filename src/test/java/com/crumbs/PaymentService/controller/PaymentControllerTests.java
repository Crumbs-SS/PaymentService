package com.crumbs.PaymentService.controller;

import com.crumbs.PaymentService.MockUtil;
import com.crumbs.PaymentService.service.StripePaymentService;
import com.crumbs.lib.repository.PaymentRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(PaymentController.class)
public class PaymentControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    StripePaymentService stripePaymentService;

    @Test
    public void createPaymentIntent() throws Exception{
        //verifying HTTP Request Matching + Input Serialization
        mockMvc.perform(post("/payment-service/create-payment-intent/{username}", "correctUsername")
                .header("Authorization", ("Bearer " + MockUtil.createMockJWT("CUSTOMER")))
                .content(objectMapper.writeValueAsString(MockUtil.validCreatePayment()))
                .contentType("application/json"))
                .andExpect(status().isOk());

        //verifying input validation
        mockMvc.perform(post("/payment-service/create-payment-intent/{username}", "correctUsername")
                .header("Authorization", ("Bearer " + MockUtil.createMockJWT("CUSTOMER")))
                .content(objectMapper.writeValueAsString(MockUtil.invalidCreatePayment()))
                .contentType("application/json"))
                .andExpect(status().isBadRequest());

        //verifying authorization with role
        mockMvc.perform(post("/payment-service/create-payment-intent/{username}", "correctUsername")
                .header("Authorization", ("Bearer " + MockUtil.createMockJWT("wrongRole")))
                .content(objectMapper.writeValueAsString(MockUtil.validCreatePayment()))
                .contentType("application/json"))
                .andExpect(status().isForbidden());
    }

}
