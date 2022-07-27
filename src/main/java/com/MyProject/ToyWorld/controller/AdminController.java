package com.MyProject.ToyWorld.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @GetMapping()
    public String showAdminHome(){
        return "admin-home";
    }

    @GetMapping("/product")
    public String showProductList(){
        return "product-management";
    }

    @GetMapping("/product/add")
    public String showAddProductPage(){
        return "add-new-product";
    }

    @GetMapping("/product/update")
    public String showUpdateProductPage(){
        return "update-product";
    }

    @GetMapping("/user")
    public String showUserList(){
        return "user-management";
    }

    @GetMapping("/user/add")
    public String showAddUserPage(){
        return "add-new-user";
    }

    @GetMapping("/user/update")
    public String showUpdateUserPage(){
        return "update-user";
    }
}
