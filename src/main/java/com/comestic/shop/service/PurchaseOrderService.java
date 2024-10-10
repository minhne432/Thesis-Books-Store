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

    public PurchaseOrder updatePurchaseOrder(Long id, PurchaseOrder updatedPurchaseOrder) {
        Optional<PurchaseOrder> optionalPurchaseOrder = purchaseOrderRepository.findById(id);
        if (optionalPurchaseOrder.isPresent()) {
            PurchaseOrder existingPurchaseOrder = optionalPurchaseOrder.get();

            // Cập nhật các thuộc tính cơ bản
            existingPurchaseOrder.setSupplier(updatedPurchaseOrder.getSupplier());
            existingPurchaseOrder.setOrderDate(updatedPurchaseOrder.getOrderDate());
            existingPurchaseOrder.setTotalAmount(updatedPurchaseOrder.getTotalAmount());
            existingPurchaseOrder.setStatus(updatedPurchaseOrder.getStatus());

            // Cập nhật danh sách PurchaseOrderDetails
            List<PurchaseOrderDetails> newDetails = updatedPurchaseOrder.getPurchaseOrderDetails();
            List<PurchaseOrderDetails> existingDetails = existingPurchaseOrder.getPurchaseOrderDetails();

            // Xóa các chi tiết không còn trong danh sách mới
            existingDetails.removeIf(existingDetail -> !newDetails.contains(existingDetail));

            // Thêm hoặc cập nhật các chi tiết mới
            for (PurchaseOrderDetails newDetail : newDetails) {
                if (!existingDetails.contains(newDetail)) {
                    // Thêm chi tiết mới
                    existingPurchaseOrder.addPurchaseOrderDetail(newDetail);
                } else {
                    // Cập nhật chi tiết đã có (nếu có logic cần cập nhật)
                    // Tùy vào yêu cầu, bạn có thể thêm logic cập nhật thuộc tính của PurchaseOrderDetails tại đây
                }
            }

            // Cập nhật tổng số tiền sau khi cập nhật chi tiết
            existingPurchaseOrder.updateTotalAmount();

            // Lưu PurchaseOrder đã được cập nhật
            return purchaseOrderRepository.save(existingPurchaseOrder);
        } else {
            return null; // Hoặc bạn có thể ném ra ngoại lệ tùy vào yêu cầu
        }
    }


    public void deletePurchaseOrder(Long id) {
        purchaseOrderRepository.deleteById(id);
    }

}
