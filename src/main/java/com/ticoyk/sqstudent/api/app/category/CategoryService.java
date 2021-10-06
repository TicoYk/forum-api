package com.ticoyk.sqstudent.api.app.category;

import com.ticoyk.sqstudent.api.app.category.dto.CategoryDTO;
import com.ticoyk.sqstudent.api.app.category.dto.CategoryFormDTO;
import com.ticoyk.sqstudent.api.app.dto.PageDTO;
import org.springframework.data.domain.Pageable;

public interface CategoryService {

    Category saveCategory(CategoryFormDTO categoryFormDTO);
    PageDTO<CategoryDTO, Category> findAll(Pageable pageable);
    Category findCategoryById(Long id);

}
