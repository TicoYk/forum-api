package com.ticoyk.sqstudent.api.auth.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/login").toUriString());
        return ResponseEntity.created(uri).body(this.userService.saveUser(user));
    }

    @GetMapping("/currentUser")
    public ResponseEntity<User> getUser(Authentication authentication) {
        User user = this.userService.getUser(authentication.getName());
        return ResponseEntity.ok().body(user);
    }

    @PatchMapping("/user/exchangeAuthority")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<?> exchangeUserAuthority(@RequestBody UserAuthorityForm form) throws Exception {
        this.userService.changeUserAuthority(form.getUsername(), form.getAuthority());
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/user/exchangeTitle")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<?> exchangeUserTitle(@RequestBody UserTitleForm form) throws Exception {
        this.userService.changeUserTitle(form.getUsername(), form.getTitleIdentifier());
        return ResponseEntity.ok().build();
    }

    @Data
    @RequiredArgsConstructor
    static class UserTitleForm {

        private String username;
        private String titleIdentifier;

    }

    @Data
    @RequiredArgsConstructor
    static class UserAuthorityForm {

        private String username;
        private String authority;

    }

}
