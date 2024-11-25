package com.plyushkin.domain.base;

import com.plyushkin.domain.user.User;
import com.plyushkin.domain.value.ID;
import org.springframework.data.domain.AuditorAware;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
class AuditorAwareImpl implements AuditorAware<ID<User>> {
    private static final long VALUE = 1L;

    @Override
    @NonNull
    // Temporary implementation
    public Optional<ID<User>> getCurrentAuditor() {
        return Optional.of(new ID<>(VALUE));
    }
}
