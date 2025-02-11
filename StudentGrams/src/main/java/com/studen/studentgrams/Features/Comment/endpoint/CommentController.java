package com.studen.studentgrams.Features.Comment.endpoint;

import com.studen.studentgrams.Features.Comment.Comment;
import com.studen.studentgrams.Features.Comment.CommentService;
import com.studen.studentgrams.Features.Comment.endpoint.DTO.CommentResponse;
import com.studen.studentgrams.Features.Comment.endpoint.DTO.CreateCommentRequest;
import com.studen.studentgrams.Features.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.Base64;
import java.util.List;
@RestController
@RequestMapping("/api/v1/comment")
public class CommentController {

    private final CommentService commentService;
    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public CommentController(CommentService commentService, SimpMessagingTemplate messagingTemplate) {
        this.commentService = commentService;
        this.messagingTemplate = messagingTemplate;
    }

    @PostMapping("/{postId}")
    public ResponseEntity<Comment> createComment(
            @PathVariable Long postId,
            @Valid @RequestBody CreateCommentRequest request,
            @AuthenticationPrincipal UserDetails userDetails) {

        User user = (User) userDetails;
        Comment savedComment = commentService.createComment(request, postId, user);
        CommentResponse commentResponse = new CommentResponse(
                savedComment.getId(),
                savedComment.getUser().getDisplayname(),
                Base64.getEncoder().encodeToString(savedComment.getUser().getProfilePicture()),
                savedComment.getMessage(),
                savedComment.getUser().getId()
        );
        messagingTemplate.convertAndSend("/topic/comments/" + postId, commentResponse);

        return ResponseEntity.status(201).body(savedComment);
    }


    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long commentId) {
        commentService.deleteCommentById(commentId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<List<CommentResponse>> getCommentsByPostId(@PathVariable Long postId) {
        List<CommentResponse> comments = commentService.getCommentsByPostId(postId);
        return ResponseEntity.ok(comments);
    }
}
