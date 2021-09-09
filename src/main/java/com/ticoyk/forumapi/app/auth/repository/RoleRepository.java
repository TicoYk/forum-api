package com.ticoyk.forumapi.app.auth.repository;

import com.ticoyk.forumapi.app.auth.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
