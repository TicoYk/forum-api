package com.ticoyk.sqstudent.api.app.category;

import com.ticoyk.sqstudent.api.app.category.dto.CategoryDTO;
import com.ticoyk.sqstudent.api.app.dto.PageDTO;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CategoryConverter {

    public PageDTO<CategoryDTO, Category> convertToPageCategoryDTO(Page<Category> categories) {
        PageDTO<CategoryDTO, Category> pageDTO = new PageDTO<>(categories);
        List<CategoryDTO> categoriesDTO =  categories.stream().map(this::convertToCategoryDTO).collect(Collectors.toList());
        pageDTO.setContent(categoriesDTO);
        return pageDTO;
    }

    public CategoryDTO convertToCategoryDTO(Category category) {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(category.getId());
        categoryDTO.setName(category.getName());
        return categoryDTO;
    }

}
