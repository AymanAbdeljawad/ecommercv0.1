package com.stripe.stripe.dto.payment;

import lombok.Data;

@Data
public class PaymentDTO {
    private Long odrerId;
    private String token;
    private String cartId;
    private Long totalPrice;
    private String currency;
    private Integer stutseOrderPayment;
}
