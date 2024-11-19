package com.comestic.shop.service;

import com.comestic.shop.dto.ProductSalesDTO;
import com.comestic.shop.dto.ProductSalesDateDTO;
import com.comestic.shop.repository.OrderDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ProductStatisticsServiceImpl implements ProductStatisticsService {

    @Autowired
    private OrderDetailsRepository orderDetailsRepository;

    @Override
    public List<ProductSalesDTO> getBestSellingProductsByDay(Date date) {
        return orderDetailsRepository.findBestSellingProductsByDay(date);
    }

    @Override
    public List<ProductSalesDTO> getBestSellingProductsByMonth(int month, int year) {
        return orderDetailsRepository.findBestSellingProductsByMonth(month, year);
    }

    @Override
    public List<ProductSalesDTO> getBestSellingProductsByYear(int year) {
        return orderDetailsRepository.findBestSellingProductsByYear(year);
    }

    //
    @Override
    public List<ProductSalesDateDTO> getProductSalesBetweenDates(Date startDate, Date endDate) {
        return orderDetailsRepository.findProductSalesBetweenDates(startDate, endDate);
    }


    @Override
    public List<ProductSalesDateDTO> getProductSalesByDates(List<Date> dates) {
        return orderDetailsRepository.findProductSalesByDates(dates);
    }

}
