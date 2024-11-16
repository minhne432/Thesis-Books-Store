package com.comestic.shop.repository;

import com.comestic.shop.model.Order;
import com.comestic.shop.model.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    // Bạn có thể thêm các phương thức tùy chỉnh nếu cần, ví dụ:
    // List<Order> findByCustomerId(int customerId);
    Order findByOrderCode(String orderCode);

    List<Order> findByBranch_BranchId(Long branchId);


    Page<Order> findByBranch_BranchId(Long branchId, Pageable pageable);

    Page<Order> findByStatus(OrderStatus status, Pageable pageable);

    Page<Order> findByOrderCodeContaining(String orderCode, Pageable pageable);

    // Kết hợp các bộ lọc
    Page<Order> findByBranch_BranchIdAndStatusAndOrderCodeContaining(
            Long branchId, OrderStatus status, String orderCode, Pageable pageable);

    Page<Order> findByBranch_BranchIdAndStatus(
            Long branchId, OrderStatus status, Pageable pageable);

    Page<Order> findByBranch_BranchIdAndOrderCodeContaining(
            Long branchId, String orderCode, Pageable pageable);

    Page<Order> findByStatusAndOrderCodeContaining(
            OrderStatus status, String orderCode, Pageable pageable);

    Page<Order> findAll(Pageable pageable);

}
