package com.order_service.dto.orderdto;

import com.order_service.common.dto.InfoDTO;
import lombok.Data;

@Data
public class RequestOrderDTO extends InfoDTO {
    private OrderDTO orderDTO;
//    private Long orderId;
//    private String token;
//    private String cartId;
//    private Double totalPrice;
//    private Integer stutseOrderPayment;
}