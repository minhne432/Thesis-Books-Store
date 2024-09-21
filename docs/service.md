Thư mục **Service** trong Spring Boot chứa các lớp logic nghiệp vụ (business logic) của ứng dụng. Đây là nơi tập trung các xử lý nghiệp vụ phức tạp, giúp tách biệt các chức năng chính khỏi các lớp điều khiển (Controller) và các lớp dữ liệu (Repository).

### Các thành phần chính trong thư mục **Service**:

1. **Các lớp Service**:

   - Các lớp này chứa các phương thức xử lý nghiệp vụ, tương tác với cơ sở dữ liệu thông qua các lớp **Repository**, và thực hiện các tính toán, logic cần thiết.
   - Các lớp service thường được đánh dấu bằng annotation `@Service` để Spring Boot có thể quản lý các lớp này như một **Spring Bean** và hỗ trợ **Dependency Injection**.
   - Mỗi service có thể tập trung xử lý một lĩnh vực cụ thể, ví dụ: `ProductService`, `OrderService`, `UserService`.

2. **Tương tác với lớp Repository**:

   - **Service** chịu trách nhiệm lấy dữ liệu từ cơ sở dữ liệu thông qua các lớp **Repository**. Các lớp **Repository** chỉ đơn thuần thực hiện các thao tác CRUD (tạo, đọc, cập nhật, xóa), trong khi **Service** xử lý logic nghiệp vụ liên quan.
   - Ví dụ: Khi lấy danh sách sản phẩm từ cơ sở dữ liệu, `ProductService` sẽ gọi `productRepository.findAll()`.

3. **Xử lý dữ liệu nghiệp vụ**:

   - Dữ liệu từ **Repository** có thể được xử lý, tính toán, hoặc kiểm tra trước khi trả về cho lớp **Controller**. Logic như kiểm tra điều kiện, xác thực, và xử lý ngoại lệ cũng nằm trong **Service**.
   - Ví dụ: Nếu một sản phẩm không tồn tại, lớp **Service** có thể kiểm tra và ném ra ngoại lệ thích hợp (`ProductNotFoundException`).

4. **Chuyển đổi giữa Model và DTO**:

   - Trong một số trường hợp, dữ liệu từ cơ sở dữ liệu (Model) sẽ được chuyển đổi thành **DTO** (Data Transfer Object) để gửi ra bên ngoài, hoặc ngược lại.
   - **Service** thường thực hiện chuyển đổi này để giữ cho các lớp **Controller** và **Repository** không quá phức tạp.
   - Ví dụ: `ProductService` sẽ chuyển đổi từ `Product` (entity) sang `ProductDTO` để trả về cho controller.

5. **Phối hợp các dịch vụ khác**:

   - Một lớp **Service** có thể gọi và phối hợp với các lớp **Service** khác để hoàn thành nghiệp vụ phức tạp. Điều này giúp tách biệt các chức năng và làm cho mã dễ bảo trì hơn.
   - Ví dụ: Khi tạo đơn hàng mới, `OrderService` có thể cần gọi `ProductService` để kiểm tra sản phẩm trước khi tạo đơn hàng.

6. **Xử lý ngoại lệ (Exception Handling)**:
   - Trong lớp **Service**, có thể xử lý các ngoại lệ nội bộ hoặc tạo các ngoại lệ tùy chỉnh để báo cáo các tình huống không mong muốn.
   - Ví dụ: Khi không tìm thấy sản phẩm, `ProductService` có thể ném ngoại lệ `ProductNotFoundException`.

### Ví dụ về lớp Service - `ProductService`:

```java
@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    // Lấy danh sách tất cả các sản phẩm
    public List<ProductDTO> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }

    // Tìm sản phẩm theo ID
    public ProductDTO getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));
        return convertToDTO(product);
    }

    // Thêm sản phẩm mới
    public ProductDTO addProduct(ProductDTO productDTO) {
        Product product = convertToEntity(productDTO);
        Product savedProduct = productRepository.save(product);
        return convertToDTO(savedProduct);
    }

    // Xóa sản phẩm theo ID
    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new ProductNotFoundException("Product not found");
        }
        productRepository.deleteById(id);
    }

    // Chuyển đổi từ Product sang ProductDTO
    private ProductDTO convertToDTO(Product product) {
        ProductDTO dto = new ProductDTO();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setPrice(product.getPrice());
        return dto;
    }

    // Chuyển đổi từ ProductDTO sang Product (Entity)
    private Product convertToEntity(ProductDTO productDTO) {
        Product product = new Product();
        product.setName(productDTO.getName());
        product.setPrice(productDTO.getPrice());
        return product;
    }
}
```

### Giải thích ví dụ:

- **ProductService** là một lớp service xử lý nghiệp vụ liên quan đến sản phẩm.
- **Phương thức `getAllProducts()`** lấy danh sách tất cả các sản phẩm từ cơ sở dữ liệu thông qua `productRepository.findAll()`, sau đó chuyển đổi các đối tượng `Product` sang `ProductDTO`.
- **Phương thức `getProductById()`** tìm sản phẩm theo ID. Nếu không tìm thấy sản phẩm, nó sẽ ném ngoại lệ `ProductNotFoundException`.
- **Phương thức `addProduct()`** nhận một `ProductDTO`, chuyển đổi nó thành đối tượng `Product`, và lưu vào cơ sở dữ liệu.
- **Phương thức `deleteProduct()`** kiểm tra xem sản phẩm có tồn tại không trước khi xóa nó khỏi cơ sở dữ liệu.

### Tóm tắt các thành phần chính trong thư mục **Service**:

1. **Lớp Service**: Chứa các phương thức nghiệp vụ chính, xử lý dữ liệu và gọi các lớp Repository.
2. **Tương tác với Repository**: Lấy dữ liệu từ cơ sở dữ liệu, thực hiện các thao tác CRUD qua Repository.
3. **Chuyển đổi giữa Model và DTO**: Giúp duy trì sự tách biệt giữa dữ liệu trong ứng dụng và dữ liệu giao tiếp với người dùng (Controller).
4. **Xử lý nghiệp vụ và ngoại lệ**: Chứa các logic nghiệp vụ phức tạp và xử lý ngoại lệ tùy chỉnh.

Với việc tách biệt logic nghiệp vụ vào các lớp **Service**, kiến trúc ứng dụng Spring Boot trở nên rõ ràng, dễ bảo trì và mở rộng khi cần thiết.
