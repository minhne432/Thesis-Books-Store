package com.comestic.shop.controller;

import com.comestic.shop.dto.OrderDto;
import com.comestic.shop.exception.InsufficientInventoryException;
import com.comestic.shop.model.Order;
import com.comestic.shop.service.OrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderServiceImpl orderServiceImple;

    @PostMapping("/place")
    public ResponseEntity<Order> placeOrder(@RequestBody OrderDto orderDto) throws InsufficientInventoryException {
        Order order = orderServiceImple.placeOrder(orderDto);
        return new ResponseEntity<>(order, HttpStatus.CREATED);
    }
}
