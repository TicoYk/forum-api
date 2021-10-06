package com.ticoyk.sqstudent.api.app.dto;

import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
public class PageDTO<T, Y> {

    private int pageNumber;
    private int totalPages;
    private int size;
    private List<T> content;

    public PageDTO() {

    }

    public PageDTO(Page<Y> page) {
        this.pageNumber = page.getNumber();
        this.totalPages = page.getTotalPages();
        this.size = page.getSize();
    }

}
