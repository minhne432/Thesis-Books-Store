package com.comestic.shop.service;

import com.comestic.shop.model.PurchaseOrder;
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

            // Cập nhật các thuộc tính khác
            supplier.setSupplierName(supplierDetails.getSupplierName());
            supplier.setContactPerson(supplierDetails.getContactPerson());
            supplier.setPhone(supplierDetails.getPhone());
            supplier.setEmail(supplierDetails.getEmail());
            supplier.setAddress(supplierDetails.getAddress());

            // Cập nhật danh sách purchaseOrders một cách cẩn thận
            List<PurchaseOrder> existingPurchaseOrders = supplier.getPurchaseOrders();

            // Xóa những đơn hàng cũ không còn tồn tại trong danh sách mới
            existingPurchaseOrders.removeIf(existingOrder ->
                    !supplierDetails.getPurchaseOrders().contains(existingOrder)
            );

            // Thêm những đơn hàng mới không có trong danh sách hiện tại
            for (PurchaseOrder newOrder : supplierDetails.getPurchaseOrders()) {
                if (!existingPurchaseOrders.contains(newOrder)) {
                    existingPurchaseOrders.add(newOrder);
                    newOrder.setSupplier(supplier); // Đảm bảo thiết lập liên kết đúng
                }
            }

            // Lưu supplier sau khi cập nhật
            return supplierRepository.save(supplier);
        } else {
            return null; // Hoặc ném ngoại lệ nếu muốn
        }
    }


    public void deleteSupplier(Long id) {
        supplierRepository.deleteById(id);
    }
}
