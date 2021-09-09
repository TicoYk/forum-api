package com.ticoyk.forumapi.app.auth.repository;

import com.ticoyk.forumapi.app.auth.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
