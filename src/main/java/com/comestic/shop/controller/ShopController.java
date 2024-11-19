package com.comestic.shop.controller;


import com.comestic.shop.model.Inventory;
import com.comestic.shop.model.Product;
import com.comestic.shop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/shop/products")
public class ShopController {

    @Autowired
    private ProductService productService;



    @GetMapping("/list")
    public String showProductList(@RequestParam(defaultValue = "0") int page, Model model) {
        int pageSize = 9; // Số sản phẩm mỗi trang
        Page<Product> productPage = productService.getProductsByPage(page, pageSize);
        model.addAttribute("productPage", productPage);
        model.addAttribute("products", productPage.getContent()); // Lấy danh sách sản phẩm
        return "shop/product_list";
    }

    @GetMapping("/search")
    public String searchProducts(@RequestParam(value = "keyword", required = false) String keyword,
                                 @RequestParam(defaultValue = "0") int page,
                                 Model model) {
        int pageSize = 9;
        Page<Product> productPage;
        if (keyword == null || keyword.trim().isEmpty()) {
            productPage = productService.getProductsByPage(page, pageSize); // Sử dụng phân trang cho tất cả sản phẩm
        } else {
            productPage = productService.searchProductsByPage(keyword, page, pageSize); // Sử dụng phương thức tìm kiếm có phân trang
        }
        model.addAttribute("productPage", productPage);
        model.addAttribute("products", productPage.getContent());
        model.addAttribute("keyword", keyword);
        return "shop/product_list";
    }

    @GetMapping("details/{id}")
    public String viewProductDetails(@PathVariable("id") int id, Model model) {
        Optional<Product> optionalProduct = productService.getProductById(id);
        if(optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            model.addAttribute("product", product);

            // Lấy danh sách Inventory cho sản phẩm này
            List<Inventory> inventories = productService.getInventoriesByProduct(product);
            model.addAttribute("inventories", inventories);

            return "shop/product_details";
        } else {
            return "redirect:/products";
        }
    }

}
