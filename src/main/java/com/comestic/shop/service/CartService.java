package com.comestic.shop.service;

import com.comestic.shop.model.*;
import com.comestic.shop.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private BranchDistanceRepository branchDistanceRepository;

    @Autowired
    private InventoryRepository inventoryRepository;

    // Lấy giỏ hàng của khách hàng
    public Cart getCartByCustomer(Customer customer) {
        return cartRepository.findByCustomer(customer)
                .orElseGet(() -> {
                    Cart cart = new Cart();
                    cart.setCustomer(customer);
                    cart.setCreatedDate(new Date());
                    return cartRepository.save(cart);
                });
    }

    // Thêm sản phẩm vào giỏ hàng
    public void addProductToCart(Customer customer, int productId, int quantity) {
        Cart cart = getCartByCustomer(customer);
        Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Sản phẩm không tồn tại"));

        Optional<CartItem> optionalCartItem = cartItemRepository.findByCartAndProduct(cart, product);

        if (optionalCartItem.isPresent()) {
            CartItem cartItem = optionalCartItem.get();
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
            cartItemRepository.save(cartItem);
        } else {
            CartItem cartItem = new CartItem();
            cartItem.setCart(cart);
            cartItem.setProduct(product);
            cartItem.setQuantity(quantity);
            cartItemRepository.save(cartItem);
        }
    }

    // Lấy danh sách các mục trong giỏ hàng
    public List<CartItem> getCartItems(Customer customer) {
        Cart cart = getCartByCustomer(customer);
        return cartItemRepository.findByCart(cart);
    }

    // Các phương thức khác như cập nhật số lượng, xóa sản phẩm khỏi giỏ hàng...
    // Cập nhật số lượng sản phẩm
    public void updateCartItem(int cartItemId, int quantity) {
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new RuntimeException("CartItem không tồn tại"));
        cartItem.setQuantity(quantity);
        cartItemRepository.save(cartItem);
    }


    // Xóa sản phẩm khỏi giỏ hàng
    public void removeCartItem(int cartItemId) {
        cartItemRepository.deleteById(cartItemId);
    }


    // Tính tổng tiền
    public double calculateTotalAmount(Customer customer) {
        Cart cart = getCartByCustomer(customer);
        List<CartItem> cartItems = cartItemRepository.findByCart(cart);
        return cartItems.stream()
                .mapToDouble(item -> item.getProduct().getPrice()
                        .multiply(BigDecimal.valueOf(item.getQuantity())) // Nhân BigDecimal với số lượng
                        .doubleValue()) // Chuyển đổi kết quả sang kiểu double
                .sum();
    }


    public Order prepareOrder(Customer customer) {
        Cart cart = getCartByCustomer(customer);
        List<CartItem> cartItems = getCartItems(customer);

        if (cartItems.isEmpty()) {
            throw new RuntimeException("Giỏ hàng trống, không thể đặt hàng.");
        }

        // Tạo đối tượng Order
        Order order = new Order();
        order.setCustomer(customer);
        order.setOrderDate(new Date());
        order.setStatus(OrderStatus.NEW);
        order.setTotalAmount(BigDecimal.valueOf(calculateTotalAmount(customer)));

        // Tìm Address từ Customer
        Optional<CustomerAddress> defaultCustomerAddress = customer.getCustomerAddresses().stream()
                .filter(CustomerAddress::isDefault)
                .findFirst();

        if (defaultCustomerAddress.isPresent()) {
            Address shippingAddress = defaultCustomerAddress.get().getAddress();
            order.setShippingAddress(shippingAddress);
            System.out.println("Shipping address set successfully.");

            // Lấy danh sách các branch đã được sắp xếp theo khoảng cách
            List<BranchDistance> branchDistances = branchDistanceRepository.findByWardIdOrderByDistanceAsc(shippingAddress.getWard().getWardID());

            // Duyệt qua các branch đã được sắp xếp theo khoảng cách
            for (BranchDistance branchDistance : branchDistances) {
                Branch branch = branchDistance.getBranch();
                boolean isStockAvailable = true;

                // Kiểm tra xem chi nhánh này có đủ sản phẩm không
                for (CartItem cartItem : cartItems) {
                    Inventory inventory = inventoryRepository.findByBranchAndProduct(branch, cartItem.getProduct());
                    if (inventory == null || inventory.getQuantity() < cartItem.getQuantity()) {
                        isStockAvailable = false;
                        break;
                    }
                }

                // Nếu chi nhánh này có đủ hàng, chọn nó và gán vào order
                if (isStockAvailable) {
                    order.setBranch(branch);
                    System.out.println("Branch set successfully.");
                    break; // Thoát khỏi vòng lặp khi tìm thấy chi nhánh phù hợp
                }
            }

            // Nếu không tìm thấy chi nhánh nào có đủ hàng, có thể ném exception hoặc thông báo lỗi
            if (order.getBranch() == null) {
                throw new RuntimeException("Không có chi nhánh nào có đủ hàng cho đơn đặt hàng.");
            }
        } else {
            System.err.println("No default shipping address found for the customer.");
        }

        // Thiết lập chi tiết đơn hàng
        List<OrderDetails> orderDetailsList = new ArrayList<>();
        for (CartItem cartItem : cartItems) {
            OrderDetails orderDetails = new OrderDetails();
            orderDetails.setProduct(cartItem.getProduct());
            orderDetails.setQuantity(cartItem.getQuantity());
            orderDetails.setUnitPrice(cartItem.getProduct().getPrice());
            orderDetails.setOrder(order);

            orderDetailsList.add(orderDetails);
        }

        order.setOrderDetails(orderDetailsList);

        return order;
    }


    public void clearCart(Customer customer) {
        Cart cart = getCartByCustomer(customer);
        cartItemRepository.deleteAllByCart(cart);
    }


}

