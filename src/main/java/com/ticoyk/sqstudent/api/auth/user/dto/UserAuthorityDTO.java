package com.ticoyk.sqstudent.api.auth.user.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class UserAuthorityDTO {

    private String username;
    private String authority;

}
