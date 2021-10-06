package com.ticoyk.sqstudent.api.app.question;

import com.ticoyk.sqstudent.api.app.comment.Comment;
import com.ticoyk.sqstudent.api.app.comment.dto.CommentDTO;
import com.ticoyk.sqstudent.api.app.comment.dto.CommentFormDTO;
import com.ticoyk.sqstudent.api.app.dto.PageDTO;
import com.ticoyk.sqstudent.api.app.question.dto.QuestionDTO;
import com.ticoyk.sqstudent.api.app.question.dto.QuestionFormDTO;
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

    @GetMapping("/{id}")
    public ResponseEntity<QuestionDTO> findQuestionById(@PathVariable Long id) {
        QuestionDTO question = this.questionService.findQuestionDTOById(id);
        return ResponseEntity.ok(question);
    }

    @GetMapping
    public ResponseEntity< PageDTO<QuestionDTO, Question>> getQuestions(@RequestParam(defaultValue = "0") int page,
                                                          @RequestParam(defaultValue = "3") int size) {
        Pageable paging = PageRequest.of(page, size);
        return ResponseEntity.ok(this.questionService.findAll(paging));
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

    @GetMapping("/{id}/comments")
    public ResponseEntity<PageDTO<CommentDTO, Comment>> getQuestionComments(@PathVariable Long id,
                                                                           @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "3") int size) {
        Pageable paging = PageRequest.of(page, size);
        return ResponseEntity.ok(this.questionService.findAllQuestionComments(id, paging));
    }

    @PostMapping("/{id}/comments")
    public ResponseEntity<Comment> addComment(@PathVariable Long id, @RequestBody @Valid CommentFormDTO commentDTO,
                                              Authentication authentication) {
        Comment createdComment = this.questionService.addComment(id, commentDTO, authentication);
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/").toUriString());
        return ResponseEntity.created(uri).body(createdComment);
    }

    @PutMapping("/comments/{id}")
    public ResponseEntity<Comment> updateComment(@PathVariable Long id,
                                                 @RequestBody @Valid CommentFormDTO commentDTO, Authentication authentication) {
        Comment updatedcomment = this.questionService.updateComment(id, commentDTO, authentication);
        return ResponseEntity.ok(updatedcomment);
    }

    @DeleteMapping("/comments/{id}")
    public ResponseEntity<Comment> removeComment(@PathVariable Long id, Authentication authentication) {
        Comment removedComment = this.questionService.removeComment(id, authentication);
        return ResponseEntity.ok(removedComment);
    }

}
