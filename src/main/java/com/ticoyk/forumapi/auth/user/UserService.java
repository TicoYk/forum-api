package com.ticoyk.forumapi.auth.user;

import java.util.List;

public interface UserService {

    List<User> getUsers();
    User saveUser(User user);
    User getUser(String username);
    void changeUserAuthority(String username, String authority) throws Exception;
    void changeUserTitle(String username, String title) throws Exception;

}
