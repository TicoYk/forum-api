package com.ticoyk.sqstudent.api.app.category;

import com.ticoyk.sqstudent.api.app.category.dto.CategoryDTO;
import com.ticoyk.sqstudent.api.app.category.dto.CategoryFormDTO;
import com.ticoyk.sqstudent.api.app.dto.PageDTO;
import com.ticoyk.sqstudent.api.exception.ContentNotFoundException;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

import java.util.Optional;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private CategoryConverter categoryConverter;
    private CategoryRepository categoryRepository;
    private CategoryMSG categoryMSG;

    @Override
    public Category saveCategory(CategoryFormDTO categoryFormDTO) {
        Category newCategory = new Category();
        newCategory.setName(categoryFormDTO.getName());
        return this.categoryRepository.save(newCategory);
    }

    @Override
    public PageDTO<CategoryDTO, Category> findAll(Pageable pageable) {
        return this.categoryConverter.convertToPageCategoryDTO(this.categoryRepository.findAll(pageable));
    }

    @Override
    public Category findCategoryById(Long id) {
        Optional<Category> optionalCategory = this.categoryRepository.findById(id);
        if (optionalCategory.isPresent()) {
            return optionalCategory.get();
        }
        throw new ContentNotFoundException(categoryMSG.createContentNotFoundException("id", id.toString()));
    }

}
