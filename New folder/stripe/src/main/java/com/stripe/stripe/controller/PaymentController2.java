package com.stripe.stripe.controller;

import com.stripe.model.checkout.Session;
import com.stripe.stripe.dto.RequestChargeDTO;
import com.stripe.stripe.service.StripeService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.stripe.exception.StripeException;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/payment")
public class PaymentController2 {

    private final StripeService stripeService;

    public PaymentController2(StripeService stripeService) {
        this.stripeService = stripeService;
    }

    @PostMapping("/charge")
    public ResponseEntity<Map<String, String>> charge(@RequestBody RequestChargeDTO requestChargeDTO, HttpSession session) {
        Map<String, String> response = new HashMap<>();
        try {
            Session checkoutSession = stripeService.createCheckoutSession(requestChargeDTO);
            String checkoutUrl = checkoutSession.getUrl();

            // Save RequestChargeDTO to Redis-backed session
            session.setAttribute("requestChargeDTO", requestChargeDTO);

            response.put("status", "success");
            response.put("message", "Charge created successfully.");
            response.put("url", checkoutUrl);

            return ResponseEntity.ok(response);
        } catch (StripeException e) {
            response.put("status", "error");
            response.put("message", "Charge creation failed: " + e.getMessage());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/success")
    public String success(HttpSession session) {
        RequestChargeDTO requestChargeDTO = (RequestChargeDTO) session.getAttribute("requestChargeDTO");
        if (requestChargeDTO != null) {
            stripeService.updateOrder(requestChargeDTO);
            return "success"; // Return the view name (success.html) or use it as needed
        } else {
            return "redirect:/error"; // Redirect to an error page or another appropriate action
        }
    }

//    @PostMapping("/charge")
//    public ResponseEntity<Map<String, String>> charge(@RequestBody RequestChargeDTO requestChargeDTO) {
//        Map<String, String> response = new HashMap<>();
//        try {
//            Session checkoutSession = stripeService.createCheckoutSession(requestChargeDTO);
//            String checkoutUrl = checkoutSession.getUrl();
//
//            HttpSession session = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getSession();
//            session.setAttribute("requestChargeDTO", requestChargeDTO);
//
//            response.put("status", "success");
//            response.put("message", "Charge created successfully.");
//            response.put("url", checkoutUrl);
//
//            return ResponseEntity.ok(response);
//        } catch (StripeException e) {
//            response.put("status", "error");
//            response.put("message", "Charge creation failed: " + e.getMessage());
//
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
//        }
//    }
//
//    @GetMapping("/success")
//    public String success(HttpSession session) {
//        RequestChargeDTO requestChargeDTO = (RequestChargeDTO) session.getAttribute("requestChargeDTO");
//        if (requestChargeDTO != null) {
//            stripeService.updateOrder(requestChargeDTO);
//            return "success"; // Return the view name (success.html) or use it as needed
//        } else {
//            return "redirect:/error"; // Redirect to an error page or another appropriate action
//        }
//    }
}