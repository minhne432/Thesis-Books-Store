package com.comestic.shop.controller;

import com.comestic.shop.config.VNPayConfig;
import com.comestic.shop.dto.CartUpdateForm;
import com.comestic.shop.dto.CartUpdateForm.CartItemForm;
import com.comestic.shop.exception.InsufficientInventoryException;
import com.comestic.shop.model.*;
import com.comestic.shop.service.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

@Controller
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;
    private final CustomerService customerService;
    private final VNPayConfig vnpayConfig;

    private final CouponService couponService;

    private final CouponUsageService couponUsageService;
    private final OrderService orderService;

    @Autowired
    public CartController(CartService cartService, CustomerService customerService, VNPayConfig vnpayConfig, OrderService orderService, CouponService couponService, CouponUsageService couponUsageService) {
        this.cartService = cartService;
        this.customerService = customerService;
        this.vnpayConfig = vnpayConfig;
        this.orderService = orderService;
        this.couponService = couponService;
        this.couponUsageService = couponUsageService;
    }

    /**
     * Retrieves the currently authenticated customer.
     *
     * @return Customer object.
     */
    private Customer getCurrentCustomer() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return customerService.getCustomerByUsername(username)
                .orElseThrow(() -> new RuntimeException("Khách hàng không tồn tại"));
    }

    /**
     * Displays the cart with current items and total amount.
     *
     * @param model Spring Model to pass attributes to the view.
     * @return Thymeleaf template name.
     */
    @GetMapping
    public String viewCart(Model model) {
        Customer customer = getCurrentCustomer();
        List<CartItem> cartItems = cartService.getCartItems(customer);
        double totalAmount = cartService.calculateTotalAmount(customer);

        // Initialize CartUpdateForm with existing cart items
        CartUpdateForm cartUpdateForm = new CartUpdateForm();
        List<CartItemForm> cartItemForms = new ArrayList<>();
        for (CartItem item : cartItems) {
            CartItemForm form = new CartItemForm();
            form.setCartItemId(item.getCartItemID());
            form.setQuantity(item.getQuantity());
            cartItemForms.add(form);
        }
        cartUpdateForm.setCartItems(cartItemForms);

        // Add attributes to the model
        model.addAttribute("cartUpdateForm", cartUpdateForm);
        model.addAttribute("cartItems", cartItems);
        model.addAttribute("totalAmount", totalAmount);
        return "cart/cart";
    }

    /**
     * Handles updating cart items based on user input.
     *
     * @param cartUpdateForm Form data containing updated cart items.
     * @return Redirects to the cart page.
     */
    @PostMapping("/update")
    public String updateCartItems(@ModelAttribute CartUpdateForm cartUpdateForm) {
        for (CartUpdateForm.CartItemForm itemForm : cartUpdateForm.getCartItems()) {
            cartService.updateCartItem(itemForm.getCartItemId(), itemForm.getQuantity());
        }
        return "redirect:/cart";
    }

    /**
     * Adds a product to the cart.
     *
     * @param productId ID of the product to add.
     * @param quantity  Quantity to add.
     * @return Redirects to the cart page.
     */
    @PostMapping("/add")
    public String addProductToCart(@RequestParam("productId") int productId,
                                   @RequestParam("quantity") int quantity) {
        Customer customer = getCurrentCustomer();
        cartService.addProductToCart(customer, productId, quantity);
        return "redirect:/cart";
    }

    /**
     * Removes an item from the cart.
     *
     * @param cartItemId ID of the cart item to remove.
     * @return Redirects to the cart page.
     */
    @GetMapping("/remove/{cartItemId}")
    public String removeCartItem(@PathVariable int cartItemId) {
        cartService.removeCartItem(cartItemId);
        return "redirect:/cart";
    }

    /**
     * Initiates the checkout process by creating a VN Pay payment URL and redirecting the user.
     *
     * @param model   Spring Model to pass attributes to the view.
     * @param request HttpServletRequest to get client IP address.
     * @return Redirect URL to VN Pay.
     */
    @PostMapping("/checkout")
    public RedirectView createPayment(Model model, HttpServletRequest request) {

        Customer customer = getCurrentCustomer();

        List<CartItem> cartItems = cartService.getCartItems(customer);

        if (cartItems.isEmpty()) {
            // Handle empty cart scenario
            model.addAttribute("message", "Giỏ hàng của bạn đang trống.");
            return new RedirectView("/cart");
        }

        double amount = cartService.calculateTotalAmount(customer);

        String vnp_Version = "2.1.0";
        String vnp_Command = "pay";
        String orderType = "other";

        long amountInVND = (long) (amount * 100);
        String bankCode = "";

        String vnp_TxnRef = vnpayConfig.getRandomNumber(8);
        String vnp_IpAddr = vnpayConfig.getIpAddress(request);

        String vnp_TmnCode = vnpayConfig.getVnp_TmnCode();

        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", vnp_Version);
        vnp_Params.put("vnp_Command", vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(amountInVND));
        vnp_Params.put("vnp_CurrCode", "VND");

        if (bankCode != null && !bankCode.isEmpty()) {
            vnp_Params.put("vnp_BankCode", bankCode);
        }

        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", "Thanh toan don hang: " + vnp_TxnRef);
        vnp_Params.put("vnp_OrderType", orderType);

        // Locale setting
        vnp_Params.put("vnp_Locale", "vn");

        vnp_Params.put("vnp_ReturnUrl", vnpayConfig.getVnp_ReturnUrl());
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);

// Create date
        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);


        // Expire date
        cld.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

        // Build data to hash and query
        List<String> fieldNames = new ArrayList<>(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        try {
            for (Iterator<String> itr = fieldNames.iterator(); itr.hasNext(); ) {
                String fieldName = itr.next();
                String fieldValue = vnp_Params.get(fieldName);
                if (fieldValue != null && !fieldValue.isEmpty()) {
                    // Build hash data
                    hashData.append(fieldName);
                    hashData.append('=');
                    hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.UTF_8.toString()));

                    // Build query
                    query.append(URLEncoder.encode(fieldName, StandardCharsets.UTF_8.toString()));
                    query.append('=');
                    query.append(URLEncoder.encode(fieldValue, StandardCharsets.UTF_8.toString()));
                    if (itr.hasNext()) {
                        hashData.append('&');
                        query.append('&');
                    }
                }
            }
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Error encoding URL parameters", e);
        }

        String vnp_SecureHash = vnpayConfig.hmacSHA512(vnpayConfig.getVnp_HashSecret(), hashData.toString());
        query.append("&vnp_SecureHash=").append(vnp_SecureHash);
        String paymentUrl = vnpayConfig.getVnp_PayUrl() + "?" + query.toString();

        return new RedirectView(paymentUrl);
    }


@GetMapping("/vnpay_return")
public String vnpayReturn(@RequestParam Map<String, String> allParams, Model model) {
    try {
        // Xác thực chữ ký VNPAY
        boolean isValid = vnpayConfig.verifySignature(allParams);

        if (isValid) {
            String vnp_ResponseCode = allParams.get("vnp_ResponseCode");
            String vnp_TxnRef = allParams.get("vnp_TxnRef");
            String vnp_Amount = allParams.get("vnp_Amount");

            // Chuyển đổi amount từ VNPAY (đơn vị là VND * 100)
            long amountInVND = Long.parseLong(vnp_Amount) / 100;

            // Tìm đơn hàng dựa trên orderCode (vnp_TxnRef)
            Order order = orderService.findByOrderCode(vnp_TxnRef);


            if (order == null) {
                model.addAttribute("message", "Không tìm thấy đơn hàng.");
                return "cart/payment_result";
            }

            // Kiểm tra số tiền thanh toán có khớp không
            if (order.getTotalAmount().doubleValue() != amountInVND) {
                model.addAttribute("message", "Số tiền thanh toán không khớp.");
                return "cart/payment_result";
            }

            if ("00".equals(vnp_ResponseCode)) {
                // Payment successful


                // Đặt hàng (cập nhật tồn kho và trạng thái)
                orderService.placeOrder(order);

                // Xóa giỏ hàng
                Customer customer = getCurrentCustomer();
                cartService.clearCart(customer);

                // Thêm thông báo thành công
                model.addAttribute("order", order);
                return "order/success";
            } else {
                // Payment failed
                order.setStatus(OrderStatus.UNPAID_CANCELED);
                orderService.saveOrder(order); // Cập nhật trạng thái đơn hàng

                model.addAttribute("message", "Thanh toán không thành công. Mã lỗi: " + vnp_ResponseCode);
                return "cart/payment_result";
            }

        } else {
            model.addAttribute("message", "Chữ ký không hợp lệ!");
            return "cart/payment_result";
        }
    } catch (RuntimeException e) {
        // Log the exception
        System.err.println("Error verifying VNPay signature: " + e.getMessage());
        e.printStackTrace();
        model.addAttribute("message", "Đã xảy ra lỗi khi xác thực thanh toán. Vui lòng liên hệ hỗ trợ.");
        return "cart/payment_result";
    } catch (InsufficientInventoryException e) {
        throw new RuntimeException(e);
    } catch (UnsupportedEncodingException e) {
        throw new RuntimeException(e);
    }
}

    private String initiateVNPayPayment(Order order, Model model) {
        try {
            // Tính toán số tiền cần thanh toán (đã bao gồm giảm giá nếu có)
            double amount = order.getTotalAmount().doubleValue();

            // Các tham số cần thiết cho VNPAY
            String vnp_Version = "2.1.0";
            String vnp_Command = "pay";
            String orderType = "other";

            long amountInVND = (long) (amount * 100);
            String bankCode = "";

            // Sử dụng orderCode hoặc orderID làm vnp_TxnRef
            String vnp_TxnRef = order.getOrderCode(); // Hoặc String.valueOf(order.getOrderID());

            String vnp_IpAddr = "127.0.0.1"; // Thay bằng cách lấy IP của client nếu cần

            String vnp_TmnCode = vnpayConfig.getVnp_TmnCode();

            Map<String, String> vnp_Params = new HashMap<>();
            vnp_Params.put("vnp_Version", vnp_Version);
            vnp_Params.put("vnp_Command", vnp_Command);
            vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
            vnp_Params.put("vnp_Amount", String.valueOf(amountInVND));
            vnp_Params.put("vnp_CurrCode", "VND");

            if (bankCode != null && !bankCode.isEmpty()) {
                vnp_Params.put("vnp_BankCode", bankCode);
            }

            vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
            vnp_Params.put("vnp_OrderInfo", "Thanh toán đơn hàng: " + vnp_TxnRef);
            vnp_Params.put("vnp_OrderType", orderType);

            // Locale setting
            vnp_Params.put("vnp_Locale", "vn");

            vnp_Params.put("vnp_ReturnUrl", vnpayConfig.getVnp_ReturnUrl());
            vnp_Params.put("vnp_IpAddr", vnp_IpAddr);

            // Create date
            Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
            String vnp_CreateDate = formatter.format(cld.getTime());
            vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

            // Expire date
            cld.add(Calendar.MINUTE, 15);
            String vnp_ExpireDate = formatter.format(cld.getTime());
            vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

            // Build data to hash and query
            List<String> fieldNames = new ArrayList<>(vnp_Params.keySet());
            Collections.sort(fieldNames);
            StringBuilder hashData = new StringBuilder();
            StringBuilder query = new StringBuilder();
            try {
                for (Iterator<String> itr = fieldNames.iterator(); itr.hasNext(); ) {
                    String fieldName = itr.next();
                    String fieldValue = vnp_Params.get(fieldName);
                    if (fieldValue != null && !fieldValue.isEmpty()) {
                        // Build hash data
                        hashData.append(fieldName);
                        hashData.append('=');
                        hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.UTF_8.toString()));

                        // Build query
                        query.append(URLEncoder.encode(fieldName, StandardCharsets.UTF_8.toString()));
                        query.append('=');
                        query.append(URLEncoder.encode(fieldValue, StandardCharsets.UTF_8.toString()));
                        if (itr.hasNext()) {
                            hashData.append('&');
                            query.append('&');
                        }
                    }
                }
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException("Error encoding URL parameters", e);
            }

            String vnp_SecureHash = vnpayConfig.hmacSHA512(vnpayConfig.getVnp_HashSecret(), hashData.toString());
            query.append("&vnp_SecureHash=").append(vnp_SecureHash);
            String paymentUrl = vnpayConfig.getVnp_PayUrl() + "?" + query.toString();

            // Chuyển hướng người dùng đến VNPAY
            return "redirect:" + paymentUrl;
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Đã xảy ra lỗi khi khởi tạo thanh toán VNPAY.");
            return "cart/cart";
        }
    }




    @PostMapping("/placeOrder")
    public String placeOrder(@RequestParam("paymentMethod") String paymentMethod, Model model, HttpSession session) {
        Customer customer = getCurrentCustomer();

        try {
            // Chuẩn bị đơn hàng từ giỏ hàng
            Order order = cartService.prepareOrder(customer);

            // Thiết lập phương thức thanh toán cho đơn hàng
            order.setPaymentMethod(paymentMethod);

            // Kiểm tra xem có áp dụng coupon không
            BigDecimal discountAmount = BigDecimal.ZERO;
            Coupon appliedCoupon = (Coupon) session.getAttribute("appliedCoupon");
            if (appliedCoupon != null) {
                discountAmount = (BigDecimal) session.getAttribute("discountAmount");
                // Tính toán lại tổng tiền sau khi áp dụng giảm giá
                BigDecimal orderTotal = order.getTotalAmount().subtract(discountAmount);
                order.setTotalAmount(orderTotal);
            }

            // Tạo mã đơn hàng duy nhất
            String orderCode = "ORD-" + System.currentTimeMillis();
            order.setOrderCode(orderCode);

            if ("COD".equals(paymentMethod)) {
                // Thanh toán khi nhận hàng
                order.setStatus(OrderStatus.PENDING);

                // Lưu đơn hàng
                Order savedOrder = orderService.placeOrder(order);

                // Nếu có sử dụng coupon, lưu thông tin sử dụng coupon
                if (appliedCoupon != null) {
                    CouponUsage couponUsage = new CouponUsage();
                    couponUsage.setCoupon(appliedCoupon);
                    couponUsage.setCustomer(customer);
                    couponUsage.setOrder(savedOrder);
                    couponUsage.setRedemptionDate(new Date());
                    couponUsage.setDiscountAmount(discountAmount);

                    couponUsageService.addCouponUsage(couponUsage);

                    // Xóa thông tin coupon khỏi session
                    session.removeAttribute("appliedCoupon");
                    session.removeAttribute("discountAmount");
                }

                // Xóa giỏ hàng sau khi đặt hàng thành công
                cartService.clearCart(customer);

                // Thêm thông báo thành công
                model.addAttribute("order", savedOrder);
                return "order/success";
            } else if ("VNPAY".equals(paymentMethod)) {
                // Thanh toán qua VNPAY
                order.setStatus(OrderStatus.PENDING);

                // Lưu đơn hàng trước khi chuyển hướng đến VNPAY
                Order savedOrder = orderService.placeOrder(order);

                // Nếu có sử dụng coupon, lưu thông tin sử dụng coupon
                if (appliedCoupon != null) {
                    CouponUsage couponUsage = new CouponUsage();
                    couponUsage.setCoupon(appliedCoupon);
                    couponUsage.setCustomer(customer);
                    couponUsage.setOrder(savedOrder);
                    couponUsage.setRedemptionDate(new Date());
                    couponUsage.setDiscountAmount(discountAmount);

                    couponUsageService.addCouponUsage(couponUsage);

                    // Xóa thông tin coupon khỏi session
                    session.removeAttribute("appliedCoupon");
                    session.removeAttribute("discountAmount");
                }

                // Chuyển hướng đến phương thức xử lý thanh toán VNPAY
                return initiateVNPayPayment(savedOrder, model);
            } else {
                // Phương thức thanh toán không hợp lệ
                model.addAttribute("errorMessage", "Phương thức thanh toán không hợp lệ.");
                return "cart/cart";
            }
        } catch (InsufficientInventoryException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "cart/cart";
        } catch (RuntimeException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "cart/cart";
        }
    }


    @PostMapping("/applyCoupon")
    public String applyCoupon(@RequestParam("couponCode") String couponCode, Model model, HttpSession session) {
        Customer customer = getCurrentCustomer();
        double totalAmount = cartService.calculateTotalAmount(customer);

        List<CartItem> cartItems = cartService.getCartItems(customer);

        // Initialize CartUpdateForm with existing cart items
        CartUpdateForm cartUpdateForm = new CartUpdateForm();
        List<CartItemForm> cartItemForms = new ArrayList<>();
        for (CartItem item : cartItems) {
            CartItemForm form = new CartItemForm();
            form.setCartItemId(item.getCartItemID());
            form.setQuantity(item.getQuantity());
            cartItemForms.add(form);
        }
        cartUpdateForm.setCartItems(cartItemForms);

        BigDecimal orderTotal = cartService.calculateTotalAmount1(customer);

        Optional<Coupon> optionalCoupon = couponService.getCouponByCode(couponCode);
        if (optionalCoupon.isPresent()) {
            Coupon coupon = optionalCoupon.get();
            if (coupon.isValid(LocalDate.now(), orderTotal)) {
                // Áp dụng giảm giá và lưu vào session
                BigDecimal discountAmount = orderTotal.subtract(coupon.applyDiscount(orderTotal));
                session.setAttribute("discountAmount", discountAmount);
                session.setAttribute("appliedCoupon", coupon);
                model.addAttribute("couponMessage", "Áp dụng mã coupon thành công!");
            } else {
                model.addAttribute("couponError", "Mã coupon không hợp lệ hoặc đã hết hạn.");
                session.removeAttribute("discountAmount");
                session.removeAttribute("appliedCoupon");
            }
        } else {
            model.addAttribute("couponError", "Mã coupon không tồn tại.");
            session.removeAttribute("discountAmount");
            session.removeAttribute("appliedCoupon");
        }


        BigDecimal discountAmount = (BigDecimal) session.getAttribute("discountAmount");
        BigDecimal totalAmountBigDecimal = BigDecimal.valueOf(totalAmount); // Chuyển đổi totalAmount từ double sang BigDecimal
        BigDecimal totalAmountAfterDiscount = totalAmountBigDecimal.subtract(discountAmount != null ? discountAmount : BigDecimal.ZERO);

        // Chuẩn bị dữ liệu cho trang giỏ hàng
        model.addAttribute("cartItems", cartItems);
        model.addAttribute("totalAmount", orderTotal);
        model.addAttribute("cartUpdateForm", cartUpdateForm);
        model.addAttribute("totalAmount", totalAmount);
        model.addAttribute("totalAmountAfterDiscount", totalAmountAfterDiscount);
        return "cart/cart"; // Tên của template giỏ hàng (cart.html)
    }



}
