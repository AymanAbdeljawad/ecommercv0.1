package com.stripe.stripe.controller;

import com.stripe.stripe.dto.RequestChargeDTO;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/session")
public class SessionController {

    @GetMapping("/get-request-charge")
    public ResponseEntity<RequestChargeDTO> getRequestCharge(HttpSession session) {
        RequestChargeDTO requestChargeDTO = (RequestChargeDTO) session.getAttribute("requestChargeDTO");

        if (requestChargeDTO != null) {
            return ResponseEntity.ok(requestChargeDTO);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}

