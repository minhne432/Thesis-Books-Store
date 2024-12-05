package com.comestic.shop.controller;

import com.comestic.shop.model.Coupon;
import com.comestic.shop.service.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequestMapping("/coupons")
public class CouponController {

    @Autowired
    private CouponService couponService;

    // Hiển thị danh sách tất cả các coupon
    @GetMapping
    public String getAllCoupons(Model model) {
        List<Coupon> coupons = couponService.getAllCoupons();
        model.addAttribute("coupons", coupons);
        return "coupon/list";
    }

    // Hiển thị form tạo mới coupon
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("coupon", new Coupon());
        return "coupon/create";
    }

    // Xử lý việc tạo mới coupon
    @PostMapping
    public String createCoupon(@ModelAttribute("coupon") Coupon coupon) {
        couponService.addCoupon(coupon);
        return "redirect:/coupons";
    }

    // Hiển thị form chỉnh sửa coupon
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        Coupon coupon = couponService.getCouponById(id).orElse(null);
        if (coupon == null) {
            // Xử lý khi coupon không tồn tại
            return "redirect:/coupons";
        }
        model.addAttribute("coupon", coupon);
        return "coupon/edit";
    }

    // Xử lý việc cập nhật coupon
    @PostMapping("/update/{id}")
    public String updateCoupon(@PathVariable("id") Long id, @ModelAttribute("coupon") Coupon couponDetails) {
        couponService.updateCoupon(id, couponDetails);
        return "redirect:/coupons";
    }

    // Xóa coupon
    @GetMapping("/delete/{id}")
    public String deleteCoupon(@PathVariable("id") Long id) {
        couponService.deleteCoupon(id);
        return "redirect:/coupons";
    }
}
