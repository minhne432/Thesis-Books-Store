package com.comestic.shop.controller;

import com.comestic.shop.dto.DistrictDTO;
import com.comestic.shop.model.Ward;
import com.comestic.shop.model.District;
import com.comestic.shop.service.ProvinceService;
import com.comestic.shop.service.WardService;
import com.comestic.shop.service.DistrictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin/wards")
public class WardController {

    @Autowired
    private ProvinceService provinceService;

    @Autowired
    private WardService wardService;

    @Autowired
    private DistrictService districtService;

    // Hiển thị danh sách tất cả các phường/xã
    @GetMapping
    public String listWards(Model model) {
        model.addAttribute("wards", wardService.getAllWards());
        return "admin/ward-list";
    }


    @GetMapping("/districts-by-province/{provinceId}")
    @ResponseBody
    public List<DistrictDTO> getDistrictsByProvince(@PathVariable("provinceId") int provinceId) {
        List<District> districts = districtService.getDistrictsByProvinceId(provinceId);
        List<DistrictDTO> districtDTOs = new ArrayList<>();
        for (District district : districts) {
            districtDTOs.add(new DistrictDTO(district.getDistrictID(), district.getDistrictName()));
        }
        return districtDTOs;
    }


    // Hiển thị form thêm mới phường/xã
    @GetMapping("/add")
    public String showAddWardForm(Model model) {
        Ward ward = new Ward();
        ward.setDistrict(new District()); // Đảm bảo district không null
        model.addAttribute("ward", ward);
        model.addAttribute("provinces", provinceService.getAllProvinces());
        return "admin/ward-add";
    }


    // Xử lý lưu phường/xã mới
    @PostMapping("/add")
    public String addWard(@ModelAttribute("ward") Ward ward) {
        if (ward.getDistrict() == null || ward.getDistrict().getDistrictID() == 0) {
            // Nếu district hoặc districtID bị null hoặc districtID là 0, trả về lỗi
            return "redirect:/admin/wards/add?error=invalidDistrict";
        }

        // Lấy thông tin District từ service nếu tìm thấy ID hợp lệ
        Optional<District> districtOptional = districtService.getDistrictById(ward.getDistrict().getDistrictID());
        if (districtOptional.isPresent()) {
            ward.setDistrict(districtOptional.get());
            wardService.saveWard(ward);
            return "redirect:/admin/wards";
        } else {
            // Nếu không tìm thấy District theo ID
            return "redirect:/admin/wards/add?error=districtNotFound";
        }
    }



    // Hiển thị form chỉnh sửa phường/xã
    @GetMapping("/edit/{id}")
    public String showEditWardForm(@PathVariable("id") int id, Model model) {
        Optional<Ward> ward = wardService.getWardById(id);
        if (ward.isPresent()) {
            model.addAttribute("ward", ward.get());
            model.addAttribute("districts", districtService.getAllDistricts());
            return "admin/ward-edit";
        } else {
            // Xử lý khi không tìm thấy phường/xã
            return "redirect:/admin/wards?error=notfound";
        }
    }

    // Xử lý cập nhật phường/xã
    @PostMapping("/edit")
    public String updateWard(@ModelAttribute("ward") Ward ward) {
        wardService.saveWard(ward);
        return "redirect:/admin/wards";
    }

    // Xóa phường/xã
    @GetMapping("/delete/{id}")
    public String deleteWard(@PathVariable("id") int id) {
        wardService.deleteWard(id);
        return "redirect:/admin/wards";
    }
}
