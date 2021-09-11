package com.ticoyk.forumapi.auth.user;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ticoyk.forumapi.auth.user.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
