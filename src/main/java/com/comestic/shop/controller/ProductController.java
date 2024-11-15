package com.comestic.shop.controller;

import com.comestic.shop.model.Inventory;
import com.comestic.shop.model.Product;
import com.comestic.shop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    // Hiển thị danh sách sản phẩm
    @GetMapping
    public String listProducts(Model model) {
        model.addAttribute("products", productService.getAllProducts());
        return "product/list";
    }

    // Hiển thị form thêm sản phẩm mới
    @GetMapping("/add")
    public String showAddProductForm(Model model) {
        model.addAttribute("product", new Product());
        return "product/add";
    }

    // Xử lý thêm sản phẩm mới
    @PostMapping("/add")
    public String addProduct(@ModelAttribute("product") Product product) {
        productService.addProduct(product);
        return "redirect:/products/list";
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
