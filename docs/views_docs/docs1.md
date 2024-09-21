Dưới đây là cấu trúc thư mục `templates` phù hợp với các chức năng của từng controller trong hệ thống của bạn sử dụng Spring Boot và Thymeleaf. Mỗi controller sẽ có một thư mục riêng để tổ chức các tệp template HTML theo từng chức năng.

### Cấu trúc thư mục `templates`

```plaintext
src/
 └── main/
     └── resources/
         └── templates/
             ├── customer/
             │   ├── register.html
             │   ├── login.html
             │   ├── profile.html
             │   ├── login-history.html
             ├── product/
             │   ├── product-list.html
             │   ├── product-detail.html
             │   ├── add-product.html
             │   ├── edit-product.html
             ├── order/
             │   ├── order-list.html
             │   ├── order-detail.html
             │   ├── create-order.html
             ├── cart/
             │   ├── cart.html
             │   ├── checkout.html
             ├── payment/
             │   ├── payment-status.html
             │   ├── payment-success.html
             ├── review/
             │   ├── product-review.html
             │   ├── add-review.html
             ├── supplier/
             │   ├── supplier-list.html
             │   ├── add-supplier.html
             │   ├── edit-supplier.html
             ├── inventory/
             │   ├── inventory-list.html
             │   ├── update-stock.html
             ├── branch/
             │   ├── branch-list.html
             │   ├── add-branch.html
             │   ├── edit-branch.html
             ├── discount-code/
             │   ├── discount-list.html
             │   ├── add-discount.html
             ├── role/
             │   ├── role-list.html
             │   ├── assign-role.html
             ├── report/
             │   ├── sales-report.html
             │   ├── customer-report.html
             ├── auth/
             │   ├── login.html
             │   ├── logout.html
```

### Mô tả từng thư mục và tệp:

1. **`customer/`**

   - `register.html`: Trang đăng ký khách hàng mới.
   - `login.html`: Trang đăng nhập cho khách hàng.
   - `profile.html`: Trang quản lý thông tin cá nhân của khách hàng.
   - `login-history.html`: Trang hiển thị lịch sử đăng nhập.

2. **`product/`**

   - `product-list.html`: Trang danh sách sản phẩm.
   - `product-detail.html`: Trang chi tiết sản phẩm.
   - `add-product.html`: Trang thêm sản phẩm mới.
   - `edit-product.html`: Trang chỉnh sửa thông tin sản phẩm.

3. **`order/`**

   - `order-list.html`: Trang hiển thị danh sách các đơn hàng của khách hàng.
   - `order-detail.html`: Trang chi tiết của một đơn hàng cụ thể.
   - `create-order.html`: Trang tạo đơn hàng mới.

4. **`cart/`**

   - `cart.html`: Trang hiển thị giỏ hàng của khách hàng.
   - `checkout.html`: Trang thanh toán cho các sản phẩm trong giỏ hàng.

5. **`payment/`**

   - `payment-status.html`: Trang trạng thái thanh toán của đơn hàng.
   - `payment-success.html`: Trang thông báo thanh toán thành công.

6. **`review/`**

   - `product-review.html`: Trang hiển thị các đánh giá sản phẩm.
   - `add-review.html`: Trang cho phép khách hàng viết đánh giá sản phẩm.

7. **`supplier/`**

   - `supplier-list.html`: Trang hiển thị danh sách nhà cung cấp.
   - `add-supplier.html`: Trang thêm nhà cung cấp mới.
   - `edit-supplier.html`: Trang chỉnh sửa thông tin nhà cung cấp.

8. **`inventory/`**

   - `inventory-list.html`: Trang hiển thị số lượng sản phẩm trong kho.
   - `update-stock.html`: Trang cập nhật số lượng tồn kho của sản phẩm.

9. **`branch/`**

   - `branch-list.html`: Trang hiển thị danh sách chi nhánh.
   - `add-branch.html`: Trang thêm chi nhánh mới.
   - `edit-branch.html`: Trang chỉnh sửa thông tin chi nhánh.

10. **`discount-code/`**

    - `discount-list.html`: Trang hiển thị danh sách mã giảm giá.
    - `add-discount.html`: Trang tạo mã giảm giá mới.

11. **`role/`**

    - `role-list.html`: Trang hiển thị danh sách vai trò và quyền hạn.
    - `assign-role.html`: Trang phân quyền cho người dùng.

12. **`report/`**

    - `sales-report.html`: Trang báo cáo doanh số bán hàng.
    - `customer-report.html`: Trang báo cáo về khách hàng thân thiết.

13. **`auth/`**
    - `login.html`: Trang đăng nhập cho khách hàng hoặc admin.
    - `logout.html`: Trang đăng xuất.

### Gợi ý sử dụng Thymeleaf:

- Sử dụng các `fragments` để tạo các phần dùng chung như `header.html`, `footer.html`, hoặc `sidebar.html` trong thư mục `fragments/`.
- Ví dụ:

```plaintext
 └── fragments/
     ├── header.html
     ├── footer.html
     ├── sidebar.html
```

Cấu trúc này giúp bạn dễ dàng tổ chức template cho từng chức năng của hệ thống và giúp việc quản lý mã dễ dàng hơn khi ứng dụng phát triển.
