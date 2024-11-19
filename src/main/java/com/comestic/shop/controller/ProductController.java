package com.comestic.shop.controller;

import com.comestic.shop.model.Category;
import com.comestic.shop.model.Inventory;
import com.comestic.shop.model.Product;
import com.comestic.shop.service.CategoryService;
import com.comestic.shop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    // Directory to save uploaded images
//    private static String UPLOAD_DIR = System.getProperty("user.dir") + "/product-images";
    private static String UPLOAD_DIR = "src/main/resources/static/images";


    // Hiển thị danh sách sản phẩm
    @GetMapping
    public String listProducts(Model model) {
        model.addAttribute("products", productService.getAllProducts());
        return "product/list";
    }

    // Hiển thị form thêm sản phẩm mới
// Thêm phương thức trong ProductController để cung cấp danh sách categories
    @GetMapping("/add")
    public String showAddProductForm(Model model) {
        model.addAttribute("product", new Product());
        List<Category> categories = categoryService.getAllCategories(); // Giả sử bạn có CategoryService
        model.addAttribute("categories", categories);
        return "product/add";
    }
    // Xử lý thêm sản phẩm mới
    @PostMapping("/add")
    public String addProduct(@ModelAttribute("product") Product product,
                             @RequestParam("imageFile") MultipartFile imageFile,
                             RedirectAttributes redirectAttributes) {
        try {
            // Save the image to the file system
            String imageFilename = saveImage(imageFile);

            // Set the image filename in the product
            product.setImageFilename(imageFilename);

            // Nếu category là đối tượng, bạn cần lấy đối tượng Category từ database
            Category category = categoryService.getCategoryById(product.getCategory().getCategoryID());
            product.setCategory(category);

            // Save the product
            productService.addProduct(product);

            redirectAttributes.addFlashAttribute("message", "Sản phẩm đã được thêm thành công!");
            return "redirect:/products/list";
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("message", "Có lỗi xảy ra khi thêm sản phẩm.");
            return "redirect:/products/add";
        }
    }


    private String saveImage(MultipartFile imageFile) throws Exception {
        // Create the directory if it doesn't exist
        Path uploadPath = Paths.get(UPLOAD_DIR);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // Generate a unique filename to prevent conflicts
        String originalFilename = imageFile.getOriginalFilename();
        String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
        String uniqueFilename = System.currentTimeMillis() + fileExtension;

        // Save the file
        Path filePath = uploadPath.resolve(uniqueFilename);
        Files.copy(imageFile.getInputStream(), filePath);

        return uniqueFilename;
    }
    // Hiển thị form chỉnh sửa sản phẩm
    @GetMapping("/edit/{id}")
    public String showEditProductForm(@PathVariable("id") int id, Model model) {
        Optional<Product> optionalProduct = productService.getProductById(id);
        if(optionalProduct.isPresent()) {
            model.addAttribute("product", optionalProduct.get());
            return "product/edit";
        } else {
            return "redirect:/products";
        }
    }

    // Xử lý cập nhật sản phẩm
    @PostMapping("/edit/{id}")
    public String editProduct(@PathVariable("id") int id, @ModelAttribute("product") Product productDetails) {
        productService.updateProduct(id, productDetails);
        return "redirect:/products";
    }

    // Xóa sản phẩm
    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable("id") int id) {
        productService.deleteProduct(id);
        return "redirect:/products";
    }

    // Xem chi tiết sản phẩm
    @GetMapping("/{id}")
    public String viewProductDetails(@PathVariable("id") int id, Model model) {
        Optional<Product> optionalProduct = productService.getProductById(id);
        if(optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            model.addAttribute("product", product);

            // Lấy danh sách Inventory cho sản phẩm này
            List<Inventory> inventories = productService.getInventoriesByProduct(product);
            model.addAttribute("inventories", inventories);

            return "product/details";
        } else {
            return "redirect:/products";
        }
    }

    @GetMapping("/list")
    public String showProductList(@RequestParam(defaultValue = "0") int page, Model model) {
        int pageSize = 10; // Số sản phẩm mỗi trang
        Page<Product> productPage = productService.getProductsByPage(page, pageSize);
        model.addAttribute("productPage", productPage);
        model.addAttribute("products", productPage.getContent()); // Lấy danh sách sản phẩm
        return "product/product_list";
    }



    @GetMapping("/shop/search")
    public String searchProducts(@RequestParam(value = "keyword", required = false) String keyword,
                                 @RequestParam(defaultValue = "0") int page,
                                 Model model) {
        int pageSize = 10;
        Page<Product> productPage;
        if (keyword == null || keyword.trim().isEmpty()) {
            productPage = productService.getProductsByPage(page, pageSize); // Sử dụng phân trang cho tất cả sản phẩm
        } else {
            productPage = productService.searchProductsByPage(keyword, page, pageSize); // Sử dụng phương thức tìm kiếm có phân trang
        }
        model.addAttribute("productPage", productPage);
        model.addAttribute("products", productPage.getContent());
        model.addAttribute("keyword", keyword);
        return "product/product_list";
    }





}
