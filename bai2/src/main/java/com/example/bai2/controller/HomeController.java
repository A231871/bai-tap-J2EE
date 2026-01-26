package com.example.bai2.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
    @GetMapping("Hello")
    public String Hello(){
        return "Hello!!";
    }
}