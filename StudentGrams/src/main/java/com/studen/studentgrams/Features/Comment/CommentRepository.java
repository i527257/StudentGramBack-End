package com.studen.studentgrams.Features.Comment;

import com.studen.studentgrams.Features.Post.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPost(Post post);
}
