package com.plyushkin.infra.security;

import com.plyushkin.infra.properties.DefaultUsersProperties;
import com.plyushkin.user.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor
public class UserAuthentication implements Authentication {
    private final UserId userId;
    private final DefaultUsersProperties.Info info;
    private boolean authenticated = true;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getCredentials() {
        return info.password();
    }

    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public UserId getPrincipal() {
        return userId;
    }

    @Override
    public boolean isAuthenticated() {
        return authenticated;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        this.authenticated = isAuthenticated;
    }

    @Override
    public String getName() {
        return info.username();
    }
}
