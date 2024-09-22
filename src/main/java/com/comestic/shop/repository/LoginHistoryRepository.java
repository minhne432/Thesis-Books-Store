package com.comestic.shop.repository;

import com.comestic.shop.model.LoginHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoginHistoryRepository extends JpaRepository<LoginHistory, Long> {
    // Sửa lại phương thức để truy cập đúng thuộc tính
//    List<LoginHistory> findByCustomerId(Long customerId);
    List<LoginHistory> findByCustomer_CustomerID(Long customerId);

}
