package com.ticoyk.sqstudent.api.auth.user.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class UserTitleDTO {

    private String username;
    private String titleIdentifier;

}
