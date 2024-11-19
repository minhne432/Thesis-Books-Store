package com.comestic.shop.service;
import com.comestic.shop.dto.BranchRevenueDTO;
import com.comestic.shop.dto.OrderDTO;
import com.comestic.shop.dto.OrderItemDTO;
import com.comestic.shop.exception.InsufficientInventoryException;
import com.comestic.shop.model.Order;
import com.comestic.shop.model.OrderDetails;
import com.comestic.shop.model.OrderStatus;
import com.comestic.shop.model.Product;
import com.comestic.shop.repository.OrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
//         Chỉ tiến hành nếu đơn hàng đang ở trạng thái "PENDING"
        if ((order.getStatus() != OrderStatus.PENDING)) {
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
            order.setStatus(OrderStatus.UNPAID_PENDING);
        } else if ("VNPAY".equals(order.getPaymentMethod())) {
            order.setStatus(OrderStatus.PAID_PENDING);
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


    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public List<Order> getOrdersByBranchId(Long branchId) {
        return orderRepository.findByBranch_BranchId(branchId);
    }

    @Override
    public Order getOrderById(int orderId) {
        return orderRepository.findById(orderId).orElse(null);
    }

    @Override
    public Order getOrderByOrderCode(String orderCode) {
        return orderRepository.findByOrderCode(orderCode);
    }

    @Override
    public Page<Order> getOrders(Long branchId, OrderStatus status, String orderCode, Pageable pageable) {
        if (branchId != null && status != null && orderCode != null && !orderCode.isEmpty()) {
            return orderRepository.findByBranch_BranchIdAndStatusAndOrderCodeContaining(
                    branchId, status, orderCode, pageable);
        } else if (branchId != null && status != null) {
            return orderRepository.findByBranch_BranchIdAndStatus(branchId, status, pageable);
        } else if (branchId != null && orderCode != null && !orderCode.isEmpty()) {
            return orderRepository.findByBranch_BranchIdAndOrderCodeContaining(branchId, orderCode, pageable);
        } else if (status != null && orderCode != null && !orderCode.isEmpty()) {
            return orderRepository.findByStatusAndOrderCodeContaining(status, orderCode, pageable);
        } else if (branchId != null) {
            return orderRepository.findByBranch_BranchId(branchId, pageable);
        } else if (status != null) {
            return orderRepository.findByStatus(status, pageable);
        } else if (orderCode != null && !orderCode.isEmpty()) {
            return orderRepository.findByOrderCodeContaining(orderCode, pageable);
        } else {
            return orderRepository.findAll(pageable);
        }
    }

    @Override
    public Page<Order> getOrdersByCustomerID(int customerID, Pageable pageable) {
        return orderRepository.findByCustomer_CustomerID(customerID, pageable);
    }

    @Override
    @Transactional
    public Order updateOrderStatus(Order order, OrderStatus newStatus) {
        // Get the current status
        OrderStatus oldStatus = order.getStatus();

        // Update the status
        order.setStatus(newStatus);
        orderRepository.save(order);

        // If the new status is PAID_CANCELED or UNPAID_CANCELED, and the old status was not canceled
        if ((newStatus == OrderStatus.PAID_CANCELED || newStatus == OrderStatus.UNPAID_CANCELED)
                && (oldStatus != OrderStatus.PAID_CANCELED && oldStatus != OrderStatus.UNPAID_CANCELED)) {

            // Increase inventory quantities
            for (OrderDetails orderDetails : order.getOrderDetails()) {
                Product product = orderDetails.getProduct();
                int quantity = orderDetails.getQuantity();

                // Increase the inventory for each item
                inventoryService.increaseInventoryQuantity(order.getBranch(), product, quantity);
            }
        }

        return order;
    }

    public List<BranchRevenueDTO> getBranchRevenueByStatus(OrderStatus status) {
        return orderRepository.findBranchRevenueByStatus(status);
    }

    @Override
    public List<BranchRevenueDTO> getBranchRevenueByStatusAndDates(OrderStatus status, Date startDate, Date endDate) {
        return orderRepository.findBranchRevenueByStatusAndDates(status, startDate, endDate);
    }
}


