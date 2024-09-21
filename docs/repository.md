Thư mục **Repository** trong Spring Boot chứa các lớp chịu trách nhiệm tương tác với cơ sở dữ liệu. Các lớp này được dùng để thực hiện các thao tác CRUD (Create, Read, Update, Delete) trên các đối tượng **Model** (Entity) mà không cần phải viết nhiều mã SQL thủ công. Spring Data JPA hỗ trợ mạnh mẽ việc này thông qua các interface giúp giảm tải cho lập trình viên.

### Các thành phần chính trong thư mục **Repository**:

1. **Các interface Repository**:

   - Đây là các interface mở rộng từ `JpaRepository` hoặc `CrudRepository`, được Spring Data JPA cung cấp để dễ dàng thao tác với cơ sở dữ liệu.
   - `JpaRepository<T, ID>`: `T` là kiểu thực thể (entity), `ID` là kiểu của khóa chính của thực thể.
   - Các phương thức CRUD cơ bản (như `save()`, `findById()`, `delete()`, `findAll()`) được cung cấp tự động bởi Spring Data JPA mà không cần phải viết mã SQL thủ công.

   **Ví dụ**:

   ```java
   @Repository
   public interface ProductRepository extends JpaRepository<Product, Long> {
   }
   ```

2. **Các phương thức tùy chỉnh (Custom Query Methods)**:

   - Spring Data JPA hỗ trợ việc tự động tạo ra các truy vấn SQL dựa trên tên của các phương thức trong repository. Lập trình viên chỉ cần định nghĩa tên phương thức theo đúng cú pháp mà không cần viết câu lệnh SQL thủ công.
   - Ví dụ, nếu bạn muốn tìm kiếm sản phẩm theo tên:

     ```java
     List<Product> findByName(String name);
     ```

   - Spring Data JPA sẽ tự động tạo ra truy vấn tương ứng:
     ```sql
     SELECT * FROM product WHERE name = ?;
     ```

3. **Các truy vấn tùy chỉnh bằng @Query**:

   - Ngoài các phương thức tự động, bạn cũng có thể viết các truy vấn tùy chỉnh bằng cách sử dụng annotation `@Query`.
   - Annotation này cho phép bạn viết câu truy vấn JPQL hoặc SQL.
   - Ví dụ:
     ```java
     @Query("SELECT p FROM Product p WHERE p.price > :price")
     List<Product> findProductsByPriceGreaterThan(@Param("price") double price);
     ```

4. **Kế thừa từ Repository khác**:

   - Ngoài `JpaRepository`, Spring Data JPA còn hỗ trợ nhiều interface khác cho các trường hợp đặc thù như `PagingAndSortingRepository` (hỗ trợ phân trang và sắp xếp), `CrudRepository` (thực hiện CRUD đơn giản).
   - Ví dụ:
     ```java
     public interface ProductRepository extends PagingAndSortingRepository<Product, Long> {
     }
     ```

5. **Sử dụng các truy vấn native (Native Queries)**:
   - Ngoài JPQL, bạn cũng có thể sử dụng các truy vấn SQL gốc (native SQL queries) thông qua `@Query`. Điều này hữu ích khi bạn muốn thực thi các truy vấn phức tạp hoặc tối ưu hóa hiệu suất với các truy vấn SQL thuần.
   - Ví dụ:
     ```java
     @Query(value = "SELECT * FROM product WHERE name LIKE %:keyword%", nativeQuery = true)
     List<Product> searchProductsByName(@Param("keyword") String keyword);
     ```

### Ví dụ về **Repository**:

```java
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    // Tìm kiếm sản phẩm theo tên
    List<Product> findByName(String name);

    // Tìm kiếm các sản phẩm có giá lớn hơn một giá trị cụ thể
    @Query("SELECT p FROM Product p WHERE p.price > :price")
    List<Product> findProductsByPriceGreaterThan(@Param("price") double price);

    // Tìm kiếm các sản phẩm theo từ khóa trong tên (Native SQL)
    @Query(value = "SELECT * FROM product WHERE name LIKE %:keyword%", nativeQuery = true)
    List<Product> searchProductsByName(@Param("keyword") String keyword);
}
```

### Các Annotation quan trọng:

- **@Repository**: Annotation này đánh dấu một lớp repository như là một Spring Bean, giúp Spring quản lý và xử lý các ngoại lệ liên quan đến cơ sở dữ liệu. Các lớp repository thường không chứa logic nghiệp vụ mà chỉ là cầu nối với cơ sở dữ liệu.
- **@Query**: Dùng để viết các truy vấn JPQL hoặc native SQL tùy chỉnh.
- **@Param**: Được sử dụng để định nghĩa các tham số trong truy vấn tùy chỉnh.

### Mối quan hệ với các thành phần khác:

- **Repository** tương tác trực tiếp với cơ sở dữ liệu để lấy hoặc lưu trữ dữ liệu. Nó cung cấp các phương thức CRUD cho các lớp **Service** để thực hiện các thao tác nghiệp vụ.
- **Service** sẽ gọi các phương thức của **Repository** để lấy dữ liệu từ cơ sở dữ liệu và thực hiện các xử lý logic trước khi trả về dữ liệu cho **Controller** hoặc người dùng.

### Tóm tắt các thành phần chính trong thư mục **Repository**:

1. **Các interface mở rộng từ JpaRepository hoặc CrudRepository**: Để thực hiện các thao tác CRUD tự động trên các thực thể.
2. **Phương thức truy vấn tự động (Derived Query Methods)**: Spring Data JPA tự động sinh SQL dựa trên tên phương thức.
3. **Các truy vấn tùy chỉnh (Custom Query Methods)**: Sử dụng `@Query` để viết các câu truy vấn JPQL hoặc SQL.
4. **Annotation @Repository**: Đánh dấu các lớp repository để Spring Boot quản lý chúng như một Spring Bean.

Như vậy, thư mục **Repository** đóng vai trò rất quan trọng trong việc kết nối giữa ứng dụng và cơ sở dữ liệu, giúp giảm thiểu việc phải viết mã SQL thủ công và tăng cường hiệu quả phát triển ứng dụng.
