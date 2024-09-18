package com.order_service.dto.orderdto;

import lombok.Data;

@Data
public class OrderDTO {

    private Long orderId;
    private String token;
    private String cartId;
    private Double totalPrice;
    private Integer stutseOrderPayment;
}

