package com.studen.studentgrams.Features.Comment;

import com.studen.studentgrams.Features.Comment.endpoint.DTO.CommentResponse;
import com.studen.studentgrams.Features.Comment.endpoint.DTO.CreateCommentRequest;
import com.studen.studentgrams.Features.Post.Post;
import com.studen.studentgrams.Features.Post.PostRepository;
import com.studen.studentgrams.Features.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    public Comment createComment(CreateCommentRequest request, Long postId, User user) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalStateException("Post not found"));
        Comment comment = new Comment(
                request.message(),
                user,
                post
        );

        return commentRepository.save(comment);
    }

    public void deleteCommentById(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalStateException("Comment not found"));
        commentRepository.delete(comment);
    }

    public List<CommentResponse> getCommentsByPostId(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalStateException("Post not found"));
        List<Comment> comments = commentRepository.findByPost(post);

        return comments.stream()
                .map(comment -> {
                    String base64ProfilePicture = Base64.getEncoder().encodeToString(comment.getUser().getProfilePicture());
                    return new CommentResponse(
                            comment.getId(),
                            comment.getUser().getDisplayname(),
                            base64ProfilePicture,
                            comment.getMessage(),
                            comment.getUser().getId());

                })
                .collect(Collectors.toList());

    }
}
