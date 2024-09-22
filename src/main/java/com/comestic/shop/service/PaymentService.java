package com.comestic.shop.service;

import com.comestic.shop.model.Payment;
import com.comestic.shop.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    public Optional<Payment> getPaymentById(Long id) {
        return paymentRepository.findById(id);
    }

    public Payment addPayment(Payment payment) {
        return paymentRepository.save(payment);
    }

    public Payment updatePayment(Long id, Payment paymentDetails) {
        Optional<Payment> optionalPayment = paymentRepository.findById(id);
        if (optionalPayment.isPresent()) {
            Payment payment = optionalPayment.get();
            payment.setOrder(paymentDetails.getOrder());
            payment.setPaymentDate(paymentDetails.getPaymentDate());
            payment.setAmountPaid(paymentDetails.getAmountPaid());
            payment.setPaymentStatus(paymentDetails.getPaymentStatus());
            return paymentRepository.save(payment);
        } else {
            return null; // Hoặc bạn có thể ném ra ngoại lệ tùy theo logic
        }
    }

    public void deletePayment(Long id) {
        paymentRepository.deleteById(id);
    }
}
