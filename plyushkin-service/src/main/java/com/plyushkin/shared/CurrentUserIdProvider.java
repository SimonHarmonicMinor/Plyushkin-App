package com.plyushkin.shared;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class CurrentUserIdProvider {
    public UserId get() {
        final var authentication = (UserAuthentication) SecurityContextHolder.getContext().getAuthentication();
        return authentication.getPrincipal();
    }
}
