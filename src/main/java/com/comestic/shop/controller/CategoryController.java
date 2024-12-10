package com.comestic.shop.controller;

import com.comestic.shop.model.Category;
import com.comestic.shop.service.CategoryService;
import com.comestic.shop.service.PermissionCheckerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;


    @Autowired
    private PermissionCheckerService permissionCheckerService;
    // Trang liệt kê danh mục sản phẩm và form thêm mới
    @GetMapping
    public String listCategories(Model model) {

        String permissionRequired = "manage_category";

        // Kiểm tra permission chung cho danh sách đơn hàng
        if (!permissionCheckerService.hasPermission(permissionRequired)) {
            return "access-denied";
        }

        List<Category> categories = categoryService.getAllCategories();
        model.addAttribute("categories", categories);
        model.addAttribute("category", new Category()); // Đối tượng để binding form tạo mới
        return "category/category_list";
    }

    // Xử lý form thêm mới danh mục
    @PostMapping
    public String addCategory(@ModelAttribute("category") Category category) {
        // Tùy chọn: Kiểm tra xem CategoryName đã tồn tại chưa
        // if(categoryService.existsByCategoryName(category.getCategoryName())) { ... }

        // Lưu category mới
        categoryService.saveCategory(category);
        return "redirect:/categories";
    }

    // Hiển thị form chỉnh sửa danh mục
    @GetMapping("/{id}/edit")
    public String editCategoryForm(@PathVariable("id") int categoryId, Model model) {
        Category category = categoryService.getCategoryById(categoryId);
        model.addAttribute("category", category);
        return "category/category_edit";
    }

    // Xử lý form chỉnh sửa danh mục
    @PostMapping("/{id}/edit")
    public String updateCategory(@PathVariable("id") int categoryId,
                                 @ModelAttribute("category") Category updatedCategory) {
        Category category = categoryService.getCategoryById(categoryId);
        category.setCategoryName(updatedCategory.getCategoryName());
        categoryService.saveCategory(category);
        return "redirect:/categories";
    }
}
