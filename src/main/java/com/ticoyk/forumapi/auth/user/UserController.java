package com.ticoyk.forumapi.auth.user;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/auth")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/login").toUriString());
        return ResponseEntity.created(uri).body(userService.saveUser(user));
    }

    @GetMapping("/currentUser")
    public ResponseEntity<User> getUser(Authentication authentication) {
        User user = userService.getUser(authentication.getName());
        return ResponseEntity.ok().body(user);
    }

    @PatchMapping("/user/exchangeAuthority")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<?> exchangeUserAuthority(@RequestBody UserAuthorityForm form) throws Exception {
        userService.changeUserAuthority(form.getUsername(), form.getAuthority());
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/user/exchangeTitle")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<?> exchangeUserTitle(@RequestBody UserTitleForm form) throws Exception {
        userService.changeUserTitle(form.getUsername(), form.getTitleIdentifier());
        return ResponseEntity.ok().build();
    }

    static class UserTitleForm {

        private String username;
        private String titleIdentifier;

        public UserTitleForm(String username, String titleIdentifier) {
            this.username = username;
            this.titleIdentifier = titleIdentifier;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getTitleIdentifier() {
            return titleIdentifier;
        }

        public void setTitleIdentifier(String titleIdentifier) {
            this.titleIdentifier = titleIdentifier;
        }

    }

    static class UserAuthorityForm {

        private String username;
        private String authority;

        public UserAuthorityForm(String username, String authority) {
            this.username = username;
            this.authority = authority;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getAuthority() {
            return authority;
        }

        public void setAuthority(String authority) {
            this.authority = authority;
        }
    }

}
