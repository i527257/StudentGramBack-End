package com.studen.studentgrams.Features.Post.endpoint;


import com.studen.studentgrams.Features.Post.PostService;
import com.studen.studentgrams.Features.Post.endpoint.DTO.CreatePost;
import com.studen.studentgrams.Features.Post.endpoint.DTO.CreatePostRequest;
import com.studen.studentgrams.Features.Post.endpoint.DTO.PostResponse;
import com.studen.studentgrams.Features.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
    public ResponseEntity<Page<PostResponse>> GetPostSByUserId(
            @PathVariable Long id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "9") int size) {
        Page<PostResponse> posts = postService.getPostsByUserId(id, page, size);
        return ResponseEntity.ok(posts);
    }


    @GetMapping("/post/{postId}")
    public ResponseEntity<PostResponse> getPostById(@PathVariable Long postId) {
        var post = postService.getPostById(postId);
        return ResponseEntity.ok(post);
    }

    @PostMapping
    public ResponseEntity<CreatePost> createPost(
            @RequestParam("description") String description,
            @RequestParam("image") MultipartFile image,
            @AuthenticationPrincipal UserDetails userDetails) throws IOException, IOException {

        User user = (User) userDetails;
        byte[] imageBytes = image.getBytes();
        CreatePostRequest createPostRequest = new CreatePostRequest(description, imageBytes);
        CreatePost create = postService.createPost(createPostRequest, user);
        return ResponseEntity.status(201).body(create);
    }


}
