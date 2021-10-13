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
        Algorithm algorithm = Algorithm.HMAC256(System.getenv("JWT_SECRET"));
        token = JWT.create()
                .withAudience("crumbs")
                .withIssuer("Crumbs")
                .withClaim("role", role)
                .withSubject("correctUsername")
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(algorithm);

        return token;
    }
}
