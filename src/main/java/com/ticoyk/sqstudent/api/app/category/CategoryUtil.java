package com.ticoyk.sqstudent.api.app.category;

import org.springframework.stereotype.Component;

@Component
public class CategoryUtil {

    public String createContentNotFoundException(String attribute, String value) {
        return "Couldn't find Category with " + attribute + ":" + value;
    }

}
