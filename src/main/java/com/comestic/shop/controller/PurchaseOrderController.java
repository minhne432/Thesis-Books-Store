package com.comestic.shop.controller;

import com.comestic.shop.dto.*;
import com.comestic.shop.model.*;
import com.comestic.shop.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
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
    public String listPurchaseOrders(
            @RequestParam(value = "purchaseOrderId", required = false) Long purchaseOrderId,
            @RequestParam(value = "supplierId", required = false) Long supplierId,
            @RequestParam(value = "branchId", required = false) Long branchId,
            @RequestParam(value = "startDate", required = false)
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @RequestParam(value = "endDate", required = false)
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate,
            @RequestParam(value = "status", required = false) PurchaseOrderStatus status,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            Model model) {

        Pageable pageable = PageRequest.of(page, size);

        Page<PurchaseOrder> purchaseOrderPage = purchaseOrderService.searchPurchaseOrders(
                purchaseOrderId, supplierId, branchId, startDate, endDate, status, pageable);

        model.addAttribute("purchaseOrders", purchaseOrderPage.getContent());
        model.addAttribute("purchaseOrderPage", purchaseOrderPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", purchaseOrderPage.getTotalPages());

        // Add suppliers and branches for the search form
        List<Supplier> suppliers = supplierService.getAllSuppliers();
        model.addAttribute("suppliers", suppliers);

        List<Branch> branches = branchService.getAllBranches();
        model.addAttribute("branches", branches);

        // Add statuses for the search form
        model.addAttribute("statuses", PurchaseOrderStatus.values());

        // Add search parameters to the model to preserve them in the view
        model.addAttribute("purchaseOrderId", purchaseOrderId);
        model.addAttribute("supplierId", supplierId);
        model.addAttribute("branchId", branchId);
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        model.addAttribute("status", status);

        // Build query string for pagination links
        StringBuilder queryString = new StringBuilder();
        if (purchaseOrderId != null) {
            queryString.append("&purchaseOrderId=").append(purchaseOrderId);
        }
        if (supplierId != null) {
            queryString.append("&supplierId=").append(supplierId);
        }
        if (branchId != null) {
            queryString.append("&branchId=").append(branchId);
        }
        if (startDate != null) {
            queryString.append("&startDate=").append(startDate);
        }
        if (endDate != null) {
            queryString.append("&endDate=").append(endDate);
        }
        if (status != null) {
            queryString.append("&status=").append(status.name());
        }
        model.addAttribute("queryString", queryString.toString());

        return "purchase-order/list";
    }


    @GetMapping("/details/{id}")
    public String viewPurchaseOrderDetails(@PathVariable("id") Long id, Model model) {
        Optional<PurchaseOrder> optionalPurchaseOrder = purchaseOrderService.getPurchaseOrderById(id);
        if (optionalPurchaseOrder.isPresent()) {
            PurchaseOrder purchaseOrder = optionalPurchaseOrder.get();
            model.addAttribute("purchaseOrder", purchaseOrder);

            // Danh sách trạng thái
            List<String> statuses = Arrays.asList("Pending", "Confirmed", "Processing", "Shipped", "Received", "Partially Received", "Completed", "Cancelled", "Returned", "Failed");
            model.addAttribute("statuses", statuses);

            return "purchase-order/details";
        } else {
            // Xử lý khi không tìm thấy đơn đặt hàng
            return "redirect:/purchase-orders?error=notfound";
        }
    }


    // Các phương thức khác như chỉnh sửa, xóa nếu cần thiết
    @PostMapping("/{id}/update-status")
    @ResponseBody
    public ResponseEntity<String> updateStatus(@PathVariable("id") Long purchaseOrderId,
                                               @RequestParam("status") String newStatus) {
        purchaseOrderService.updatePurchaseOrderStatus(purchaseOrderId, newStatus);
        return ResponseEntity.ok("Status updated successfully.");
    }

}
