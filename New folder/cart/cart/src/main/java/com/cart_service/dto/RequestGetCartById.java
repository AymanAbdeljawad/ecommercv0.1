package com.cart_service.dto;

import com.cart_service.common.dto.InfoDTO;

public class RequestGetCartById extends InfoDTO {
    private Long cartId;

    public Long getCartId() {
        return cartId;
    }

    public void setCartId(Long cartId) {
        this.cartId = cartId;
    }
}


//json
//{
//    "clientId": "0",
//        "tracingId": "0",
//        "errorCode": "a",
//        "errorDesc": "a",
//        "token":"1001",
//        "cartId":"1"
//}