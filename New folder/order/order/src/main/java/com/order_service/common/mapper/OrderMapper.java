package com.order_service.common.mapper;

import com.order_service.dto.orderdto.OrderDTO;
import com.order_service.entity.Order;
import org.springframework.stereotype.Component;

@Component
public class OrderMapper {

    public static OrderDTO convertToDTO(Order order) {
        if (order == null) {
            return null;
        }
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setOrderId(order.getId());
        orderDTO.setToken(order.getToken());
        orderDTO.setCartId(order.getCartId());
        orderDTO.setTotalPrice(order.getTotalPrice());
        orderDTO.setStutseOrderPayment(order.getStutseOrderPayment());
        return orderDTO;
    }

    public static Order convertToEntity(OrderDTO orderDTO) {
        if (orderDTO == null) {
            return null;
        }
        Order order = new Order();
        order.setId(orderDTO.getOrderId());
        order.setToken(orderDTO.getToken());
        order.setCartId(orderDTO.getCartId());
        order.setTotalPrice(orderDTO.getTotalPrice());
        order.setStutseOrderPayment(orderDTO.getStutseOrderPayment());
        return order;
    }
}
