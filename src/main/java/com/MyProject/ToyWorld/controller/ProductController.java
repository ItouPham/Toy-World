package com.MyProject.ToyWorld.controller;

import com.MyProject.ToyWorld.dto.AddProductToCartDTO;
import com.MyProject.ToyWorld.service.CartService;
import com.MyProject.ToyWorld.service.ProductService;
import com.MyProject.ToyWorld.util.SecurityUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/product")
public class ProductController {
    private ProductService productService;
    private CartService cartService;

    public ProductController(ProductService productService, CartService cartService) {
        this.productService = productService;
        this.cartService = cartService;
    }

    @GetMapping()
    public String showProductPage(Model model) {
        model.addAttribute("products", productService.findAllProduct());
        return "product";
    }

    @GetMapping("/product-detail/{id}")
    public String showProductDetailPage(@PathVariable("id") Long id, Model model) {
        model.addAttribute("product", productService.findProductById(id));
        model.addAttribute("req", new AddProductToCartDTO(id, 1));
        return "product-detail";
    }

    @GetMapping("/addToCart")
    public String addProductToCart(@ModelAttribute("req") AddProductToCartDTO addProductToCartDTO,
                                   Model model, RedirectAttributes redirect) {
        model.addAttribute("product",productService.findProductById(addProductToCartDTO.getProductId()));
        cartService.addProductToCart(SecurityUtil.getCurrentUser().getCart().getId(),addProductToCartDTO);
        model.addAttribute("successMessage","Add product to cart successfully");
        return "product-detail";
    }
}
