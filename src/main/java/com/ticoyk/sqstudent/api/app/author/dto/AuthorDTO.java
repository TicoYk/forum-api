package com.ticoyk.sqstudent.api.app.author.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AuthorDTO {
    private Long userId;
    private String name;
}
