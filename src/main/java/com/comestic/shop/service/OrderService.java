package com.comestic.shop.service;

import com.comestic.shop.dto.OrderDto;
import com.comestic.shop.exception.InsufficientInventoryException;
import com.comestic.shop.model.Order;
import com.comestic.shop.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public interface OrderService {
    Order placeOrder(OrderDto orderDto) throws InsufficientInventoryException;
}
