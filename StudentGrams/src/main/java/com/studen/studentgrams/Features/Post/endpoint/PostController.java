package com.studen.studentgrams.Features.Post.endpoint;


import com.studen.studentgrams.Features.Post.PostService;
import com.studen.studentgrams.Features.Post.endpoint.DTO.CreatePostRequest;
import com.studen.studentgrams.Features.Post.endpoint.DTO.PostResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/post")
public class PostController {
    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<PostResponse>> GetPostSByUserId
            (@PathVariable Long id, @RequestParam(defaultValue = "0") int page,
             @RequestParam(defaultValue = "9") int size) {
        var posts = postService.getPostsByUserId(id, page, size);
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<PostResponse> getPostById(@PathVariable Long postId) {
        var post = postService.getPostById(postId);
        return ResponseEntity.ok(post);
    }

    @PostMapping
    public ResponseEntity<CreatePostRequest> createPost(@RequestBody CreatePostRequest createPostRequest) {
        return null;
    }

}
