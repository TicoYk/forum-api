package com.ticoyk.forumapi.app.auth.service;

import com.ticoyk.forumapi.app.auth.domain.Role;
import com.ticoyk.forumapi.app.auth.domain.User;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

public interface UserService {
    User saveUser(User user);
    Role saveRole(Role role);
    void addRoleToUser(String username, String roleName);
    User getUser(String username);
    List<User> getUsers();
}
