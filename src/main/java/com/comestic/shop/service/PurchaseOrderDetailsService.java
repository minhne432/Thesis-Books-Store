package com.comestic.shop.service;

import com.comestic.shop.model.PurchaseOrderDetails;
import com.comestic.shop.repository.PurchaseOrderDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PurchaseOrderDetailsService {

    @Autowired
    private PurchaseOrderDetailsRepository purchaseOrderDetailsRepository;

    public List<PurchaseOrderDetails> getAllPurchaseOrderDetails() {
        return purchaseOrderDetailsRepository.findAll();
    }

    public Optional<PurchaseOrderDetails> getPurchaseOrderDetailsById(Long id) {
        return purchaseOrderDetailsRepository.findById(id);
    }

    public PurchaseOrderDetails addPurchaseOrderDetails(PurchaseOrderDetails purchaseOrderDetails) {
        return purchaseOrderDetailsRepository.save(purchaseOrderDetails);
    }

    public PurchaseOrderDetails updatePurchaseOrderDetails(Long id, PurchaseOrderDetails purchaseOrderDetailsDetails) {
        Optional<PurchaseOrderDetails> optionalPurchaseOrderDetails = purchaseOrderDetailsRepository.findById(id);
        if (optionalPurchaseOrderDetails.isPresent()) {
            PurchaseOrderDetails purchaseOrderDetails = optionalPurchaseOrderDetails.get();
            purchaseOrderDetails.setPurchaseOrder(purchaseOrderDetailsDetails.getPurchaseOrder());
            purchaseOrderDetails.setProduct(purchaseOrderDetailsDetails.getProduct());
            purchaseOrderDetails.setQuantity(purchaseOrderDetailsDetails.getQuantity());
            purchaseOrderDetails.setUnitPrice(purchaseOrderDetailsDetails.getUnitPrice());
            purchaseOrderDetails.setReceivedDate(purchaseOrderDetailsDetails.getReceivedDate());
            return purchaseOrderDetailsRepository.save(purchaseOrderDetails);
        } else {
            return null; // Hoặc bạn có thể ném ra ngoại lệ tùy theo logic
        }
    }

    public void deletePurchaseOrderDetails(Long id) {
        purchaseOrderDetailsRepository.deleteById(id);
    }
}
