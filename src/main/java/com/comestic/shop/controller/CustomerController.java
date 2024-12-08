package com.comestic.shop.controller;

import com.comestic.shop.dto.AddressForm;
import com.comestic.shop.model.*;
import com.comestic.shop.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
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
    // 1. List all customers
    @GetMapping("/customers")
    public String listCustomers(Model model,
                                @RequestParam("page") Optional<Integer> page,
                                @RequestParam("size") Optional<Integer> size) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(10);

        Page<Customer> customerPage = customerService.findPaginated(PageRequest.of(currentPage - 1, pageSize));

        model.addAttribute("customerPage", customerPage);

        return "customer/customer_list";
    }

    // 2. Update isActive status
    @PostMapping("/customers/updateStatus")
    public String updateCustomerStatus(@RequestParam("customerId") int customerId,
                                       @RequestParam("isActive") boolean isActive) {
        Optional<Customer> optionalCustomer = customerService.getCustomerById(customerId);
        if (optionalCustomer.isPresent()) {
            Customer customer = optionalCustomer.get();
            customer.setActive(isActive);
            customerService.saveCustomer(customer);
        }
        return "redirect:/customers";
    }

    // 3. View customer details
    @GetMapping("/customers/{id}")
    public String viewCustomerDetails(@PathVariable("id") int id, Model model) {
        Optional<Customer> optionalCustomer = customerService.getCustomerById(id);
        if (optionalCustomer.isPresent()) {
            model.addAttribute("customer", optionalCustomer.get());
            return "customer/customer_detail";
        } else {
            return "redirect:/customers";
        }
    }
    // new feature
    private Customer getCurrentCustomer() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return customerService.getCustomerByUsername(username)
                .orElseThrow(() -> new RuntimeException("Khách hàng không tồn tại"));
    }
    // Liệt kê danh sách địa chỉ
    @GetMapping("/customer/addresses")
    public String listCustomerAddresses(Model model) {
        Customer currentCustomer = getCurrentCustomer();
        List<CustomerAddress> customerAddresses = currentCustomer.getCustomerAddresses();
        model.addAttribute("customerAddresses", customerAddresses);
        return "customer/customer_addresses";
    }

    // Hiển thị form thêm địa chỉ mới
    @GetMapping("/customer/addresses/new")
    public String showAddAddressForm(Model model) {
        // Load danh sách provinces
        List<Province> provinces = provinceService.getAllProvinces();
        model.addAttribute("provinces", provinces);
        // Tạo một object để binding data của form
        model.addAttribute("addressForm", new AddressForm());
        return "customer/customer_add_address";
    }

    // Xử lý thêm địa chỉ mới
    @PostMapping("/customer/addresses/new")
    public String addNewAddress(@ModelAttribute("addressForm") AddressForm addressForm, Model model) {
        Customer customer = getCurrentCustomer();

        // Lấy ward dựa trên wardId
        Ward ward = wardService.getWardById(addressForm.getWardId()).orElse(null);
        if (ward == null) {
            model.addAttribute("addressError", "Please select a valid ward");
            // Load lại danh sách provinces
            List<Province> provinces = provinceService.getAllProvinces();
            model.addAttribute("provinces", provinces);
            return "customer/customer_add_address";
        }

        Address address = new Address();
        address.setWard(ward);
        address.setStreetAddress(addressForm.getStreetAddress());
        address.setPostalCode(addressForm.getPostalCode());
        address.setDefault(false); // Chỗ này tuỳ logic bạn, có thể mặc định là false
        addressService.saveAddress(address);

        CustomerAddress customerAddress = new CustomerAddress();
        customerAddress.setCustomer(customer);
        customerAddress.setAddress(address);
        customerAddress.setDefault(false);
        customerAddressService.addCustomerAddress(customerAddress);

        return "redirect:/profile";
    }

    @PostMapping("/customer/addresses/{id}/default")
    public String setDefaultAddress(@PathVariable("id") Long addressId) {
        Customer currentCustomer = getCurrentCustomer(); // Lấy customer hiện tại

        // Lấy danh sách địa chỉ
        List<CustomerAddress> customerAddresses = currentCustomer.getCustomerAddresses();

        // Kiểm tra xem addressId có thuộc về customer hiện tại không
        Optional<CustomerAddress> optionalAddress = customerAddresses.stream()
                .filter(ca -> ca.getCustomerAddressID().equals(addressId))
                .findFirst();

        if (optionalAddress.isPresent()) {
            // Set tất cả địa chỉ khác về default = false
            for (CustomerAddress ca : customerAddresses) {
                ca.setDefault(false);
                customerAddressService.save(ca);
            }

            // Set địa chỉ được chọn thành default = true
            CustomerAddress selectedAddress = optionalAddress.get();
            selectedAddress.setDefault(true);
            customerAddressService.save(selectedAddress);
        }

        return "redirect:/profile";
    }

    @GetMapping("/profile")
    public String viewProfile(Model model) {
        // Lấy customer hiện tại, giả sử bạn đã có logic getCurrentCustomer()
        Customer currentCustomer = getCurrentCustomer();
        model.addAttribute("customer", currentCustomer);
        return "customers/profile"; // Trả về template profile.html
    }


}
