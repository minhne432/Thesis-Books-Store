package com.comestic.shop.service;

import com.comestic.shop.dto.OrderDTO;
import com.comestic.shop.exception.InsufficientInventoryException;
import com.comestic.shop.model.Order;

public interface OrderService {
    Order placeOrder(OrderDTO orderDto) throws InsufficientInventoryException;
}
