package com.MyProject.ToyWorld.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProductController {

    @GetMapping("/product")
    public String showProductPage(){
        return "product";
    }

    @GetMapping("/product-detail")
    public String showProductDetailPage(){
        return "product-detail";
    }
}
