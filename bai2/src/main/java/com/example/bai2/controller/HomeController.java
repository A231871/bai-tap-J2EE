package com.example.bai2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Controller
public class HomeController {
    @GetMapping("/home")
    public String index(){
        return "index";
    }
    @GetMapping("/test")
    public String test(Model model) {
        model.addAttribute("message", "message from the controler.");

        Map<String, String> homeData = new HashMap<>();
        homeData.put("title", "title from controller");
        model.addAttribute("home", homeData);

        return "test";
    }
}