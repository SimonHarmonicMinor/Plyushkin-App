package com.plyushkin.user.security;

import com.plyushkin.user.properties.DefaultUsersProperties;
import com.plyushkin.user.domain.User;
import com.plyushkin.user.repository.UserRepository;
import com.plyushkin.shared.WriteTransactional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import static org.springframework.transaction.annotation.Propagation.REQUIRES_NEW;

@Component
@ConditionalOnProperty(value = "default-users.enable-creation", havingValue = "true")
@RequiredArgsConstructor
class DefaultUsersApplicationStartService {
    private final UserRepository userRepository;
    private final DefaultUsersProperties properties;

    @EventListener(ApplicationStartedEvent.class)
    @WriteTransactional(propagation = REQUIRES_NEW)
    public void createDefaultUsers() {
        for (final var userId : properties.values().keySet()) {
            if (!userRepository.existsById(userId)) {
                userRepository.save(new User(userId));
            }
        }
    }
}
