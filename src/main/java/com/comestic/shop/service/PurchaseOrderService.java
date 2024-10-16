package com.comestic.shop.service;

import com.comestic.shop.exception.ResourceNotFoundException;
import com.comestic.shop.model.Branch;
import com.comestic.shop.model.Product;
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


    @Autowired
    private InventoryService inventoryService;

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

    public void updatePurchaseOrderStatus(Long purchaseOrderId, String newStatus) {
        Optional<PurchaseOrder> optionalPurchaseOrder = purchaseOrderRepository.findById(purchaseOrderId);
        if (optionalPurchaseOrder.isPresent()) {
            PurchaseOrder purchaseOrder = optionalPurchaseOrder.get();
            purchaseOrder.setStatus(newStatus);

            // Kiểm tra nếu trạng thái là "received successfully"
            if ("Received".equalsIgnoreCase(newStatus)) {
                // Gọi phương thức để cập nhật kho
                updateInventoryForReceivedOrder(purchaseOrder);
            }

            purchaseOrderRepository.save(purchaseOrder);
        } else {
            throw new ResourceNotFoundException("PurchaseOrder not found with ID: " + purchaseOrderId);
        }
    }

    private void updateInventoryForReceivedOrder(PurchaseOrder purchaseOrder) {
        // Duyệt qua tất cả các PurchaseOrderDetails
        for (PurchaseOrderDetails detail : purchaseOrder.getPurchaseOrderDetails()) {
            Product product = detail.getProduct();
            Branch branch = purchaseOrder.getBranch();
            int receivedQuantity = detail.getQuantity();

            // Cập nhật Inventory
            inventoryService.increaseInventoryQuantity(branch, product, receivedQuantity);
        }
    }

}
