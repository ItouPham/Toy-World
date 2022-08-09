package com.MyProject.ToyWorld.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    @RequestMapping("/")
    public String viewHomePage() {
        return "index";
    }


    @RequestMapping("/test")
    public String viewTestPage() {
        return "test";
    }

    @RequestMapping("/403")
    public String show403Page(){
        return "403";
    }
}
