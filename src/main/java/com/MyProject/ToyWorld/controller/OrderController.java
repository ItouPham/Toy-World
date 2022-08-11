package com.MyProject.ToyWorld.controller;

import com.MyProject.ToyWorld.entity.CartItem;
import com.MyProject.ToyWorld.entity.User;
import com.MyProject.ToyWorld.security.CustomUserDetails;
import com.MyProject.ToyWorld.service.AuthService;
import com.MyProject.ToyWorld.service.CartService;
import com.MyProject.ToyWorld.util.SecurityUtil;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;
import java.util.List;

@Controller
public class OrderController {
    private CartService cartService;
    private AuthService authService;

    public OrderController(CartService cartService, AuthService authService) {
        this.cartService = cartService;
        this.authService = authService;
    }

    @GetMapping("/checkout")
    public String showCheckoutPage(@AuthenticationPrincipal CustomUserDetails loggedUser, Model model){
        List<CartItem> cartItems = cartService.listCartItem(SecurityUtil.getCurrentUser().getCart().getId());
        BigDecimal itemCost  = BigDecimal.ZERO;
        BigDecimal totalCost = BigDecimal.ZERO;
        for (CartItem item : cartItems){
            itemCost = item.getProduct().getPrice().multiply(BigDecimal.valueOf(item.getQuantity()));
            totalCost = totalCost.add(itemCost);
        }
        User user = authService.findUserByEmail(loggedUser.getUsername());
        model.addAttribute("user",user);
        model.addAttribute("total", totalCost);
        model.addAttribute("cartItems", cartItems);
        return "checkout";
    }
}
