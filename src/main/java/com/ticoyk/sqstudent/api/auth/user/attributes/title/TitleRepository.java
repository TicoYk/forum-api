package com.ticoyk.sqstudent.api.auth.user.attributes.title;


import org.springframework.data.jpa.repository.JpaRepository;

public interface TitleRepository extends JpaRepository<Title, Long> {
    Title findByName(String name);
    boolean existsByName(String name);
}
