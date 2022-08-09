package com.MyProject.ToyWorld.service.impl;

import com.MyProject.ToyWorld.dto.AddProductToCartDTO;
import com.MyProject.ToyWorld.entity.Cart;
import com.MyProject.ToyWorld.entity.CartItem;
import com.MyProject.ToyWorld.entity.Product;
import com.MyProject.ToyWorld.exception.UnauthorizedException;
import com.MyProject.ToyWorld.repository.CartItemRepository;
import com.MyProject.ToyWorld.repository.CartRepository;
import com.MyProject.ToyWorld.service.CartService;
import com.MyProject.ToyWorld.service.ProductService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CartServiceImpl implements CartService {
    private CartRepository cartRepository;
    private ProductService productService;
    private CartItemRepository cartItemRepository;

    public CartServiceImpl(CartRepository cartRepository, ProductService productService, CartItemRepository cartItemRepository) {
        this.cartRepository = cartRepository;
        this.productService = productService;
        this.cartItemRepository = cartItemRepository;
    }


    private Cart findCartById(Long id) {
        return cartRepository.findById(id).
                orElseThrow(() -> new RuntimeException("Cart Not Found"));
    }

    @Override
    public List<CartItem> listCartItem(Long cartId) {
        return cartItemRepository.findByCartId(cartId);
    }

    @Override
    public void addProductToCart(Long cartId, AddProductToCartDTO addProductToCartDTO) {
        Cart cart = findCartById(cartId);

        Product product = productService.findProductById(addProductToCartDTO.getProductId());

        CartItem cartItem = cartItemRepository.
                findByCartIdAndProductId(cartId, addProductToCartDTO.getProductId()).
                orElseGet(()-> new CartItem(product, cart));

        cartItem.setQuantity(cartItem.getQuantity() +  addProductToCartDTO.getQuantity());

        cartItemRepository.save(cartItem);
    }

    @Override
    public void clearCart(Long cartId) {
        cartItemRepository.deleteByCartId(cartId);
    }

    @Override
    public void deleteCartItem(Long cartId, Long cartItemId) {
        CartItem cartItem = cartItemRepository.findById(cartId).orElseThrow(() -> new RuntimeException("Item Not Found"));
        if (cartItem.getCart().getId() != cartId){
            throw new UnauthorizedException();
        }

        cartItemRepository.delete(cartItem);
    }
}
