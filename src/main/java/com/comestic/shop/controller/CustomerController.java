package com.comestic.shop.controller;

import com.comestic.shop.model.*;
import com.comestic.shop.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private ProvinceService provinceService;

    @Autowired
    private WardService wardService;

    @Autowired
    private AddressService addressService;

    @Autowired
    private CustomerAddressService customerAddressService;

    // GET method to show the registration form
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("customer", new Customer());
        List<Province> provinces = provinceService.getAllProvinces();
        model.addAttribute("provinces", provinces);
        return "register";
    }

    // POST method to handle form submission for user registration
    @PostMapping("/register")
    public String registerCustomer(@ModelAttribute("customer") @Valid Customer customer,
                                   BindingResult result,
                                   @RequestParam("provinceId") int provinceId,
                                   @RequestParam("districtId") int districtId,
                                   @RequestParam("wardId") int wardId,
                                   @RequestParam("streetAddress") String streetAddress,
                                   @RequestParam("postalCode") String postalCode,
                                   Model model) {
        // Kiểm tra lỗi
        if (result.hasErrors()) {
            // Load lại danh sách provinces nếu có lỗi
            List<Province> provinces = provinceService.getAllProvinces();
            model.addAttribute("provinces", provinces);
            return "register";
        }

        // Kiểm tra email đã tồn tại
        if (customerService.emailExists(customer.getEmail())) {
            model.addAttribute("emailError", "Email đã được đăng ký");
            // Load lại danh sách provinces nếu có lỗi
            List<Province> provinces = provinceService.getAllProvinces();
            model.addAttribute("provinces", provinces);
            return "register";
        }

        // Lưu thông tin khách hàng
        customerService.saveCustomer(customer);

        // Lưu thông tin địa chỉ
        // Lấy thông tin ward
        Ward ward = wardService.getWardById(wardId).orElse(null);
        if (ward == null) {
            model.addAttribute("addressError", "Vui lòng chọn phường/xã hợp lệ");
            // Load lại danh sách provinces nếu có lỗi
            List<Province> provinces = provinceService.getAllProvinces();
            model.addAttribute("provinces", provinces);
            return "register";
        }

        Address address = new Address();
        address.setWard(ward);
        address.setStreetAddress(streetAddress);
        address.setPostalCode(postalCode);
        address.setDefault(true); // Đặt địa chỉ này là mặc định
        addressService.saveAddress(address);

        // Tạo CustomerAddress để liên kết Customer và Address
        CustomerAddress customerAddress = new CustomerAddress();
        customerAddress.setCustomer(customer);
        customerAddress.setAddress(address);
        customerAddress.setDefault(true);
        customerAddressService.addCustomerAddress(customerAddress);

        // Redirect đến trang đăng nhập hoặc trang khác sau khi đăng ký thành công
        return "redirect:/login";
    }

    // Các phương thức khác
}
