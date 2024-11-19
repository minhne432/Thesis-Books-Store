package com.comestic.shop.service;

import com.comestic.shop.dto.ProductSalesDTO;
import com.comestic.shop.dto.ProductSalesDateDTO;

import java.util.Date;
import java.util.List;

public interface ProductStatisticsService {
    List<ProductSalesDTO> getBestSellingProductsByDay(Date date);
    List<ProductSalesDTO> getBestSellingProductsByMonth(int month, int year);
    List<ProductSalesDTO> getBestSellingProductsByYear(int year);
    //
    public List<ProductSalesDateDTO> getProductSalesBetweenDates(Date startDate, Date endDate);

    List<ProductSalesDateDTO> getProductSalesByDates(List<Date> dates);
}
