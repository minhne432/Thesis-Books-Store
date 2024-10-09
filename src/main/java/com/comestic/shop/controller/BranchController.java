package com.comestic.shop.controller;

import com.comestic.shop.model.*;
import com.comestic.shop.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/branches")
public class BranchController {

    @Autowired
    private BranchService branchService;

    @Autowired
    private AddressService addressService;

    @Autowired
    private ProvinceService provinceService;

    @Autowired
    private DistrictService districtService;

    @Autowired
    private WardService wardService;


    // Hiển thị danh sách chi nhánh
    @GetMapping
    public String listBranches(Model model) {
        List<Branch> branches = branchService.getAllBranches();
        model.addAttribute("branches", branches);
        return "branch/branch-list";  // Trả về view branch-list.html
    }

    // Hiển thị form tạo chi nhánh mới
    @GetMapping("/create")
    public String showCreateBranchForm(Model model) {
        model.addAttribute("branch", new Branch());
        model.addAttribute("address", new Address());

        // Lấy danh sách các tỉnh/thành phố
        List<Province> provinces = provinceService.getAllProvinces();
        model.addAttribute("provinces", provinces);

        return "branch/add-branch";
    }

    // Xử lý dữ liệu khi người dùng gửi form
    @PostMapping("/create")
    public String createBranch(@ModelAttribute Branch branch,
                               @ModelAttribute Address address,
                               @RequestParam("provinceId") int provinceId,
                               @RequestParam("districtId") int districtId,
                               @RequestParam("wardId") int wardId,
                               RedirectAttributes redirectAttributes) {
        // Lấy thông tin Ward dựa trên wardId
        Ward ward = wardService.getWardById(wardId).orElse(null);
        if (ward == null) {
            redirectAttributes.addFlashAttribute("error", "Phường/Xã không hợp lệ!");
            return "redirect:/branches/create";
        }

        // Thiết lập thông tin địa chỉ
        address.setWard(ward);
        addressService.saveAddress(address);

        // Thiết lập địa chỉ cho chi nhánh
        branch.setAddress(address);

        // Lưu chi nhánh
        branchService.addBranch(branch);

        redirectAttributes.addFlashAttribute("success", "Thêm chi nhánh mới thành công!");
        return "redirect:/branches";
    }
    // Hiển thị form chỉnh sửa chi nhánh
    @GetMapping("/edit/{id}")
    public String showEditBranchForm(@PathVariable("id") Long branchId, Model model, RedirectAttributes redirectAttributes) {
        // Lấy thông tin chi nhánh
        Optional<Branch> branchOptional = branchService.getBranchById(branchId);
        if (branchOptional.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Chi nhánh không tồn tại");
            return "redirect:/branches";
        }
        Branch branch = branchOptional.get();

        // Thêm thông tin vào model để hiển thị
        model.addAttribute("branch", branch);
        model.addAttribute("address", branch.getAddress());

        // Lấy danh sách các tỉnh/thành phố để hiển thị
        List<Province> provinces = provinceService.getAllProvinces();
        model.addAttribute("provinces", provinces);

        // Lấy tỉnh, quận/huyện và phường/xã hiện tại của chi nhánh
        int provinceId = branch.getAddress().getWard().getDistrict().getProvince().getProvinceID();
        int districtId = branch.getAddress().getWard().getDistrict().getDistrictID();
        int wardId = branch.getAddress().getWard().getWardID();

        // Lấy danh sách quận/huyện thuộc tỉnh hiện tại
        List<District> districts = districtService.getDistrictsByProvinceId(provinceId);
        model.addAttribute("districts", districts);

        // Lấy danh sách phường/xã thuộc quận/huyện hiện tại
        List<Ward> wards = wardService.getWardsByDistrictId(districtId);
        model.addAttribute("wards", wards);

        return "branch/edit-branch";  // Chuyển đến view edit-branch.html
    }


    // Xử lý cập nhật chi nhánh
    @PostMapping("/edit")
    public String updateBranch(@ModelAttribute Branch branch,
                               @ModelAttribute Address address,
                               @RequestParam("provinceId") int provinceId,
                               @RequestParam("districtId") int districtId,
                               @RequestParam("wardId") int wardId,
                               RedirectAttributes redirectAttributes) {
        // Kiểm tra thông tin Ward
        Optional<Ward> wardOptional = wardService.getWardById(wardId);
        if (wardOptional.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Phường/Xã không hợp lệ!");
            return "redirect:/branches/edit/" + branch.getBranchId();
        }
        Ward ward = wardOptional.get();

        // Cập nhật thông tin địa chỉ
        address.setWard(ward);
        addressService.saveAddress(address);

        // Cập nhật chi nhánh với địa chỉ mới
        branch.setAddress(address);
        branchService.updateBranch(branch.getBranchId(), branch);

        redirectAttributes.addFlashAttribute("success", "Cập nhật chi nhánh thành công!");
        return "redirect:/branches";
    }
}
