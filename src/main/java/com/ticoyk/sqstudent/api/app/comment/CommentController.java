package com.ticoyk.sqstudent.api.app.comment;

import com.ticoyk.sqstudent.api.app.comment.dto.CommentDTO;
import com.ticoyk.sqstudent.api.app.comment.dto.CommentFormDTO;
import com.ticoyk.sqstudent.api.app.dto.PageDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@AllArgsConstructor
public class CommentController {

    private CommentService commentService;

    @GetMapping("/questions/{questionId}/comments")
    public ResponseEntity<PageDTO<CommentDTO, Comment>> getQuestionComments(@PathVariable Long questionId,
                                                                            @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "3") int size) {
        return ResponseEntity.ok(this.commentService.findAllQuestionComments(page, size, questionId));
    }

    @PostMapping("/questions/{questionId}/comments")
    public ResponseEntity<CommentDTO> addComment(@PathVariable Long questionId,
                                                 @RequestBody @Valid CommentFormDTO commentDTO, Authentication authentication) {
        CommentDTO createdComment = this.commentService.addComment(questionId, commentDTO, authentication);
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/").toUriString());
        return ResponseEntity.created(uri).body(createdComment);
    }

    @PutMapping("/questions/comments/{commentId}")
    public ResponseEntity<CommentDTO> updateComment(@PathVariable Long commentId,
                                                 @RequestBody @Valid CommentFormDTO commentDTO, Authentication authentication) {
        CommentDTO updatedcomment = this.commentService.updateComment(commentId, commentDTO, authentication);
        return ResponseEntity.ok(updatedcomment);
    }

    @DeleteMapping("/questions/comments/{commentId}")
    public ResponseEntity<CommentDTO> removeComment(@PathVariable Long commentId, Authentication authentication) {
        CommentDTO removedComment = this.commentService.removeComment(commentId, authentication);
        return ResponseEntity.ok(removedComment);
    }

}
