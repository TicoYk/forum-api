package com.ticoyk.sqstudent.api.app.question;

import com.ticoyk.sqstudent.api.app.dto.PageDTO;
import com.ticoyk.sqstudent.api.app.question.dto.QuestionDTO;
import com.ticoyk.sqstudent.api.app.question.dto.QuestionFormDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/questions")
@AllArgsConstructor
public class QuestionController {

    private QuestionService questionService;

    @GetMapping("/{id}")
    public ResponseEntity<QuestionDTO> findQuestionById(@PathVariable Long id) {
        QuestionDTO question = this.questionService.findQuestionDTOById(id);
        return ResponseEntity.ok(question);
    }

    @GetMapping
    public ResponseEntity< PageDTO<QuestionDTO, Question>> getQuestions(@RequestParam(defaultValue = "0") int page,
                                                          @RequestParam(defaultValue = "3") int size) {
        return ResponseEntity.ok(this.questionService.findAll(page, size));
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity< PageDTO<QuestionDTO, Question>> getQuestionsByCategory(@RequestParam(defaultValue = "0") int page,
                                                                        @RequestParam(defaultValue = "3") int size, @PathVariable Long categoryId) {
        return ResponseEntity.ok(this.questionService.findAllByCategory(page, size, categoryId));
    }

    @PostMapping
    public ResponseEntity<QuestionDTO> saveQuestion(@RequestBody @Valid QuestionFormDTO questionFormDTO, Authentication authentication) {
        QuestionDTO createdQuestion = this.questionService.saveQuestion(questionFormDTO, authentication);
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/").toUriString());
        return ResponseEntity.created(uri).body(createdQuestion);
    }

    @PutMapping({"/{id}"})
    public ResponseEntity<QuestionDTO> updateQuestion(@PathVariable Long id,
                                                   @RequestBody @Valid QuestionFormDTO questionFormDTO, Authentication authentication) {
        QuestionDTO createdQuestion = this.questionService.updateQuestion(id, questionFormDTO, authentication);
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/").toUriString());
        return ResponseEntity.created(uri).body(createdQuestion);
    }

    @DeleteMapping("{/id}")
    public ResponseEntity<QuestionDTO> removeQuestion(@PathVariable Long id, Authentication authentication) {
        QuestionDTO removedQuestion = this.questionService.removeQuestion(id, authentication);
        return ResponseEntity.ok(removedQuestion);
    }

}
