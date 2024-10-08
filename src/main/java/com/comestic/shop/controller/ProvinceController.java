package com.comestic.shop.controller;

import com.comestic.shop.model.Province;
import com.comestic.shop.service.ProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@Controller
@RequestMapping("/admin/provinces")
public class ProvinceController {

    @Autowired
    private ProvinceService provinceService;

    // Hiển thị danh sách tất cả các tỉnh
    @GetMapping
    public String listProvinces(Model model) {
        model.addAttribute("provinces", provinceService.getAllProvinces());
        return "admin/province-list";
    }

    // Hiển thị form thêm mới tỉnh
    @GetMapping("/add")
    public String showAddProvinceForm(Model model) {
        model.addAttribute("province", new Province());
        return "admin/province-add";
    }

    // Xử lý lưu tỉnh mới
    @PostMapping("/add")
    public String addProvince(@ModelAttribute("province") Province province) {
        provinceService.saveProvince(province);
        return "redirect:/admin/provinces";
    }

    // Hiển thị form chỉnh sửa tỉnh
    @GetMapping("/edit/{id}")
    public String showEditProvinceForm(@PathVariable("id") int id, Model model) {
        Optional<Province> province = provinceService.getProvinceById(id);
        if (province.isPresent()) {
            model.addAttribute("province", province.get());
            return "admin/province-edit";
        } else {
            // Xử lý khi không tìm thấy tỉnh
            return "redirect:/admin/provinces?error=notfound";
        }
    }

    // Xử lý cập nhật tỉnh
    @PostMapping("/edit")
    public String updateProvince(@ModelAttribute("province") Province province) {
        provinceService.saveProvince(province);
        return "redirect:/admin/provinces";
    }

    // Xóa tỉnh
    @GetMapping("/delete/{id}")
    public String deleteProvince(@PathVariable("id") int id) {
        provinceService.deleteProvince(id);
        return "redirect:/admin/provinces";
    }
}
