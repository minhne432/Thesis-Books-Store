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
    @Autowired
    private ProductService productService;
    // Các service khác...

    @Override
    @Transactional
    public Order placeOrder(OrderDTO orderDto) throws InsufficientInventoryException {
        // Tạo đối tượng Order
        Order order = new Order();
        order.setOrderDate(new Date());
        order.setCustomer(orderDto.getCustomer());
        order.setTotalAmount(orderDto.getTotalAmount());
        order.setStatus("PLACED");
        order.setPaymentMethod(orderDto.getPaymentMethod());
        order.setShippingAddress(orderDto.getAddress());
        order.setBranch(orderDto.getBranch());

        // Tạo danh sách OrderDetails
        List<OrderDetails> orderDetailsList = new ArrayList<>();
        for (OrderItemDTO itemDto : orderDto.getItems()) {
            OrderDetails orderDetails = new OrderDetails();
            Optional<Product> productOpt = productService.getProductById(itemDto.getProductId());


            if(productOpt.isPresent()){
                Product product = productOpt.get();
                // Giảm số lượng tồn kho
                inventoryService.decreaseInventoryQuantity(orderDto.getBranch(), product, itemDto.getQuantity());

                orderDetails.setProduct(product);
                orderDetails.setQuantity(itemDto.getQuantity());
                orderDetails.setUnitPrice(product.getPrice());
                orderDetails.setOrder(order);

                orderDetailsList.add(orderDetails);
            }

        }

        order.setOrderDetails(orderDetailsList);

        // Lưu Order (cascading sẽ lưu cả OrderDetails)
        Order savedOrder = orderRepository.save(order);

        // Xử lý thanh toán nếu cần
        // ...

        return savedOrder;
    }
}
