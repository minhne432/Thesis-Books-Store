package com.comestic.shop.service;

import com.comestic.shop.model.Order;
import com.comestic.shop.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Optional<Order> getOrderById(int id) {
        return orderRepository.findById(id);
    }

    public Order addOrder(Order order) {
        return orderRepository.save(order);
    }

    public Order updateOrder(int id, Order orderDetails) {
        Optional<Order> optionalOrder = orderRepository.findById(id);
        if (optionalOrder.isPresent()) {
            Order order = optionalOrder.get();
            order.setOrderDate(orderDetails.getOrderDate());
            order.setTotalAmount(orderDetails.getTotalAmount());
            order.setStatus(orderDetails.getStatus());
            order.setPaymentMethod(orderDetails.getPaymentMethod());
            order.setCustomer(orderDetails.getCustomer());
            order.setBranch(orderDetails.getBranch());
            order.setCoupon(orderDetails.getCoupon());
            order.setOrderDetails(orderDetails.getOrderDetails());
            order.setPayment(orderDetails.getPayment());
            return orderRepository.save(order);
        } else {
            return null; // Hoặc bạn có thể ném ra ngoại lệ tùy theo logic
        }
    }

    public void deleteOrder(int id) {
        orderRepository.deleteById(id);
    }
}
