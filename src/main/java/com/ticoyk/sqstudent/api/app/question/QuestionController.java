package com.ticoyk.sqstudent.api.app.question;

import com.ticoyk.sqstudent.api.app.comment.Comment;
import com.ticoyk.sqstudent.api.app.comment.CommentDTO;
import com.ticoyk.sqstudent.api.app.dto.PageDTO;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    @GetMapping
    public ResponseEntity<PageDTO<Question>> getQuestions(@RequestParam(defaultValue = "0") int page,
                                                          @RequestParam(defaultValue = "3") int size) {
        Pageable paging = PageRequest.of(page, size);
        return ResponseEntity.ok(this.questionService.findAll(paging));
    }

    @PostMapping
    public ResponseEntity<Question> saveQuestion(@RequestBody @Valid QuestionDTO questionDTO, Authentication authentication) {
        Question createdQuestion = this.questionService.saveQuestion(questionDTO, authentication);
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/").toUriString());
        return ResponseEntity.created(uri).body(createdQuestion);
    }

    @DeleteMapping("{/id}")
    public ResponseEntity<Question> removeQuestion(@PathVariable Long id, Authentication authentication) {
        Question removedQuestion = this.questionService.removeQuestion(id, authentication);
        return ResponseEntity.ok(removedQuestion);
    }

    @PutMapping({"/{id}"})
    public ResponseEntity<Question> updateQuestion(@PathVariable Long id,
                                                   @RequestBody @Valid QuestionDTO questionDTO, Authentication authentication) {
        Question createdQuestion = this.questionService.updateQuestion(id, questionDTO, authentication);
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/").toUriString());
        return ResponseEntity.created(uri).body(createdQuestion);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Question> findQuestionById(@PathVariable Long id) {
        Question question = this.questionService.findQuestionById(id);
        return ResponseEntity.ok(question);
    }

    @PostMapping("/{id}/comments")
    public ResponseEntity<Comment> addComment(@PathVariable Long id, @RequestBody @Valid CommentDTO commentDTO,
                                              Authentication authentication) {
        Comment createdComment = this.questionService.addComment(id, commentDTO, authentication);
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/").toUriString());
        return ResponseEntity.created(uri).body(createdComment);
    }

    @GetMapping("/{id}/comments")
    public ResponseEntity<PageDTO<Comment>> getQuestionComments( @PathVariable Long id,
            @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "3") int size) {
        Pageable paging = PageRequest.of(page, size);
        return ResponseEntity.ok(this.questionService.findAllQuestionComments(id, paging));
    }

    @DeleteMapping("/comments/{id}")
    public ResponseEntity<Comment> removeComment(@PathVariable Long id, Authentication authentication) {
        Comment removedComment = this.questionService.removeComment(id, authentication);
        return ResponseEntity.ok(removedComment);
    }

    @PutMapping("/comments/{id}")
    public ResponseEntity<Comment> updateComment(@PathVariable Long id,
                                                 @RequestBody @Valid CommentDTO commentDTO, Authentication authentication) {
        Comment updatedcomment = this.questionService.updateComment(id, commentDTO, authentication);
        return ResponseEntity.ok(updatedcomment);
    }

}
