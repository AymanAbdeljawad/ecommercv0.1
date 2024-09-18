package com.cart_service.dto;

public class CartItemDTO {
    private Long id;
    private String productName;
    private int quantity;
    private Double price;
    private String description;
    private String token;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}


//jason
//{
//    "clientId": "0",
//        "tracingId": "0",
//        "errorCode": "a",
//        "errorDesc": "a",
//        "token":"1001",
//        "cartId":"",
//        "cartItemsDTO":[
//    {
//        "productName":"product 1",
//            "quantity":"50",
//            "price":"99.99",
//            "description":"product descraption"
//    }
//    ]
//
//}