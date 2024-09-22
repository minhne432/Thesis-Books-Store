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
            branch.setInventories(branchDetails.getInventories());
            branch.setOrders(branchDetails.getOrders());
            return branchRepository.save(branch);
        } else {
            return null; // Hoặc bạn có thể ném ra ngoại lệ tùy theo logic
        }
    }

    public void deleteBranch(Long id) {
        branchRepository.deleteById(id);
    }
}
