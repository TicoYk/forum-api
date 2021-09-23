package com.ticoyk.sqstudent.api.app.category;

import com.ticoyk.sqstudent.api.app.dto.PageDTO;
import com.ticoyk.sqstudent.api.exception.ContentNotFoundException;

import org.hibernate.Hibernate;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

import java.util.Optional;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private CategoryRepository categoryRepository;
    private CategoryUtil categoryUtil;

    @Override
    public Category saveCategory(CategoryDTO categoryDTO) {
        Category newCategory = new Category();
        newCategory.setName(categoryDTO.getName());
        return this.categoryRepository.save(newCategory);
    }

    @Override
    public PageDTO<Category> findAll(Pageable pageable) {
        return new PageDTO<>(categoryRepository.findAll(pageable));
    }

    @Override
    public Category findCategoryById(Long id) {
        Optional<Category> optionalCategory = this.categoryRepository.findById(id);
        if (optionalCategory.isPresent()) {
            return optionalCategory.get();
        }
        throw new ContentNotFoundException(categoryUtil.createContentNotFoundException("id", id.toString()));
    }

}
