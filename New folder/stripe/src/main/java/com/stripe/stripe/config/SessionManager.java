package com.stripe.stripe.config;

import com.stripe.stripe.dto.PaymentRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;

@Component
public class SessionManager {

    public void storePaymentRequest(HttpSession session, PaymentRequest paymentRequest) {
        session.setAttribute("paymentRequest", paymentRequest);
    }

    public PaymentRequest getPaymentRequest(HttpSession session) {
        return (PaymentRequest) session.getAttribute("paymentRequest");
    }
}

