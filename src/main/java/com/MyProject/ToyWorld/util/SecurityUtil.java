package com.MyProject.ToyWorld.util;

import com.MyProject.ToyWorld.exception.UnauthorizedException;
import com.MyProject.ToyWorld.security.CustomUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class SecurityUtil {

    public static CustomUserDetails getCurrentUser() {
        return Optional.ofNullable(SecurityContextHolder.getContext())
                .map(SecurityContext::getAuthentication)
                .map(Authentication::getPrincipal)
                .map(o -> {
                    if (o instanceof CustomUserDetails){
                        return o;
                    }
                    return null;
                })
                .map(CustomUserDetails.class::cast)
                .orElseThrow(UnauthorizedException::new);
    }
}
