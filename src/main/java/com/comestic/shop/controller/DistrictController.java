package com.comestic.shop.controller;

import com.comestic.shop.model.District;
import com.comestic.shop.model.Province;
import com.comestic.shop.service.DistrictService;
import com.comestic.shop.service.ProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin/districts")
public class DistrictController {

    @Autowired
    private DistrictService districtService;

    @Autowired
    private ProvinceService provinceService;

    // Hiển thị danh sách tất cả các quận/huyện
    @GetMapping
    public String listDistricts(Model model) {
        model.addAttribute("districts", districtService.getAllDistricts());
        return "admin/district-list";
    }

    // Hiển thị form thêm mới quận/huyện
    @GetMapping("/add")
    public String showAddDistrictForm(Model model) {
        model.addAttribute("district", new District());
        model.addAttribute("provinces", provinceService.getAllProvinces());
        return "admin/district-add";
    }

    // Xử lý lưu quận/huyện mới
    @PostMapping("/add")
    public String addDistrict(@ModelAttribute("district") District district) {
        districtService.saveDistrict(district);
        return "redirect:/admin/districts";
    }

    // Hiển thị form chỉnh sửa quận/huyện
    @GetMapping("/edit/{id}")
    public String showEditDistrictForm(@PathVariable("id") int id, Model model) {
        Optional<District> district = districtService.getDistrictById(id);
        if (district.isPresent()) {
            model.addAttribute("district", district.get());
            model.addAttribute("provinces", provinceService.getAllProvinces());
            return "admin/district-edit";
        } else {
            // Xử lý khi không tìm thấy quận/huyện
            return "redirect:/admin/districts?error=notfound";
        }
    }

    // Xử lý cập nhật quận/huyện
    @PostMapping("/edit")
    public String updateDistrict(@ModelAttribute("district") District district) {
        districtService.saveDistrict(district);
        return "redirect:/admin/districts";
    }

    // Xóa quận/huyện
    @GetMapping("/delete/{id}")
    public String deleteDistrict(@PathVariable("id") int id) {
        districtService.deleteDistrict(id);
        return "redirect:/admin/districts";
    }

    // Lấy danh sách District theo ProvinceID
    @GetMapping("/by-province/{provinceId}")
    @ResponseBody // Đảm bảo rằng phương thức này trả về JSON
    public List<District> getDistrictsByProvinceId(@PathVariable int provinceId) {
        return districtService.getDistrictsByProvinceId(provinceId);
    }
}
