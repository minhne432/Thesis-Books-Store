Để thực hiện tính toán khoảng cách giữa tọa độ của địa chỉ giao hàng và các chi nhánh kho hàng, bạn có thể sử dụng **haversine formula** để tính khoảng cách giữa hai tọa độ (kinh độ và vĩ độ) trên bề mặt của Trái đất. Sau đó, bạn có thể tìm chi nhánh gần nhất bằng cách so sánh các khoảng cách.

### Các bước thực hiện:

1. **Lưu tọa độ của các chi nhánh**:
   - Bạn đã có bảng lưu tọa độ chi nhánh, ví dụ:
   ```sql
   CREATE TABLE Branches (
       BranchID INT PRIMARY KEY AUTO_INCREMENT,
       BranchName VARCHAR(255) NOT NULL,
       Latitude DECIMAL(10, 8) NOT NULL,
       Longitude DECIMAL(11, 8) NOT NULL
   );
   ```

2. **Haversine formula**:
   Công thức Haversine tính toán khoảng cách giữa hai điểm trên bề mặt của Trái đất dựa trên tọa độ:
   ```java
   public double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
       final int EARTH_RADIUS = 6371; // Bán kính Trái đất (km)

       double dLat = Math.toRadians(lat2 - lat1);
       double dLon = Math.toRadians(lon2 - lon1);

       double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                  Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                  Math.sin(dLon / 2) * Math.sin(dLon / 2);

       double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

       return EARTH_RADIUS * c; // Khoảng cách tính bằng km
   }
   ```

3. **Tính toán khoảng cách trong Spring Boot**:
   Khi người dùng nhập địa chỉ giao hàng và bạn đã lấy được tọa độ (phường/xã), bạn có thể sử dụng đoạn mã trên để tính khoảng cách đến từng chi nhánh và chọn chi nhánh gần nhất.

4. **Truy vấn từ cơ sở dữ liệu**:
   Truy vấn tất cả các chi nhánh từ cơ sở dữ liệu, và sau đó tính khoảng cách:

   ```java
   @Autowired
   private BranchRepository branchRepository;

   public Branch findNearestBranch(double userLat, double userLon) {
       List<Branch> branches = branchRepository.findAll();
       Branch nearestBranch = null;
       double shortestDistance = Double.MAX_VALUE;

       for (Branch branch : branches) {
           double distance = calculateDistance(userLat, userLon, branch.getLatitude(), branch.getLongitude());
           if (distance < shortestDistance) {
               shortestDistance = distance;
               nearestBranch = branch;
           }
       }

       return nearestBranch;
   }
   ```

5. **Hiển thị kết quả trong Thymeleaf**:
   Sau khi tìm được chi nhánh gần nhất, bạn có thể hiển thị thông tin chi nhánh này trong giao diện người dùng thông qua Thymeleaf:
   ```html
   <p>Chi nhánh gần nhất của bạn là: [[${nearestBranch.branchName}]]</p>
   ```

### Tổng kết:
- Bạn sử dụng công thức Haversine để tính toán khoảng cách giữa địa chỉ giao hàng và các chi nhánh.
- Trong Spring Boot, bạn có thể viết một hàm tính khoảng cách, sau đó tìm chi nhánh gần nhất bằng cách so sánh khoảng cách này.
- Kết quả chi nhánh gần nhất được hiển thị qua Thymeleaf cho người dùng.

Giải pháp này có thể mở rộng, dễ triển khai và tích hợp với hệ thống Spring Boot + Thymeleaf của bạn.