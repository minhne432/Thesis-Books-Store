package com.comestic.shop.service;

import com.comestic.shop.model.Review;
import com.comestic.shop.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    public void save(Review review) {
        reviewRepository.save(review);
    }

}
