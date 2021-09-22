package com.ticoyk.sqstudent.api.auth.user;

import com.ticoyk.sqstudent.api.auth.user.dto.UserDTO;

import java.util.List;

public interface UserService {

    List<User> getUsers();
    User saveUser(User user);
    User saveUser(UserDTO userDTO);
    User getUser(String username);
    void changeUserAuthority(String username, String authority) throws Exception;
    void changeUserTitle(String username, String title) throws Exception;

}
