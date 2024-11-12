package com.comestic.shop.service;

import com.comestic.shop.dto.OrderDTO;
import com.comestic.shop.dto.OrderItemDTO;
import com.comestic.shop.exception.InsufficientInventoryException;
import com.comestic.shop.model.Order;
import com.comestic.shop.model.OrderDetails;
import com.comestic.shop.model.Product;
import com.comestic.shop.repository.OrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private InventoryService inventoryService;

    @Transactional
    @Override
    public Order placeOrder(Order order) throws InsufficientInventoryException {
        // Chỉ tiến hành nếu đơn hàng đang ở trạng thái "PENDING"
        if (!"PENDING".equals(order.getStatus())) {
            throw new RuntimeException("Đơn hàng không ở trạng thái chờ xử lý.");
        }

        // Kiểm tra và cập nhật tồn kho
        for (OrderDetails orderDetails : order.getOrderDetails()) {
            Product product = orderDetails.getProduct();
            int quantity = orderDetails.getQuantity();
            inventoryService.decreaseInventoryQuantity(order.getBranch(), product, quantity);
        }

        // Cập nhật trạng thái đơn hàng dựa trên phương thức thanh toán
        if ("COD".equals(order.getPaymentMethod())) {
            order.setStatus("NEW");
        } else if ("VNPAY".equals(order.getPaymentMethod())) {
            order.setStatus("PAID");
        }

        // Lưu đơn hàng
        return orderRepository.save(order);
    }


    @Override
    public Order saveOrder(Order order) {
        // Lưu đơn hàng mà không cập nhật tồn kho
        return orderRepository.save(order);
    }

    @Override
    public Order findByOrderCode(String orderCode) {
        return orderRepository.findByOrderCode(orderCode);
    }

}


