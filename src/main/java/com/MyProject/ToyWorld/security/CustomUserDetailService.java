package com.MyProject.ToyWorld.security;

import com.MyProject.ToyWorld.entity.Cart;
import com.MyProject.ToyWorld.entity.User;
import com.MyProject.ToyWorld.repository.CartRepository;
import com.MyProject.ToyWorld.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {

    private UserRepository userRepository;
    private CartRepository cartRepository;

    public CustomUserDetailService(UserRepository userRepository, CartRepository cartRepository) {
        this.userRepository = userRepository;
        this.cartRepository = cartRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Account not found"));
        Cart cart = cartRepository.findByUserId(user.getId()).orElseGet(() -> cartRepository.save(new Cart(user)));
        return new CustomUserDetails(user, cart);
    }
}
