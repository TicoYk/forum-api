package com.ticoyk.forumapi.auth.user;

import java.util.List;

public interface UserService {
    User saveUser(User user);
    User getUser(String username);
    void changeUserAuthority(String username, String authority) throws Exception;
    List<User> getUsers();
}
