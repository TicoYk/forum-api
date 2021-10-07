package com.ticoyk.sqstudent.api.app.category;

import com.ticoyk.sqstudent.api.app.category.dto.CategoryDTO;
import com.ticoyk.sqstudent.api.app.category.dto.CategoryFormDTO;
import com.ticoyk.sqstudent.api.app.dto.PageDTO;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;


@RestController
@RequestMapping("/categories")
@AllArgsConstructor
public class CategoryController {

    private CategoryService categoryService;

    @GetMapping
    public ResponseEntity<PageDTO<CategoryDTO, Category>> getQuestions(@RequestParam(defaultValue = "0") int page,
                                                                      @RequestParam(defaultValue = "3") int size) {
        Pageable paging = PageRequest.of(page, size);
        return ResponseEntity.ok(this.categoryService.findAll(paging));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> findCategoryById(@PathVariable Long id) {
        return ResponseEntity.ok(this.categoryService.findCategoryDTOById(id));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('admin') or hasAuthority('manager')")
    public ResponseEntity<CategoryDTO> saveCategory(@RequestBody @Valid CategoryFormDTO categoryFormDTO) {
        CategoryDTO categoryDTO = this.categoryService.saveCategory(categoryFormDTO);
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/" + categoryDTO.getId()).toUriString());
        return ResponseEntity.created(uri).body(categoryDTO);
    }

}
