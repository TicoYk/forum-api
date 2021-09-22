package com.ticoyk.sqstudent.api.auth.user.attributes.title;

import java.util.List;

public interface TitleService {

    List<Title> getTitles();
    Title getTitle(String identifier);
    Title updateTitleName(String name, String newName);
    Title saveTitle(TitleDTO titleDTO);
    boolean isTitleValid(String identifier);

}
