package com.comestic.shop.service;

import com.comestic.shop.model.LoginHistory;
import com.comestic.shop.repository.LoginHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LoginHistoryService {

    @Autowired
    private LoginHistoryRepository loginHistoryRepository;

    public List<LoginHistory> getAllLoginHistories() {
        return loginHistoryRepository.findAll();
    }

    public Optional<LoginHistory> getLoginHistoryById(Long id) {
        return loginHistoryRepository.findById(id);
    }

    public List<LoginHistory> getLoginHistoriesByCustomerId(Long customerId) {
        return loginHistoryRepository.findByCustomerId(customerId);
    }

    public LoginHistory addLoginHistory(LoginHistory loginHistory) {
        return loginHistoryRepository.save(loginHistory);
    }

    public LoginHistory updateLoginHistory(Long id, LoginHistory loginHistoryDetails) {
        Optional<LoginHistory> optionalLoginHistory = loginHistoryRepository.findById(id);
        if (optionalLoginHistory.isPresent()) {
            LoginHistory loginHistory = optionalLoginHistory.get();
            loginHistory.setCustomer(loginHistoryDetails.getCustomer());
            loginHistory.setLoginTimestamp(loginHistoryDetails.getLoginTimestamp());
            loginHistory.setIpAddress(loginHistoryDetails.getIpAddress());
            loginHistory.setDeviceInfo(loginHistoryDetails.getDeviceInfo());
            loginHistory.setLoginStatus(loginHistoryDetails.getLoginStatus());
            return loginHistoryRepository.save(loginHistory);
        } else {
            return null; // Hoặc bạn có thể ném ra ngoại lệ tùy theo logic
        }
    }

    public void deleteLoginHistory(Long id) {
        loginHistoryRepository.deleteById(id);
    }
}
