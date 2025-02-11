package com.studen.studentgrams.Features.Post;

import com.studen.studentgrams.Features.Post.endpoint.DTO.CreatePost;
import com.studen.studentgrams.Features.Post.endpoint.DTO.CreatePostRequest;
import com.studen.studentgrams.Features.Post.endpoint.DTO.PostDTO;
import com.studen.studentgrams.Features.Post.endpoint.DTO.PostResponse;
import com.studen.studentgrams.Features.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    public PostDTO convertToDto(Post post) {
        return new PostDTO(
                post.getId(),
                post.getImage(),
                post.getDescription(),
                post.getUser().getId()
        );
    }

    public Page<PostResponse> getPostsByUserId(Long userId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return postRepository.findByUser_IdOrderByCreatedAtDesc(userId, pageable)
                .map(post -> {
                    String base64Image = Base64.getEncoder().encodeToString(post.getImage());
                    return new PostResponse(
                            post.getId(),
                            base64Image,
                            post.getDescription(),
                            post.getUser().getId());
                });
    }



    public PostResponse getPostById(Long postId) {
        var post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalStateException("Post not found"));
        String base64Image = Base64.getEncoder().encodeToString(post.getImage());
        return new PostResponse(
                post.getId(),
                base64Image,
                post.getDescription(),
                post.getUser().getId()
        );
    }


    public CreatePost createPost(CreatePostRequest createPostRequest, User user) {
        Post newPost = new Post(
                createPostRequest.image(),
                createPostRequest.Description(),
                user
        );

        Post savedPost = postRepository.save(newPost);
        return new CreatePost(
                savedPost.getId(),
                savedPost.getImage(),
                savedPost.getDescription()
        );
    }

}
