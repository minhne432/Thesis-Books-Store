package com.comestic.shop.service;

import com.comestic.shop.model.Inventory;
import com.comestic.shop.model.Product;
import com.comestic.shop.repository.InventoryRepository;
import com.comestic.shop.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private InventoryRepository inventoryRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> getProductById(int id) {
        return productRepository.findById(id);
    }

    public Product addProduct(Product product) {
        return productRepository.save(product);
    }

    public Product updateProduct(int id, Product productDetails) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            product.setProductName(productDetails.getProductName());
            product.setBrand(productDetails.getBrand());
            product.setCategory(productDetails.getCategory());
            product.setDescription(productDetails.getDescription());
            product.setPrice(productDetails.getPrice());
            product.setStockQuantity(productDetails.getStockQuantity());
            return productRepository.save(product);
        } else {
            return null; // Hoặc ném ra ngoại lệ tùy theo logic của bạn
        }
    }

    public void deleteProduct(int id) {
        productRepository.deleteById(id);
    }

    public List<Inventory> getInventoriesByProduct(Product product) {
        return inventoryRepository.findByProduct(product);
    }

    public Page<Product> getProductsByPage(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return productRepository.findAll(pageable);
    }


    public List<Product> searchProducts(String keyword) {
        return productRepository.searchProducts(keyword);
    }

    public Page<Product> searchProductsByPage(String keyword, int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return productRepository.findByProductNameContaining(keyword, pageable); // Giả sử bạn tìm kiếm theo tên sản phẩm
    }

    public void updateProductStockQuantity(int productId) {
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            Integer totalQuantity = inventoryRepository.sumQuantityByProduct(product);
            if (totalQuantity == null) {
                totalQuantity = 0;
            }
            product.setStockQuantity(totalQuantity);
            productRepository.save(product);
        } else {
            // Xử lý khi sản phẩm không tồn tại
            throw new IllegalArgumentException("Sản phẩm không tồn tại với ID: " + productId);
        }
    }


    public Page<Product> filterProducts(String category, String brand, Integer priceMin, Integer priceMax, int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);

        boolean hasCategory = (category != null && !category.trim().isEmpty());
        boolean hasBrand = (brand != null && !brand.trim().isEmpty());
        boolean hasPriceRange = (priceMin != null && priceMax != null);

        if (hasCategory && hasBrand && hasPriceRange) {
            return productRepository.findByCategory_CategoryNameIgnoreCaseAndBrandIgnoreCaseAndPriceBetween(category, brand, priceMin, priceMax, pageable);
        } else if (hasCategory && hasBrand) {
            return productRepository.findByCategory_CategoryNameIgnoreCaseAndBrandIgnoreCase(category, brand, pageable);
        } else if (hasCategory && hasPriceRange) {
            return productRepository.findByCategory_CategoryNameIgnoreCaseAndPriceBetween(category, priceMin, priceMax, pageable);
        } else if (hasBrand && hasPriceRange) {
            return productRepository.findByBrandIgnoreCaseAndPriceBetween(brand, priceMin, priceMax, pageable);
        } else if (hasCategory) {
            return productRepository.findByCategory_CategoryNameIgnoreCase(category, pageable);
        } else if (hasBrand) {
            return productRepository.findByBrandIgnoreCase(brand, pageable);
        } else if (hasPriceRange) {
            return productRepository.findByPriceBetween(priceMin, priceMax, pageable);
        } else {
            // Nếu không có tham số lọc, trả về tất cả
            return productRepository.findAll(pageable);
        }
    }

}
