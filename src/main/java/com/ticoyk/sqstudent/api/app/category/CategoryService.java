package com.ticoyk.sqstudent.api.app.category;

import com.ticoyk.sqstudent.api.app.category.dto.CategoryDTO;
import com.ticoyk.sqstudent.api.app.category.dto.CategoryFormDTO;
import com.ticoyk.sqstudent.api.app.dto.PageDTO;
import org.springframework.data.domain.Pageable;

public interface CategoryService {

    Category findCategoryById(Long id);
    CategoryDTO findCategoryDTOById(Long id);
    PageDTO<CategoryDTO, Category> findAll(Pageable pageable);
    CategoryDTO saveCategory(CategoryFormDTO categoryFormDTO);

}
