package com.plyushkin.infra.security;

import com.plyushkin.infra.properties.DefaultUsersProperties;
import com.plyushkin.user.User;
import com.plyushkin.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.transaction.annotation.Propagation.REQUIRES_NEW;

@Component
@ConditionalOnProperty(value = "default-users.enable-creation", havingValue = "true")
@RequiredArgsConstructor
class DefaultUsersApplicationStartService {
    private final UserRepository userRepository;
    private final DefaultUsersProperties properties;

    @EventListener(ApplicationStartedEvent.class)
    @Transactional(propagation = REQUIRES_NEW)
    public void createDefaultUsers() {
        for (final var userId : properties.values().keySet()) {
            if (!userRepository.existsById(userId)) {
                userRepository.save(new User(userId));
            }
        }
    }
}
