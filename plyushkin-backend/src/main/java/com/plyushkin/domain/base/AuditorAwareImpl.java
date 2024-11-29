package com.plyushkin.domain.base;

import com.plyushkin.domain.user.User;
import com.plyushkin.domain.value.ID;
import org.springframework.data.domain.AuditorAware;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
class AuditorAwareImpl implements AuditorAware<ID<User>> {
    private static final UUID VALUE = UUID.fromString("99723d04-ecd0-48b8-804b-bbb0acda540e");

    @Override
    @NonNull
    // Temporary implementation
    public Optional<ID<User>> getCurrentAuditor() {
        return Optional.of(new ID<>(VALUE));
    }
}
