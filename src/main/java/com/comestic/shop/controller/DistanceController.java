package com.comestic.shop.controller;

import com.comestic.shop.service.DistanceCalculationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/distance")
public class DistanceController {

    @Autowired
    private DistanceCalculationService distanceCalculationService;

    // Endpoint để khởi động quá trình tính toán khoảng cách và hiển thị kết quả trên giao diện
    @GetMapping("/calculate")
    public String calculateAndSaveDistances(Model model) {
        // Gọi service để thực hiện tính toán và lưu khoảng cách
        distanceCalculationService.calculateAndSaveDistances();

        // Thêm thông báo vào model để hiển thị trên giao diện
        model.addAttribute("message", "Khoảng cách giữa các chi nhánh và các khu vực đã được tính toán và lưu vào cơ sở dữ liệu.");

        // Trả về tên template Thymeleaf
        return "distance/distance_result";
    }
}
