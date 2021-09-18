package com.ticoyk.sqstudent.api.app.question;

import com.ticoyk.sqstudent.api.app.dto.PageDTO;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/questions")
@AllArgsConstructor
public class QuestionController {

    private QuestionService questionService;

    @GetMapping
    public ResponseEntity<PageDTO<Question>> getQuestions(@RequestParam(defaultValue = "0") int page,
                                                          @RequestParam(defaultValue = "3") int size) {
        Pageable paging = PageRequest.of(page, size);
        return ResponseEntity.ok(this.questionService.findAll(paging));
    }

    @PostMapping
    public ResponseEntity<Question> saveQuestion(@RequestBody Question question, Authentication authentication) {
        Question createdQuestion = this.questionService.saveQuestion(question, authentication);
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/").toUriString());
        return ResponseEntity.created(uri).body(createdQuestion);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Question> findQuestionById(@PathVariable Long id) {
        return ResponseEntity.ok(this.questionService.findQuestionById(id));
    }

}
