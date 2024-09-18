package com.stripe.stripe.controller;

import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.stripe.dto.RequestChargeDTO;
import com.stripe.stripe.service.StripeService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.HashMap;
import java.util.Map;

@Controller
@SessionAttributes("cartDTO")
public class PaymentController {

    @Autowired
    private RestTemplate restTemplate;

    private final StripeService stripeService;

    public PaymentController(StripeService stripeService) {
        this.stripeService = stripeService;
    }

    RequestChargeDTO requestChargeDTO = new RequestChargeDTO();


    @PostMapping("/charge")
    public ResponseEntity<Map<String, String>> charge(@RequestBody RequestChargeDTO requestChargeDTO) {
        Map<String, String> response = new HashMap<>();
        try {
            // Create Stripe Checkout Session
            Session checkoutSession = stripeService.createCheckoutSession(requestChargeDTO);
            String checkoutUrl = checkoutSession.getUrl();

            // Save RequestChargeDTO to session
            HttpSession session = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getSession();
            session.setAttribute("requestChargeDTO", requestChargeDTO);

            // Prepare success response
            response.put("status", "success");
            response.put("message", "Charge created successfully.");
            response.put("url", checkoutUrl); // URL to redirect for checkout

            return ResponseEntity.ok(response);
        } catch (StripeException e) {
            // Prepare error response
            response.put("status", "error");
            response.put("message", "Charge creation failed: " + e.getMessage());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }


//    @PostMapping("/charge")
//    public ResponseEntity<Map<String, String>> charge(@RequestBody RequestChargeDTO requestChargeDTO) {
//        Map<String, String> response = new HashMap<>();
//        try {
//            // Create Stripe Checkout Session
//            Session checkoutSession = stripeService.createCheckoutSession(requestChargeDTO);
//            String checkoutUrl = checkoutSession.getUrl();
//
//            // Prepare success response
//            response.put("status", "success");
//            response.put("message", "Charge created successfully.");
//            response.put("url", checkoutUrl); // URL to redirect for checkout
//
//            return ResponseEntity.ok(response);
//        } catch (StripeException e) {
//            // Prepare error response
//            response.put("status", "error");
//            response.put("message", "Charge creation failed: " + e.getMessage());
//
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
//        }
//    }

//    @PostMapping("/charge")
//    public ResponseEntity<Map<String, String>> charge(@RequestBody RequestChargeDTO requestChargeDTO) {
//        Map<String, String> response = new HashMap<>();
//        try {
//            Session checkoutSession = stripeService.createCheckoutSession(requestChargeDTO);
//            String checkoutUrl = checkoutSession.getUrl();
//            response.put("url", checkoutUrl);
//            return ResponseEntity.ok(response);
//        } catch (StripeException e) {
//            response.put("error", "Failure: " + e.getMessage());
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
//        }
//    }

//    @PostMapping("/charge")
//    public String charge(@RequestBody RequestChargeDTO requestChargeDTO, Model model) {
//        try {
//            Session checkoutSession = stripeService.createCheckoutSession(requestChargeDTO);
//            RequestChargeDTO extracted = extracted(requestChargeDTO);
//            return "redirect:" + checkoutSession.getUrl();
//        } catch (StripeException e) {
//            return "redirect:/failure";
//        }
//    }

//    @PostMapping("/charge")
//    public ResponseEntity<String> charge(@RequestBody RequestChargeDTO requestChargeDTO) {
//        try {
//            Session checkoutSession = stripeService.createCheckoutSession(requestChargeDTO);
//            String checkoutUrl = checkoutSession.getUrl();
//            return ResponseEntity.status(HttpStatus.FOUND).header("Location", checkoutUrl).build();
//        } catch (StripeException e) {
//            return ResponseEntity.status(HttpStatus.SEE_OTHER).header("Location", "/failure").build();
//        }
//    }
//@PostMapping("/charge")
//public ResponseEntity<String> charge(@RequestBody RequestChargeDTO requestChargeDTO) {
//    try {
//        Session checkoutSession = stripeService.createCheckoutSession(requestChargeDTO);
//        String checkoutUrl = checkoutSession.getUrl();
//        return ResponseEntity.ok(checkoutUrl); // Return the URL in the response body
//    } catch (StripeException e) {
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failure: " + e.getMessage());
//    }
//}


//    @GetMapping("/success")
//    public String success() {
//        return "success";
//    }


    @GetMapping("/cancel")
    public String cancel(SessionStatus sessionStatus) {
        sessionStatus.setComplete();
        return "cancel";
    }

    private RequestChargeDTO extracted(RequestChargeDTO requestChargeDTO) {
        this.requestChargeDTO.setPaymentDTO(requestChargeDTO.getPaymentDTO());
        this.requestChargeDTO.setClientId(requestChargeDTO.getClientId());
        this.requestChargeDTO.setTracingId(requestChargeDTO.getTracingId());
        this.requestChargeDTO.setErrorCode(requestChargeDTO.getErrorCode());
        this.requestChargeDTO.setErrorDesc(requestChargeDTO.getErrorDesc());
        this.requestChargeDTO.setToken(requestChargeDTO.getToken());
        return this.requestChargeDTO;
    }
}


//import com.stripe.exception.StripeException;
//import com.stripe.model.checkout.Session;
//import com.stripe.stripe.dto.*;
//import com.stripe.stripe.service.PaymentService;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpSession;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.bind.support.SessionStatus;
//import org.springframework.web.client.RestTemplate;
//
//import java.util.Map;
//
//@Controller
//public class PaymentController {
//    @Autowired
//    private RestTemplate restTemplate;
//
//    private final PaymentService paymentService;
//
//    public PaymentController(PaymentService paymentService) {
//        this.paymentService = paymentService;
//    }
//
//    @PostMapping("/charge")
//    public String charge(@RequestBody RequestChargeDTO requestChargeDTO) {
//        try {
//            Session checkoutSession = paymentService.createCheckoutSession(requestChargeDTO);
//            return "redirect:" + checkoutSession.getUrl();
//        } catch (StripeException e) {
//            return "redirect:/failure";
//        }
//    }
//
//    @GetMapping("/success")
//    public String success() {
//        return "success";
//    }
//
//    @GetMapping("/cancel")
//    public String cancel(SessionStatus sessionStatus) {
//        sessionStatus.setComplete();
//        return "cancel";
//    }
//}
//
