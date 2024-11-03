package com.comestic.shop.service;

import com.comestic.shop.model.Branch;
import com.comestic.shop.model.BranchDistance;
import com.comestic.shop.model.Ward;
import com.comestic.shop.repository.BranchDistanceRepository;
import com.comestic.shop.repository.BranchRepository;
import com.comestic.shop.repository.WardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class DistanceCalculationService {

    @Autowired
    private BranchRepository branchRepository;

    @Autowired
    private WardRepository wardRepository;

    @Autowired
    private BranchDistanceRepository branchDistanceRepository;

    public void calculateAndSaveDistances() {
        List<Branch> branches = branchRepository.findAll();
        List<Ward> wards = wardRepository.findAll();

        List<BranchDistance> distances = new ArrayList<>();

        for (Branch branch : branches) {
            // Kiểm tra và lấy tọa độ của Branch
            if (branch.getAddress() == null || branch.getAddress().getWard() == null ||
                    branch.getAddress().getWard().getLatitude() == null || branch.getAddress().getWard().getLongitude() == null) {
                // Xử lý trường hợp thiếu dữ liệu
                continue;
            }

            double branchLat = branch.getAddress().getWard().getLatitude().doubleValue();
            double branchLon = branch.getAddress().getWard().getLongitude().doubleValue();

            for (Ward ward : wards) {
                // Kiểm tra và lấy tọa độ của Ward
                if (ward.getLatitude() == null || ward.getLongitude() == null) {
                    continue;
                }

                double wardLat = ward.getLatitude().doubleValue();
                double wardLon = ward.getLongitude().doubleValue();

                double distance = calculateDistance(branchLat, branchLon, wardLat, wardLon);

                // Tạo đối tượng BranchDistance
                BranchDistance branchDistance = new BranchDistance(
                        branch,
                        ward,
                        BigDecimal.valueOf(distance)
                );

                distances.add(branchDistance);
            }
        }

        // Lưu tất cả khoảng cách vào cơ sở dữ liệu
        branchDistanceRepository.saveAll(distances);
    }

    // Hàm tính khoảng cách theo công thức Haversine
    public static double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        final int EARTH_RADIUS = 6371; // Bán kính Trái Đất tính bằng km

        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(dLon / 2) * Math.sin(dLon / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return EARTH_RADIUS * c; // Khoảng cách tính bằng km
    }
}
