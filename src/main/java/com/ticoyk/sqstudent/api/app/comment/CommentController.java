package com.ticoyk.sqstudent.api.app.comment;

import com.ticoyk.sqstudent.api.app.comment.dto.CommentDTO;
import com.ticoyk.sqstudent.api.app.comment.dto.CommentFormDTO;
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
@AllArgsConstructor
public class CommentController {

    private CommentService commentService;

    @GetMapping("/questions/{id}/comments")
    public ResponseEntity<PageDTO<CommentDTO, Comment>> getQuestionComments(@PathVariable Long id,
                                                                            @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "3") int size) {
        Pageable paging = PageRequest.of(page, size);
        return ResponseEntity.ok(this.commentService.findAllQuestionComments(id, paging));
    }

    @PostMapping("/questions/{id}/comments")
    public ResponseEntity<CommentDTO> addComment(@PathVariable Long id, @RequestBody @Valid CommentFormDTO commentDTO,
                                              Authentication authentication) {
        CommentDTO createdComment = this.commentService.addComment(id, commentDTO, authentication);
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/").toUriString());
        return ResponseEntity.created(uri).body(createdComment);
    }

    @PutMapping("/questions/comments/{id}")
    public ResponseEntity<CommentDTO> updateComment(@PathVariable Long id,
                                                 @RequestBody @Valid CommentFormDTO commentDTO, Authentication authentication) {
        CommentDTO updatedcomment = this.commentService.updateComment(id, commentDTO, authentication);
        return ResponseEntity.ok(updatedcomment);
    }

    @DeleteMapping("/questions/comments/{id}")
    public ResponseEntity<CommentDTO> removeComment(@PathVariable Long id, Authentication authentication) {
        CommentDTO removedComment = this.commentService.removeComment(id, authentication);
        return ResponseEntity.ok(removedComment);
    }

}
