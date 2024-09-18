package com.order_service.dto.paymentdto;

import lombok.Data;

@Data
public class PaymentDTO {
    private Long odrerId;
    private String token;
    private String cartId;
    private Double totalPrice;
    private String currency;
    private Integer stutseOrderPayment;
}
