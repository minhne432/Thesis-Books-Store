package com.comestic.shop.config;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

@Component
public class VNPayConfig {

    @Value("${vnpay.vnp_PayUrl}")
    private String vnp_PayUrl;

    @Value("${vnpay.vnp_ReturnUrl}")
    private String vnp_ReturnUrl;

    @Value("${vnpay.vnp_TmnCode}")
    private String vnp_TmnCode;

    @Value("${vnpay.vnp_HashSecret}")
    private String vnp_HashSecret;

    @Value("${vnpay.vnp_ApiUrl}")
    private String vnp_ApiUrl;

    // Getters...

    // HMAC SHA512 method
    public String hmacSHA512(final String key, final String data) {
        try {
            if (key == null || data == null) {
                throw new NullPointerException("Key or data is null");
            }
            Mac hmac512 = Mac.getInstance("HmacSHA512");
            byte[] hmacKeyBytes = key.getBytes(StandardCharsets.UTF_8);
            SecretKeySpec secretKey = new SecretKeySpec(hmacKeyBytes, "HmacSHA512");
            hmac512.init(secretKey);
            byte[] dataBytes = data.getBytes(StandardCharsets.UTF_8);
            byte[] result = hmac512.doFinal(dataBytes);
            StringBuilder sb = new StringBuilder(2 * result.length);
            for (byte b : result) {
                sb.append(String.format("%02x", b & 0xff));
            }
            return sb.toString();
        } catch (Exception ex) {
            throw new RuntimeException("Error generating HMAC SHA512 hash", ex);
        }
    }

    // IP Address retrieval method
    public String getIpAddress(HttpServletRequest request) {
        String ipAddress;
        try {
            ipAddress = request.getHeader("X-FORWARDED-FOR");
            if (ipAddress == null || ipAddress.isEmpty()) {
                ipAddress = request.getRemoteAddr();
                if (ipAddress.equals("0:0:0:0:0:0:0:1")) {
                    // Localhost IPv6
                    InetAddress inetAddress = InetAddress.getLocalHost();
                    ipAddress = inetAddress.getHostAddress();
                }
            }
        } catch (UnknownHostException e) {
            ipAddress = "Unknown";
        }
        return ipAddress;
    }

    // Random number generator
    public String getRandomNumber(int len) {
        Random rnd = new Random();
        String chars = "0123456789";
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            sb.append(chars.charAt(rnd.nextInt(chars.length())));
        }
        return sb.toString();
    }

    public String getVnp_PayUrl() {
        return vnp_PayUrl;
    }

    public void setVnp_PayUrl(String vnp_PayUrl) {
        this.vnp_PayUrl = vnp_PayUrl;
    }

    public String getVnp_ReturnUrl() {
        return vnp_ReturnUrl;
    }

    public void setVnp_ReturnUrl(String vnp_ReturnUrl) {
        this.vnp_ReturnUrl = vnp_ReturnUrl;
    }

    public String getVnp_TmnCode() {
        return vnp_TmnCode;
    }

    public void setVnp_TmnCode(String vnp_TmnCode) {
        this.vnp_TmnCode = vnp_TmnCode;
    }

    public String getVnp_HashSecret() {
        return vnp_HashSecret;
    }

    public void setVnp_HashSecret(String vnp_HashSecret) {
        this.vnp_HashSecret = vnp_HashSecret;
    }

    public String getVnp_ApiUrl() {
        return vnp_ApiUrl;
    }

    public void setVnp_ApiUrl(String vnp_ApiUrl) {
        this.vnp_ApiUrl = vnp_ApiUrl;
    }

    public boolean verifySignature(Map<String, String> fields) throws UnsupportedEncodingException {
        String vnp_SecureHash = fields.get("vnp_SecureHash");
        if (vnp_SecureHash == null) {
            return false;
        }

        // Remove vnp_SecureHash parameter
        fields.remove("vnp_SecureHash");
        fields.remove("vnp_SecureHashType");

        // Sort parameters
        List<String> fieldNames = new ArrayList<>(fields.keySet());
        Collections.sort(fieldNames);

        // Build hash data
        StringBuilder hashData = new StringBuilder();
        for (String fieldName : fieldNames) {
            String fieldValue = fields.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                hashData.append(fieldName);
                hashData.append('=');
                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.UTF_8.toString()));
                hashData.append('&');
            }
        }

        // Remove last '&'
        if (hashData.length() > 0) {
            hashData.deleteCharAt(hashData.length() - 1);
        }

        // Compute hash
        String secureHash = hmacSHA512(getVnp_HashSecret(), hashData.toString());

        // Compare with provided hash
        return secureHash.equals(vnp_SecureHash);
    }

}
