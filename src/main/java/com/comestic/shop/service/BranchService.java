package com.comestic.shop.service;

import com.comestic.shop.model.Branch;
import com.comestic.shop.model.Address;
import com.comestic.shop.repository.BranchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BranchService {

    @Autowired
    private BranchRepository branchRepository;

    // Lấy tất cả các chi nhánh
    public List<Branch> getAllBranches() {
        return branchRepository.findAll();
    }

    // Lấy chi nhánh theo ID
    public Optional<Branch> getBranchById(Long id) {
        return branchRepository.findById(id);
    }

    // Thêm chi nhánh mới
    public Branch addBranch(Branch branch) {
        return branchRepository.save(branch);
    }

    // Cập nhật chi nhánh
    public Branch updateBranch(Long id, Branch branchDetails) {
        Optional<Branch> optionalBranch = branchRepository.findById(id);
        if (optionalBranch.isPresent()) {
            Branch branch = optionalBranch.get();

            branch.setBranchName(branchDetails.getBranchName());
            branch.setContactNumber(branchDetails.getContactNumber());
            branch.setManager(branchDetails.getManager());

            // Cập nhật địa chỉ của chi nhánh
            Address updatedAddress = branchDetails.getAddress();
            branch.setAddress(updatedAddress);

            // Cập nhật inventories thay vì thay thế toàn bộ
            branch.getInventories().clear();  // Xóa các phần tử hiện tại
            branch.getInventories().addAll(branchDetails.getInventories());  // Thêm các phần tử mới

            // Cập nhật orders thay vì thay thế toàn bộ
            branch.getOrders().clear();  // Xóa các phần tử hiện tại
            branch.getOrders().addAll(branchDetails.getOrders());  // Thêm các phần tử mới

            return branchRepository.save(branch);
        } else {
            return null; // Hoặc ném ra ngoại lệ tùy theo logic của bạn
        }
    }

    // Xóa chi nhánh
    public void deleteBranch(Long id) {
        branchRepository.deleteById(id);
    }
}
