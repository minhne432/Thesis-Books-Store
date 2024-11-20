package com.comestic.shop.service;

import com.comestic.shop.model.Branch;
import com.comestic.shop.model.Address;
import com.comestic.shop.repository.BranchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BranchService {

    @Autowired
    private BranchRepository branchRepository;


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

    // Lấy tất cả các chi nhánh không phân trang
    public List<Branch> getAllBranches() {
        return branchRepository.findAll();
    }


    // Lấy tất cả các chi nhánh với phân trang
    public Page<Branch> getAllBranches(Pageable pageable) {
        return branchRepository.findAll(pageable);
    }

    // Tìm kiếm chi nhánh theo tên với phân trang
    public Page<Branch> searchBranchesByName(String keyword, Pageable pageable) {
        return branchRepository.findByBranchNameContainingIgnoreCase(keyword, pageable);
    }

    // Tìm chi nhánh theo ID
    public Optional<Branch> getBranchById(Long id) {
        return branchRepository.findByBranchId(id);
    }
}
