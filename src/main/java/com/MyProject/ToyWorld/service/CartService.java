package com.MyProject.ToyWorld.service;

import com.MyProject.ToyWorld.dto.AddProductToCartDTO;
import com.MyProject.ToyWorld.entity.Cart;
import com.MyProject.ToyWorld.entity.CartItem;

import java.util.List;

public interface CartService {
    List<CartItem> listCartItem(Long cartId);
    void addProductToCart(Long cartId, AddProductToCartDTO addProductToCartDTO);
    void clearCart(Long cartId);
    void deleteCartItem(Long cartId, Long cartItemId);
}
