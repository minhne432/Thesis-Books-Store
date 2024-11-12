package com.comestic.shop.service;

import com.comestic.shop.dto.OrderDTO;
import com.comestic.shop.exception.InsufficientInventoryException;
import com.comestic.shop.model.Order;

public interface OrderService {
    Order placeOrder(Order order) throws InsufficientInventoryException;

    Order saveOrder(Order order);

    Order findByOrderCode(String orderCode);

}

