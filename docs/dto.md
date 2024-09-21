Dưới đây là một ví dụ thực tế về chức năng hiển thị danh sách sản phẩm sử dụng **Spring Boot** với **Thymeleaf** để minh họa mối quan hệ giữa **DTO**, **Model**, **Service**, và **Controller**.

### 1. **Model (Entity)** - `Product`

Đây là lớp biểu diễn cho sản phẩm, liên kết với cơ sở dữ liệu.

```java
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private double price;

    // Getters and Setters
}
```

### 2. **DTO (Data Transfer Object)** - `ProductDTO`

Lớp này chỉ chứa dữ liệu cần thiết để hiển thị cho người dùng.

```java
public class ProductDTO {
    private Long id;
    private String name;
    private double price;

    // Constructors, Getters and Setters
}
```

### 3. **Repository** - `ProductRepository`

Sử dụng để thao tác với cơ sở dữ liệu thông qua `JpaRepository`.

```java
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
```

### 4. **Service** - `ProductService`

Lớp này chịu trách nhiệm xử lý nghiệp vụ liên quan đến sản phẩm, ví dụ như lấy danh sách sản phẩm từ cơ sở dữ liệu và chuyển đổi từ `Product` sang `ProductDTO`.

```java
@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<ProductDTO> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    private ProductDTO convertToDTO(Product product) {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(product.getId());
        productDTO.setName(product.getName());
        productDTO.setPrice(product.getPrice());
        return productDTO;
    }
}
```

### 5. **Controller** - `ProductController`

Lớp này nhận yêu cầu từ client (thông qua HTTP), lấy dữ liệu từ `ProductService`, và hiển thị danh sách sản phẩm lên giao diện với sự trợ giúp của **Thymeleaf**.

```java
@Controller
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public String showProductList(Model model) {
        List<ProductDTO> products = productService.getAllProducts();
        model.addAttribute("products", products);
        return "productList";
    }
}
```

### 6. **View (Thymeleaf Template)** - `productList.html`

Đây là trang giao diện nơi danh sách sản phẩm được hiển thị cho người dùng. Sử dụng Thymeleaf để hiển thị các đối tượng `ProductDTO` từ `Controller`.

```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
  <head>
    <title>Product List</title>
  </head>
  <body>
    <h1>Product List</h1>
    <table>
      <thead>
        <tr>
          <th>ID</th>
          <th>Name</th>
          <th>Price</th>
        </tr>
      </thead>
      <tbody>
        <tr th:each="product : ${products}">
          <td th:text="${product.id}">1</td>
          <td th:text="${product.name}">Sample Product</td>
          <td th:text="${product.price}">10.00</td>
        </tr>
      </tbody>
    </table>
  </body>
</html>
```

### Mối quan hệ giữa các thành phần:

1. **Model (Product)**: Đối tượng `Product` đại diện cho một bảng trong cơ sở dữ liệu, chứa các thuộc tính và ràng buộc về sản phẩm.
2. **DTO (ProductDTO)**: Đối tượng này được tạo ra từ `Product`, chỉ chứa thông tin cần thiết để hiển thị cho người dùng, giúp giảm thiểu dữ liệu không cần thiết.
3. **Service (ProductService)**: Lớp `ProductService` chịu trách nhiệm chuyển đổi giữa `Product` và `ProductDTO`, và cung cấp danh sách sản phẩm cho `Controller`.
4. **Controller (ProductController)**: Lớp `ProductController` nhận yêu cầu từ người dùng, gọi `ProductService` để lấy danh sách sản phẩm, và gửi danh sách này tới giao diện.
5. **View (Thymeleaf)**: Trang giao diện sử dụng Thymeleaf để hiển thị danh sách sản phẩm dưới dạng bảng.

Với cách làm này, ứng dụng sẽ dễ bảo trì và mở rộng, vì mỗi lớp có trách nhiệm riêng biệt và không phụ thuộc trực tiếp vào nhau.
