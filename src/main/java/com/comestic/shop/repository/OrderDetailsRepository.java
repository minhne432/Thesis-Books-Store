package com.comestic.shop.repository;

import com.comestic.shop.dto.CategorySalesDTO;
import com.comestic.shop.dto.ProductSalesDTO;
import com.comestic.shop.dto.ProductSalesDateDTO;
import com.comestic.shop.model.OrderDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface OrderDetailsRepository extends JpaRepository<OrderDetails, Long> {
    // Bạn có thể thêm các phương thức tùy chỉnh nếu cần
    // Add this method to find order details by orderID
    List<OrderDetails> findByOrder_OrderID(int orderID);

    @Query("SELECT new com.comestic.shop.dto.ProductSalesDTO(od.product.productName, SUM(od.quantity)) "
            + "FROM OrderDetails od JOIN od.order o "
            + "WHERE FUNCTION('DATE', o.orderDate) = :date "
            + "GROUP BY od.product.productName "
            + "ORDER BY SUM(od.quantity) DESC")
    List<ProductSalesDTO> findBestSellingProductsByDay(@Param("date") Date date);

    @Query("SELECT new com.comestic.shop.dto.ProductSalesDTO(od.product.productName, SUM(od.quantity)) "
            + "FROM OrderDetails od JOIN od.order o "
            + "WHERE FUNCTION('MONTH', o.orderDate) = :month AND FUNCTION('YEAR', o.orderDate) = :year "
            + "GROUP BY od.product.productName "
            + "ORDER BY SUM(od.quantity) DESC")
    List<ProductSalesDTO> findBestSellingProductsByMonth(@Param("month") int month, @Param("year") int year);

    @Query("SELECT new com.comestic.shop.dto.ProductSalesDTO(od.product.productName, SUM(od.quantity)) "
            + "FROM OrderDetails od JOIN od.order o "
            + "WHERE FUNCTION('YEAR', o.orderDate) = :year "
            + "GROUP BY od.product.productName "
            + "ORDER BY SUM(od.quantity) DESC")
    List<ProductSalesDTO> findBestSellingProductsByYear(@Param("year") int year);

    //
    @Query("SELECT new com.comestic.shop.dto.ProductSalesDateDTO(DATE(o.orderDate), od.product.productName, SUM(od.quantity)) "
            + "FROM OrderDetails od JOIN od.order o "
            + "WHERE o.orderDate BETWEEN :startDate AND :endDate "
            + "GROUP BY DATE(o.orderDate), od.product.productName "
            + "ORDER BY DATE(o.orderDate), SUM(od.quantity) DESC")
    List<ProductSalesDateDTO> findProductSalesBetweenDates(@Param("startDate") Date startDate, @Param("endDate") Date endDate);


    @Query("SELECT new com.comestic.shop.dto.ProductSalesDateDTO(DATE(o.orderDate), od.product.productName, SUM(od.quantity)) "
            + "FROM OrderDetails od JOIN od.order o "
            + "WHERE DATE(o.orderDate) IN :dates "
            + "GROUP BY DATE(o.orderDate), od.product.productName "
            + "ORDER BY DATE(o.orderDate), SUM(od.quantity) DESC")
    List<ProductSalesDateDTO> findProductSalesByDates(@Param("dates") List<Date> dates);


    //thong ke danh muc ban duoc
    @Query("SELECT new com.comestic.shop.dto.CategorySalesDTO(od.product.category.categoryName, SUM(od.quantity)) "
            + "FROM OrderDetails od "
            + "GROUP BY od.product.category.categoryName "
            + "ORDER BY SUM(od.quantity) DESC")
    List<CategorySalesDTO> findCategorySales();

    @Query("SELECT new com.comestic.shop.dto.CategorySalesDTO(od.product.category.categoryName, SUM(od.quantity)) "
            + "FROM OrderDetails od "
            + "WHERE od.order.orderDate BETWEEN :startDate AND :endDate "
            + "GROUP BY od.product.category.categoryName "
            + "ORDER BY SUM(od.quantity) DESC")
    List<CategorySalesDTO> findCategorySalesBetweenDates(@Param("startDate") Date startDate, @Param("endDate") Date endDate);



}