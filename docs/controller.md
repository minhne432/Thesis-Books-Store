Thư mục **Controller** trong một dự án Spring Boot thường chứa các lớp điều khiển (controller classes) để xử lý các yêu cầu HTTP từ client và điều hướng dữ liệu giữa các lớp Service và View. **Controller** là nơi đầu tiên nhận yêu cầu từ phía người dùng, sau đó nó giao tiếp với các lớp nghiệp vụ (service) để thực hiện các xử lý, và cuối cùng trả về kết quả cho người dùng (thông qua View hoặc JSON/XML).

### Những thành phần chính mà thư mục **Controller** thường chứa:

1. **Lớp Controller (RestController)**:

   - Các lớp này được chú thích với các annotation như `@Controller`, `@RestController`, để định nghĩa rằng lớp này sẽ xử lý các yêu cầu HTTP.
   - **@Controller**: Được dùng khi bạn muốn trả về một view (giao diện người dùng) như với Thymeleaf, JSP.
   - **@RestController**: Dùng để trả về dữ liệu JSON/XML cho các ứng dụng web service (RESTful API).

2. **Phương thức xử lý yêu cầu (Request Mapping)**:

   - Các phương thức trong lớp controller được chú thích với các annotation như `@GetMapping`, `@PostMapping`, `@PutMapping`, `@DeleteMapping`, hoặc `@RequestMapping` để chỉ định cách xử lý các yêu cầu HTTP tương ứng (GET, POST, PUT, DELETE).
   - Ví dụ:
     ```java
     @GetMapping("/products")
     public String getAllProducts(Model model) {
         // Lấy danh sách sản phẩm và thêm vào model
         return "productList"; // Trả về view productList
     }
     ```

3. **Tương tác với lớp Service**:

   - Controller không chứa logic nghiệp vụ (business logic). Thay vào đó, nó gọi các phương thức từ lớp **Service** để thực hiện các chức năng cần thiết.
   - Ví dụ:

     ```java
     @Autowired
     private ProductService productService;

     @GetMapping("/products")
     public String showProductList(Model model) {
         List<ProductDTO> products = productService.getAllProducts();
         model.addAttribute("products", products);
         return "productList";
     }
     ```

4. **Xử lý dữ liệu đầu vào từ client**:

   - Các lớp controller cũng chịu trách nhiệm xử lý dữ liệu đầu vào từ client. Điều này có thể thông qua các annotation như `@RequestParam`, `@PathVariable`, hoặc `@RequestBody` để nhận các tham số từ URL hoặc dữ liệu từ form.
   - Ví dụ:
     ```java
     @PostMapping("/products")
     public String addProduct(@RequestBody ProductDTO productDTO) {
         productService.addProduct(productDTO);
         return "redirect:/products";
     }
     ```

5. **Quản lý phản hồi**:

   - Sau khi xử lý nghiệp vụ, controller sẽ trả về phản hồi cho client, có thể là:
     - Một **view** (giao diện người dùng) nếu sử dụng các công cụ template như Thymeleaf, JSP.
     - Một đối tượng **JSON/XML** nếu bạn đang xây dựng API.

6. **Xử lý lỗi**:
   - Controller có thể xử lý các lỗi hoặc ngoại lệ bằng cách sử dụng `@ExceptionHandler` hoặc `@ControllerAdvice`. Những phương thức này có thể được định nghĩa để xử lý các lỗi cụ thể và trả về phản hồi phù hợp.

### Ví dụ về lớp **Controller**:

```java
@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    // Lấy danh sách sản phẩm
    @GetMapping
    public List<ProductDTO> getAllProducts() {
        return productService.getAllProducts();
    }

    // Thêm một sản phẩm mới
    @PostMapping
    public ProductDTO addProduct(@RequestBody ProductDTO productDTO) {
        return productService.addProduct(productDTO);
    }

    // Lấy sản phẩm theo ID
    @GetMapping("/{id}")
    public ProductDTO getProductById(@PathVariable Long id) {
        return productService.getProductById(id);
    }

    // Xóa một sản phẩm
    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
    }
}
```

### Tóm tắt các thành phần trong thư mục **Controller**:

- **Các lớp Controller**: Được sử dụng để xử lý các yêu cầu HTTP từ người dùng.
- **Các phương thức xử lý yêu cầu**: Được gắn các annotation như `@GetMapping`, `@PostMapping` để định nghĩa đường dẫn và loại yêu cầu HTTP (GET, POST, PUT, DELETE).
- **Tương tác với Service**: Gọi các phương thức nghiệp vụ từ Service.
- **Xử lý dữ liệu từ client**: Nhận dữ liệu đầu vào từ URL, form, hoặc body của yêu cầu.
- **Trả về phản hồi**: Trả về view (Thymeleaf) hoặc dữ liệu JSON/XML cho client.

Với cách tổ chức như vậy, lớp **Controller** trong Spring Boot có vai trò quan trọng trong việc kết nối giữa người dùng (client) và phần logic xử lý của ứng dụng (business logic).
