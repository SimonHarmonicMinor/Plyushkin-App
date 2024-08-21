package com.plyushkin.user.repository;

import com.plyushkin.user.domain.User;
import com.plyushkin.user.UserId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, UserId> {
}
