package com.ticoyk.sqstudent.api.auth.user.attributes.title;

import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class TitleServiceImpl implements TitleService {

    private final TitleRepository titleRepository;

    public TitleServiceImpl(TitleRepository titleRepository) {
        this.titleRepository = titleRepository;
    }

    @Override
    public List<Title> getTitles() {
        return titleRepository.findAll();
    }

    @Override
    public Title getTitle(String identifier) {
        if (isIdentifierId(identifier)) {
            return titleRepository.getById(Long.getLong(identifier));
        }
        return titleRepository.findByName(identifier);
    }

    @Override
    public Title updateTitleName(String identifier, String newName) {
        Title title = getTitle(identifier);
        title.setName(newName);
        return title;
    }

    @Override
    public Title createTitle(String name) {
        Title title = new Title();
        title.setName(name);
        return titleRepository.save(title);
    }

    @Override
    public boolean isTitleValid(String identifier) {
        if(isIdentifierId(identifier)) {
            return titleRepository.existsById(Long.getLong(identifier));
        }
        return titleRepository.existsByName(identifier);
    }

    private boolean isIdentifierId(String identifier) {
        return identifier.matches("[0-9]");
    }

}
