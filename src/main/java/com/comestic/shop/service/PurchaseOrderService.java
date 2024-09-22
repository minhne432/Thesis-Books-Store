package com.comestic.shop.service;

import com.comestic.shop.model.PurchaseOrder;
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
            purchaseOrder.setSupplier(purchaseOrderDetails.getSupplier());
            purchaseOrder.setOrderDate(purchaseOrderDetails.getOrderDate());
            purchaseOrder.setTotalAmount(purchaseOrderDetails.getTotalAmount());
            purchaseOrder.setStatus(purchaseOrderDetails.getStatus());
            purchaseOrder.setPurchaseOrderDetails(purchaseOrderDetails.getPurchaseOrderDetails());
            purchaseOrder.updateTotalAmount();
            return purchaseOrderRepository.save(purchaseOrder);
        } else {
            return null; // Hoặc bạn có thể ném ra ngoại lệ tùy theo logic
        }
    }

    public void deletePurchaseOrder(Long id) {
        purchaseOrderRepository.deleteById(id);
    }
}
