package com.comestic.shop.service;

import com.comestic.shop.model.PurchaseOrder;
import com.comestic.shop.model.PurchaseOrderDetails;
import com.comestic.shop.repository.PurchaseOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PurchaseOrderService {

    @Autowired
    private PurchaseOrderRepository purchaseOrderRepository;

    public List<PurchaseOrder> getAllPurchaseOrders() {
        return purchaseOrderRepository.findAll();
    }

    public Optional<PurchaseOrder> getPurchaseOrderById(Long id) {
        return purchaseOrderRepository.findById(id);
    }

    public PurchaseOrder addPurchaseOrder(PurchaseOrder purchaseOrder) {
        return purchaseOrderRepository.save(purchaseOrder);
    }

    public PurchaseOrder updatePurchaseOrder(Long id, PurchaseOrder purchaseOrderDetails) {
        Optional<PurchaseOrder> optionalPurchaseOrder = purchaseOrderRepository.findById(id);
        if (optionalPurchaseOrder.isPresent()) {
            PurchaseOrder purchaseOrder = optionalPurchaseOrder.get();

            // Cập nhật các thuộc tính của PurchaseOrder
            purchaseOrder.setSupplier(purchaseOrderDetails.getSupplier());
            purchaseOrder.setOrderDate(purchaseOrderDetails.getOrderDate());
            purchaseOrder.setStatus(purchaseOrderDetails.getStatus());

            // Xử lý danh sách purchaseOrderDetails một cách cẩn thận
            List<PurchaseOrderDetails> existingDetails = purchaseOrder.getPurchaseOrderDetails();

            // Xóa các chi tiết đơn hàng không còn trong danh sách mới
            existingDetails.removeIf(existingDetail ->
                    !purchaseOrderDetails.getPurchaseOrderDetails().contains(existingDetail)
            );

            // Thêm các chi tiết đơn hàng mới
            for (PurchaseOrderDetails newDetail : purchaseOrderDetails.getPurchaseOrderDetails()) {
                if (!existingDetails.contains(newDetail)) {
                    purchaseOrder.addPurchaseOrderDetail(newDetail); // Phải thêm qua helper method
                }
            }

            // Cập nhật tổng tiền sau khi thay đổi chi tiết đơn hàng
            purchaseOrder.updateTotalAmount();

            // Lưu purchase order sau khi cập nhật
            return purchaseOrderRepository.save(purchaseOrder);
        } else {
            return null; // Hoặc có thể ném ra ngoại lệ tùy theo logic của bạn
        }
    }


    public void deletePurchaseOrder(Long id) {
        purchaseOrderRepository.deleteById(id);
    }
}
