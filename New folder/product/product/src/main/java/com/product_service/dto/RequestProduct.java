package com.product_service.dto;

import com.product_service.common.dto.InfoDTO;
import lombok.Data;

@Data
public class RequestProduct extends InfoDTO {
    private ProductDTO productDTO;
}



//json
//{
//        "clientId": "0",
//        "tracingId": "0",
//        "errorCode": "a",
//        "errorDesc": "a",
//        "token": "1001",
//        "productDTO": {
//        "name": "product 1",
//        "description": "product 1 descraption",
//        "price": "180.99",
//        "quantity": "120"
//        }
//        }