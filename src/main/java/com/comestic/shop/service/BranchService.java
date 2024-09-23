package com.comestic.shop.service;

import com.comestic.shop.model.Branch;
import com.comestic.shop.repository.BranchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BranchService {

    @Autowired
    private BranchRepository branchRepository;

    public List<Branch> getAllBranches() {
        return branchRepository.findAll();
    }

    public Optional<Branch> getBranchById(Long id) {
        return branchRepository.findById(id);
    }

    public Branch addBranch(Branch branch) {
        return branchRepository.save(branch);
    }

    public Branch updateBranch(Long id, Branch branchDetails) {
        Optional<Branch> optionalBranch = branchRepository.findById(id);
        if (optionalBranch.isPresent()) {
            Branch branch = optionalBranch.get();

            branch.setBranchName(branchDetails.getBranchName());
            branch.setLocation(branchDetails.getLocation());
            branch.setLatitude(branchDetails.getLatitude());
            branch.setLongitude(branchDetails.getLongitude());
            branch.setContactNumber(branchDetails.getContactNumber());
            branch.setManager(branchDetails.getManager());

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


    public void deleteBranch(Long id) {
        branchRepository.deleteById(id);
    }
}
