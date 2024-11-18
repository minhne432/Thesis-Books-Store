package com.comestic.shop.service;

import com.comestic.shop.model.OrderDetails;
import com.comestic.shop.repository.OrderDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderDetailsService {

    @Autowired
    private OrderDetailsRepository orderDetailsRepository;

    public List<OrderDetails> getAllOrderDetails() {
        return orderDetailsRepository.findAll();
    }

    public Optional<OrderDetails> getOrderDetailsById(Long id) {
        return orderDetailsRepository.findById(id);
    }

    public OrderDetails addOrderDetails(OrderDetails orderDetails) {
        return orderDetailsRepository.save(orderDetails);
    }

    public OrderDetails updateOrderDetails(Long id, OrderDetails orderDetailsDetails) {
        Optional<OrderDetails> optionalOrderDetails = orderDetailsRepository.findById(id);
        if (optionalOrderDetails.isPresent()) {
            OrderDetails orderDetails = optionalOrderDetails.get();
            orderDetails.setQuantity(orderDetailsDetails.getQuantity());
            orderDetails.setUnitPrice(orderDetailsDetails.getUnitPrice());
            orderDetails.setOrder(orderDetailsDetails.getOrder());
            orderDetails.setProduct(orderDetailsDetails.getProduct());
            return orderDetailsRepository.save(orderDetails);
        } else {
            return null; // Hoặc bạn có thể ném ra ngoại lệ tùy theo logic
        }
    }

    public void deleteOrderDetails(Long id) {
        orderDetailsRepository.deleteById(id);
    }

    public List<OrderDetails> getOrderDetailsByOrderID(int orderID) {
        return orderDetailsRepository.findByOrder_OrderID(orderID);
    }
}
