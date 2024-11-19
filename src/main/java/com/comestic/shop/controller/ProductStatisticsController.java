package com.comestic.shop.controller;

import com.comestic.shop.dto.BranchRevenueDTO;
import com.comestic.shop.dto.CategorySalesDTO;
import com.comestic.shop.dto.ProductSalesDTO;
import com.comestic.shop.dto.ProductSalesDateDTO;
import com.comestic.shop.model.OrderStatus;
import com.comestic.shop.service.OrderDetailsService;
import com.comestic.shop.service.OrderService;
import com.comestic.shop.service.ProductStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/statistics")
public class ProductStatisticsController {

    @Autowired
    private ProductStatisticsService productStatisticsService;

    @Autowired
    private OrderDetailsService orderDetailsService;

    @Autowired
    private OrderService orderService;

    @GetMapping("/best-selling-products")
    public String getBestSellingProducts(
            @RequestParam(value = "type", required = false, defaultValue = "day") String type,
            @RequestParam(value = "date", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date date,
            @RequestParam(value = "month", required = false) Integer month,
            @RequestParam(value = "year", required = false) Integer year,
            Model model) {

        List<ProductSalesDTO> productSales = new ArrayList<>();

        if ("day".equalsIgnoreCase(type)) {
            if (date == null) {
                date = new Date(); // Mặc định là ngày hôm nay
            }
            productSales = productStatisticsService.getBestSellingProductsByDay(date);
        } else if ("month".equalsIgnoreCase(type)) {
            if (month == null || year == null) {
                Calendar cal = Calendar.getInstance();
                month = cal.get(Calendar.MONTH) + 1; // Tháng bắt đầu từ 0
                year = cal.get(Calendar.YEAR);
            }
            productSales = productStatisticsService.getBestSellingProductsByMonth(month, year);
        } else if ("year".equalsIgnoreCase(type)) {
            if (year == null) {
                Calendar cal = Calendar.getInstance();
                year = cal.get(Calendar.YEAR);
            }
            productSales = productStatisticsService.getBestSellingProductsByYear(year);
        }

        // Chuẩn bị dữ liệu cho Chart.js
        List<String> productNames = productSales.stream()
                .map(ProductSalesDTO::getProductName)
                .collect(Collectors.toList());

        List<Long> quantities = productSales.stream()
                .map(ProductSalesDTO::getTotalQuantity)
                .collect(Collectors.toList());

        if (productSales.isEmpty()) {
            model.addAttribute("message", "Không có dữ liệu trong khoảng thời gian đã chọn.");
        }
        model.addAttribute("productNames", productNames);
        model.addAttribute("quantities", quantities);
        model.addAttribute("type", type);
        model.addAttribute("date", date);
        model.addAttribute("month", month);
        model.addAttribute("year", year);

        return "statistics/best-selling-products"; // Tên của template Thymeleaf
    }

    @GetMapping("/compare-best-selling-products")
    public String compareBestSellingProducts(
            @RequestParam(value = "startDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
            @RequestParam(value = "endDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate,
            Model model) {

        if (startDate == null) {
            // Mặc định là ngày hôm nay
            startDate = new Date();
        }
        if (endDate == null) {
            // Mặc định là ngày hôm nay
            endDate = new Date();
        }

        // Kiểm tra nếu startDate sau endDate
        if (startDate.after(endDate)) {
            model.addAttribute("error", "Ngày bắt đầu phải trước hoặc bằng ngày kết thúc.");
            return "statistics/compare-best-selling-products";
        }

        List<ProductSalesDateDTO> productSales = productStatisticsService.getProductSalesBetweenDates(startDate, endDate);

        // Chuẩn bị dữ liệu cho biểu đồ
        Map<Date, Map<String, Long>> dataMap = new TreeMap<>(); // TreeMap để sắp xếp theo ngày
        for (ProductSalesDateDTO dto : productSales) {
            dataMap.computeIfAbsent(dto.getDate(), k -> new HashMap<>())
                    .put(dto.getProductName(), dto.getTotalQuantity());
        }

        // Chuẩn bị danh sách sản phẩm và ngày
        Set<String> productNames = new HashSet<>();
        for (Map<String, Long> productMap : dataMap.values()) {
            productNames.addAll(productMap.keySet());
        }

        List<String> productNameList = new ArrayList<>(productNames);
        List<Date> dateLabels = new ArrayList<>(dataMap.keySet());

        // Chuẩn bị datasets cho biểu đồ
        List<Map<String, Object>> datasets = new ArrayList<>();
        for (String productName : productNameList) {
            List<Long> quantities = new ArrayList<>();
            for (Date date : dateLabels) {
                Long quantity = dataMap.get(date).getOrDefault(productName, 0L);
                quantities.add(quantity);
            }
            Map<String, Object> dataset = new HashMap<>();
            dataset.put("label", productName);
            dataset.put("data", quantities);
            dataset.put("backgroundColor", getRandomColor());
            dataset.put("borderColor", getRandomColor());
            dataset.put("borderWidth", 1);
            datasets.add(dataset);
        }

        model.addAttribute("dateLabels", dateLabels);
        model.addAttribute("datasets", datasets);
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);

        return "statistics/compare-best-selling-products";
    }

    private String getRandomColor() {
        Random random = new Random();
        int r = random.nextInt(256);
        int g = random.nextInt(256);
        int b = random.nextInt(256);
        return "rgba(" + r + ", " + g + ", " + b + ", 0.6)";
    }



    @GetMapping("/category-sales")
    public String categorySales(
            @RequestParam(value = "startDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
            @RequestParam(value = "endDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate,
            Model model) {

        List<CategorySalesDTO> categorySales;

        if (startDate != null && endDate != null) {
            // Kiểm tra nếu startDate sau endDate
            if (startDate.after(endDate)) {
                model.addAttribute("error", "Ngày bắt đầu phải trước hoặc bằng ngày kết thúc.");
                return "statistics/category-sales";
            }
            categorySales = orderDetailsService.getCategorySalesBetweenDates(startDate, endDate);
        } else {
            categorySales = orderDetailsService.getCategorySales();
        }

        // Chuẩn bị danh sách tên danh mục và số lượng
        List<String> categoryNames = categorySales.stream()
                .map(CategorySalesDTO::getCategoryName)
                .collect(Collectors.toList());

        List<Long> quantities = categorySales.stream()
                .map(CategorySalesDTO::getTotalQuantity)
                .collect(Collectors.toList());

        // Tạo mảng màu
        List<String> colors = generateRandomColors(categoryNames.size());

        model.addAttribute("categoryNames", categoryNames);
        model.addAttribute("quantities", quantities);
        model.addAttribute("colors", colors);
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);

        return "statistics/category-sales";
    }

    private List<String> generateRandomColors(int count) {
        List<String> colors = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < count; i++) {
            int r = random.nextInt(256);
            int g = random.nextInt(256);
            int b = random.nextInt(256);
            colors.add("rgba(" + r + ", " + g + ", " + b + ", 0.6)");
        }
        return colors;
    }

    @GetMapping("/branch-revenue")
    public String branchRevenue(
            @RequestParam(value = "status", required = false, defaultValue = "DELIVERED") OrderStatus status,
            @RequestParam(value = "startDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
            @RequestParam(value = "endDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate,
            Model model) {

        List<BranchRevenueDTO> branchRevenues;

        if (startDate != null && endDate != null) {
            // Kiểm tra nếu startDate sau endDate
            if (startDate.after(endDate)) {
                model.addAttribute("error", "Ngày bắt đầu phải trước hoặc bằng ngày kết thúc.");
                return "statistics/branch-revenue";
            }
            branchRevenues = orderService.getBranchRevenueByStatusAndDates(status, startDate, endDate);
        } else {
            branchRevenues = orderService.getBranchRevenueByStatus(status);
        }

        // Chuẩn bị danh sách tên chi nhánh và doanh thu
        List<String> branchNames = branchRevenues.stream()
                .map(BranchRevenueDTO::getBranchName)
                .collect(Collectors.toList());

        List<BigDecimal> revenues = branchRevenues.stream()
                .map(BranchRevenueDTO::getTotalRevenue)
                .collect(Collectors.toList());

        model.addAttribute("branchNames", branchNames);
        model.addAttribute("revenues", revenues);
        model.addAttribute("status", status);
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);

        return "statistics/branch-revenue";
    }


}
