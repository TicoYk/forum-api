package com.ticoyk.forumapi.app.question;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import java.awt.print.Pageable;

public interface QuestionRepository extends JpaRepository<Question, Long> {

}
