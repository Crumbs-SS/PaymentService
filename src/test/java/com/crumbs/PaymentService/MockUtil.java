package com.crumbs.PaymentService;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.crumbs.PaymentService.dto.CreatePayment;
import com.crumbs.lib.entity.Payment;

import java.util.Date;
import java.util.Optional;

public class MockUtil {

    public static CreatePayment validCreatePayment(){
        return CreatePayment.builder().cartTotal(2F).build();
    }
    public static CreatePayment invalidCreatePayment(){
        return CreatePayment.builder().cartTotal(null).build();
    }

    public static Payment getPayment(){
        return Payment.builder().status("success").stripeID("stripeId").clientSecret("clientSecret").amount("1.2").build();
    }

    public  static String createMockJWT(String role){
        final long EXPIRATION_TIME = 900_000;
        String token;
        Algorithm algorithm = Algorithm.HMAC256("MfiVzoZ/aO8N4sdd32WKC8qdIag1diSNfiZ4mtKQ8J1oaBxoCsgcXzjeH43rIwjSuKVC9BpeqEV/iUGczehBjyHH2j3ofifbQW9MquNd8mROjloyzzTGdD1iw4d5uxFV88GJcjPRo1BUvhVRbtIvKYjmeSyxA3cvpjPUinp6HMIoh0uHChrM8kUfql1WpmmSM+NyRMlMY7WGbiZ/GRCCdB8s4hzxy9baLp0ENQ==");
        token = JWT.create()
                .withClaim("role", role)
                .withSubject("correctUsername")
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(algorithm);

        return token;
    }
}
