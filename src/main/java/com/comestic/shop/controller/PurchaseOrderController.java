package com.comestic.shop.controller;

import com.comestic.shop.dto.*;
import com.comestic.shop.model.*;
import com.comestic.shop.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/purchase-orders")
public class PurchaseOrderController {

    @Autowired
    private PurchaseOrderService purchaseOrderService;

    @Autowired
    private SupplierService supplierService;

    @Autowired
    private BranchService branchService;

    @Autowired
    private ProductService productService;

//    @GetMapping("/add")
//    public String showAddPurchaseOrderForm(Model model) {
//        PurchaseOrder purchaseOrder = new PurchaseOrder();
//        purchaseOrder.setOrderDate(LocalDate.now());
//        purchaseOrder.setStatus("Pending");
//
//        List<PurchaseOrderDetails> detailsList = new ArrayList<>();
//        purchaseOrder.setPurchaseOrderDetails(detailsList);
//
//        List<SupplierDTO> supplierDTOs = supplierService.getAllSuppliers().stream()
//                .map(supplier -> new SupplierDTO(supplier.getSupplierId(), supplier.getSupplierName()))
//                .collect(Collectors.toList());
//
//        List<BranchDTO> branchDTOs = branchService.getAllBranches().stream()
//                .map(branch -> new BranchDTO(branch.getBranchId(), branch.getBranchName()))
//                .collect(Collectors.toList());
//
//        List<ProductDTO> productDTOs = productService.getAllProducts().stream()
//                .map(product -> new ProductDTO(product.getProductID(), product.getProductName()))
//                .collect(Collectors.toList());
//
//        model.addAttribute("purchaseOrder", purchaseOrder);
//        model.addAttribute("suppliers", supplierDTOs);
//        model.addAttribute("branches", branchDTOs);
//        model.addAttribute("products", productDTOs);
//
//        return "purchase-order/add";
//    }

    @GetMapping("/add")
    public String showAddPurchaseOrderForm(Model model) {
        PurchaseOrderDTO purchaseOrderDTO = new PurchaseOrderDTO();
        purchaseOrderDTO.setOrderDate(LocalDate.now());
        purchaseOrderDTO.setStatus("Pending");

        // Không cần khởi tạo danh sách chi tiết nếu đã khởi tạo mặc định

        // Lấy danh sách DTO cho suppliers, branches, products (giữ nguyên)
        List<SupplierDTO> supplierDTOs = supplierService.getAllSuppliers().stream()
                .map(supplier -> new SupplierDTO(supplier.getSupplierId(), supplier.getSupplierName()))
                .collect(Collectors.toList());

        List<BranchDTO> branchDTOs = branchService.getAllBranches().stream()
                .map(branch -> new BranchDTO(branch.getBranchId(), branch.getBranchName()))
                .collect(Collectors.toList());

        List<ProductDTO> productDTOs = productService.getAllProducts().stream()
                .map(product -> new ProductDTO(product.getProductID(), product.getProductName()))
                .collect(Collectors.toList());

        model.addAttribute("purchaseOrderDTO", purchaseOrderDTO);
        model.addAttribute("suppliers", supplierDTOs);
        model.addAttribute("branches", branchDTOs);
        model.addAttribute("products", productDTOs);

        return "purchase-order/add";
    }


//    @PostMapping("/add")
//    public String addPurchaseOrder(@ModelAttribute("purchaseOrder") PurchaseOrder purchaseOrder,
//                                   BindingResult result,
//                                   Model model) {
//
//        if (result.hasErrors()) {
//            model.addAttribute("suppliers", supplierService.getAllSuppliers());
//            model.addAttribute("branches", branchService.getAllBranches());
//            model.addAttribute("products", productService.getAllProducts());
//            return "purchase-order/add";
//        }
//
//        // Lấy Supplier từ cơ sở dữ liệu
//        Optional<Supplier> supplierOpt = supplierService.getSupplierById(purchaseOrder.getSupplier().getSupplierId());
//        if (supplierOpt.isPresent()) {
//            purchaseOrder.setSupplier(supplierOpt.get());
//        } else {
//            // Xử lý khi không tìm thấy Supplier
//        }
//
//        // Lấy Branch từ cơ sở dữ liệu
//        Optional<Branch> branchOpt = branchService.getBranchById(purchaseOrder.getBranch().getBranchId());
//        if (branchOpt.isPresent()) {
//            purchaseOrder.setBranch(branchOpt.get());
//        } else {
//            // Xử lý khi không tìm thấy Branch
//        }
//
//        // Xử lý các PurchaseOrderDetails
//        List<PurchaseOrderDetails> detailsList = purchaseOrder.getPurchaseOrderDetails();
//        for (PurchaseOrderDetails detail : detailsList) {
//            // Thiết lập PurchaseOrder cho mỗi detail
//            detail.setPurchaseOrder(purchaseOrder);
//
//            // Lấy Product từ cơ sở dữ liệu
//            Optional<Product> productOpt = productService.getProductById(detail.getProduct().getProductID());
//            if (productOpt.isPresent()) {
//                detail.setProduct(productOpt.get());
//            } else {
//                // Xử lý khi không tìm thấy Product
//            }
//        }
//
//        // Tính tổng số tiền
//        purchaseOrder.updateTotalAmount();
//
//        // Lưu đơn đặt hàng
//        purchaseOrderService.addPurchaseOrder(purchaseOrder);
//
//        return "redirect:/purchase-orders"; // Điều hướng đến trang danh sách đơn đặt hàng
//    }
@PostMapping("/add")
public String addPurchaseOrder(@ModelAttribute("purchaseOrderDTO") PurchaseOrderDTO purchaseOrderDTO,
                               BindingResult result,
                               Model model) {

    if (result.hasErrors()) {
        model.addAttribute("suppliers", supplierService.getAllSuppliers());
        model.addAttribute("branches", branchService.getAllBranches());
        model.addAttribute("products", productService.getAllProducts());
        return "purchase-order/add";
    }

    // Chuyển đổi từ DTO sang Entity
    PurchaseOrder purchaseOrder = new PurchaseOrder();
    purchaseOrder.setOrderDate(purchaseOrderDTO.getOrderDate());
    purchaseOrder.setStatus(purchaseOrderDTO.getStatus());

    // Lấy Supplier từ cơ sở dữ liệu
    Optional<Supplier> supplierOpt = supplierService.getSupplierById(purchaseOrderDTO.getSupplierId());
    if (supplierOpt.isPresent()) {
        purchaseOrder.setSupplier(supplierOpt.get());
    } else {
        // Xử lý khi không tìm thấy Supplier
    }

    // Lấy Branch từ cơ sở dữ liệu
    Optional<Branch> branchOpt = branchService.getBranchById(purchaseOrderDTO.getBranchId());
    if (branchOpt.isPresent()) {
        purchaseOrder.setBranch(branchOpt.get());
    } else {
        // Xử lý khi không tìm thấy Branch
    }

    // Xử lý các PurchaseOrderDetails
    List<PurchaseOrderDetails> detailsList = new ArrayList<>();
    for (PurchaseOrderDetailDTO detailDTO : purchaseOrderDTO.getPurchaseOrderDetails()) {
        PurchaseOrderDetails detail = new PurchaseOrderDetails();
        detail.setPurchaseOrder(purchaseOrder);
        detail.setQuantity(detailDTO.getQuantity());
        detail.setUnitPrice(detailDTO.getUnitPrice());

        // Lấy Product từ cơ sở dữ liệu
        Optional<Product> productOpt = productService.getProductById(detailDTO.getProductId());
        if (productOpt.isPresent()) {
            detail.setProduct(productOpt.get());
        } else {
            // Xử lý khi không tìm thấy Product
        }

        detailsList.add(detail);
    }
    purchaseOrder.setPurchaseOrderDetails(detailsList);

    // Tính tổng số tiền
    purchaseOrder.updateTotalAmount();

    // Lưu đơn đặt hàng
    purchaseOrderService.addPurchaseOrder(purchaseOrder);

    return "redirect:/purchase-orders";
}


    @GetMapping
    public String listPurchaseOrders(Model model) {
        model.addAttribute("purchaseOrders", purchaseOrderService.getAllPurchaseOrders());
        return "purchase-order/list";
    }

    // Các phương thức khác như chỉnh sửa, xóa nếu cần thiết
}
