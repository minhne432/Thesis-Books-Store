package com.comestic.shop.service;

import com.comestic.shop.model.Supplier;
import com.comestic.shop.repository.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SupplierService {

    @Autowired
    private SupplierRepository supplierRepository;

    public List<Supplier> getAllSuppliers() {
        return supplierRepository.findAll();
    }

    public Optional<Supplier> getSupplierById(Long id) {
        return supplierRepository.findById(id);
    }

    public Supplier addSupplier(Supplier supplier) {
        return supplierRepository.save(supplier);
    }

    public Supplier updateSupplier(Long id, Supplier supplierDetails) {
        Optional<Supplier> optionalSupplier = supplierRepository.findById(id);
        if (optionalSupplier.isPresent()) {
            Supplier supplier = optionalSupplier.get();
            supplier.setSupplierName(supplierDetails.getSupplierName());
            supplier.setContactPerson(supplierDetails.getContactPerson());
            supplier.setPhone(supplierDetails.getPhone());
            supplier.setEmail(supplierDetails.getEmail());
            supplier.setAddress(supplierDetails.getAddress());
            supplier.setPurchaseOrders(supplierDetails.getPurchaseOrders());
            return supplierRepository.save(supplier);
        } else {
            return null; // Hoặc bạn có thể ném ra ngoại lệ tùy theo logic
        }
    }

    public void deleteSupplier(Long id) {
        supplierRepository.deleteById(id);
    }
}
