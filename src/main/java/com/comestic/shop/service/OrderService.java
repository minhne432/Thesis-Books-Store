package com.comestic.shop.service;

import com.comestic.shop.dto.BranchRevenueDTO;
import com.comestic.shop.dto.OrderDTO;
import com.comestic.shop.exception.InsufficientInventoryException;
import com.comestic.shop.model.Order;
import com.comestic.shop.model.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;

public interface OrderService {
    Order placeOrder(Order order) throws InsufficientInventoryException;

    Order saveOrder(Order order);

    Order findByOrderCode(String orderCode);

    List<Order> getAllOrders();
    List<Order> getOrdersByBranchId(Long branchId);
    Order getOrderById(int orderId);
    Order getOrderByOrderCode(String orderCode);

    Page<Order> getOrders(Long branchId, OrderStatus status, String orderCode, Pageable pageable);
    Page<Order> getOrdersByCustomerID(int customerID, Pageable pageable);

    Order updateOrderStatus(Order order, OrderStatus newStatus);

    //
    List<BranchRevenueDTO> getBranchRevenueByStatus(OrderStatus status);

    List<BranchRevenueDTO> getBranchRevenueByStatusAndDates(OrderStatus status, Date startDate, Date endDate);


}

