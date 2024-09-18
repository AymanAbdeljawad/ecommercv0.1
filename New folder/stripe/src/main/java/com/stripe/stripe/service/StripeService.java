package com.stripe.stripe.service;

import com.google.gson.Gson;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.model.checkout.Session;
import com.stripe.param.PaymentIntentCreateParams;
import com.stripe.param.checkout.SessionCreateParams;
import com.stripe.stripe.dto.PaymentRequest;
import com.stripe.stripe.dto.QuantityDTO;
import com.stripe.stripe.dto.RequestChargeDTO;
import com.stripe.stripe.dto.cartdto.CartDTO;
import com.stripe.stripe.dto.cartdto.CartItemDTO;
import com.stripe.stripe.dto.cartdto.ResponseCartDTO;
import com.stripe.stripe.dto.payment.PaymentDTO;
import com.stripe.stripe.dto.productdto.RequestupdateQuantity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StripeService {
    private final String stripeSecretKey;
    private final Gson gson = new Gson();

    @Autowired
    private RestTemplate restTemplate;

    public StripeService(@Value("${stripe.secret.key}") String stripeSecretKey) {
        this.stripeSecretKey = stripeSecretKey;
        Stripe.apiKey = stripeSecretKey;
    }


    public Session createCheckoutSession(RequestChargeDTO requestChargeDTO) throws StripeException {
        PaymentDTO paymentDTO = requestChargeDTO.getPaymentDTO();
        SessionCreateParams sessionParams = SessionCreateParams.builder()
                .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                .addLineItem(SessionCreateParams.LineItem.builder()
                        .setPriceData(SessionCreateParams.LineItem.PriceData.builder()
                                .setCurrency(paymentDTO.getCurrency())
                                .setProductData(SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                        .setName("Product")
                                        .build())
                                .setUnitAmount((long) paymentDTO.getTotalPrice())
                                .build())
                        .setQuantity(1L)
                        .build())
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl("http://localhost:8084/payment/success")
                .setCancelUrl("http://localhost:8084/payment/cancel")
                .build();

        Session session = Session.create(sessionParams);
        return session;
    }



    public void updateOrder(RequestChargeDTO requestChargeDTO) {
        try {
            CartDTO cartDTO = fetchCartItems(requestChargeDTO);
            List<CartItemDTO> items = cartDTO.getItems();
            RequestupdateQuantity requestupdateQuantity = createUpdateQuantityRequest(requestChargeDTO, items);
            updateProductQuantities(requestupdateQuantity);
            updatePaymentStatus(requestChargeDTO);
            if (requestChargeDTO.getClientId() == 0) {
                throw new RuntimeException("Client ID is zero; performing additional actions.");
            }
        } catch (Exception e) {
            throw new RuntimeException("Order update failed", e);
        }
    }

    private CartDTO fetchCartItems(RequestChargeDTO requestChargeDTO) {
        String url = "http://localhost:8082/api/cart/getItemsFromCartById";
        ResponseEntity<ResponseCartDTO> response = restTemplate.postForEntity(url, requestChargeDTO, ResponseCartDTO.class);
        if (response.getStatusCode() != HttpStatus.OK) {
            throw new RuntimeException("Failed to retrieve cart items");
        }
        return response.getBody().getCartDTO();
    }

    private RequestupdateQuantity createUpdateQuantityRequest(RequestChargeDTO requestChargeDTO, List<CartItemDTO> items) {
        RequestupdateQuantity requestupdateQuantity = new RequestupdateQuantity();
        requestupdateQuantity.setToken(requestChargeDTO.getToken());
        requestupdateQuantity.setClientId(requestChargeDTO.getClientId());
        requestupdateQuantity.setTracingId(requestChargeDTO.getTracingId());
        requestupdateQuantity.setErrorCode(requestChargeDTO.getErrorCode());
        requestupdateQuantity.setErrorDesc(requestChargeDTO.getErrorDesc());

        List<QuantityDTO> quantityDTOList = items.stream()
                .map(item -> {
                    QuantityDTO quantityDTO = new QuantityDTO();
                    quantityDTO.setQuantity(item.getQuantity());
                    quantityDTO.setProductName(item.getProductName());
                    return quantityDTO;
                })
                .collect(Collectors.toList());

        requestupdateQuantity.setQuantityDTOList(quantityDTOList);
        return requestupdateQuantity;
    }

    private void updateProductQuantities(RequestupdateQuantity requestupdateQuantity) {
        String url = "http://localhost:8081/api/products/updateQuantityList";
        ResponseEntity<PaymentRequest.RequestProduct> response = restTemplate.postForEntity(url, requestupdateQuantity, PaymentRequest.RequestProduct.class);
        if (response.getStatusCode() != HttpStatus.OK) {
            throw new RuntimeException("Failed to update product quantities");
        }
    }

    private void updatePaymentStatus(RequestChargeDTO requestChargeDTO) {
        String url = "http://localhost:8083/api/order/updatePaymentStatus";
        ResponseEntity<RequestChargeDTO> response = restTemplate.postForEntity(url, requestChargeDTO, RequestChargeDTO.class);
        if (response.getStatusCode() != HttpStatus.OK) {
            throw new RuntimeException("Failed to update payment status");
        }
    }
}
