package com.ticoyk.sqstudent.api.app.author;

import com.ticoyk.sqstudent.api.app.author.dto.AuthorDTO;
import com.ticoyk.sqstudent.api.auth.user.User;
import org.springframework.stereotype.Component;

@Component
public class AuthorConverter {

    public AuthorDTO convertUserToAuthorDTO(User user) {
        AuthorDTO authorDTO = new AuthorDTO();
        authorDTO.setUserId(user.getId());
        authorDTO.setName(user.getName());
        return authorDTO;
    }

}
