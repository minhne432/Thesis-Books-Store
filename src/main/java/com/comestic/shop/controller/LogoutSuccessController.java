package com.comestic.shop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LogoutSuccessController {

    @GetMapping("/logout-success")
    public String logoutSuccess() {
        return "logout-success"; // Trả về view logout-success.html
    }
}
