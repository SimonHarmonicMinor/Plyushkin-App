package com.plyushkin.infra.security;

import com.plyushkin.infra.properties.DefaultUsersProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class LocalPropertiesAuthenticationProvider implements AuthenticationProvider {
    private final DefaultUsersProperties defaultUsersProperties;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        final var request = (UsernamePasswordAuthenticationToken) authentication;
        return defaultUsersProperties.values()
                .entrySet()
                .stream()
                .filter(e -> e.getValue().username().equals(request.getPrincipal())
                        && e.getValue().password().equals(request.getCredentials()))
                .findFirst()
                .map(e -> new UserAuthentication(e.getKey(), e.getValue()))
                .orElseThrow(() -> new BadCredentialsException("Username is not found or password is incorrect"));
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
