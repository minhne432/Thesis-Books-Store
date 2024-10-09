package com.comestic.shop.controller;

import com.comestic.shop.model.*;
import com.comestic.shop.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

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

    // Các phương thức khác như hiển thị danh sách chi nhánh, cập nhật, xóa...
}
