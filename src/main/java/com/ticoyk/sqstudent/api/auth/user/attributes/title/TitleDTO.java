package com.ticoyk.sqstudent.api.auth.user.attributes.title;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@RequiredArgsConstructor
public class TitleDTO {

    @Size(min = 3, max = 60, message = "Title name size, should be between 3 and 60 ")
    @NotBlank(message = "Title name is required")
    private String name;

}
