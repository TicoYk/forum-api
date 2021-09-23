package com.ticoyk.sqstudent.api.app.category;

import com.ticoyk.sqstudent.api.app.dto.PageDTO;
import org.springframework.data.domain.Pageable;

public interface CategoryService {

    Category saveCategory(CategoryDTO categoryDTO);
    PageDTO<Category> findAll(Pageable pageable);
    Category findCategoryById(Long id);

}
