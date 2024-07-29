package com.plyushkin.user.service;

import com.plyushkin.infra.security.UserAuthentication;
import com.plyushkin.user.UserId;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class CurrentUserIdProvider {
    public UserId get() {
        final var authentication = (UserAuthentication) SecurityContextHolder.getContext().getAuthentication();
        return authentication.getPrincipal();
    }
}
