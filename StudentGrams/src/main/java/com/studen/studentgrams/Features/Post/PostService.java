package com.studen.studentgrams.Features.Post;

import com.studen.studentgrams.Features.Post.endpoint.DTO.PostDTO;
import com.studen.studentgrams.Features.Post.endpoint.DTO.PostResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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

    public List<PostResponse> getPostsByUserId(Long userId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return postRepository.findByUserIdOrderByCreatedAtDesc(userId, pageable)
                .stream()
                .map(post -> new PostResponse(
                        post.getId(),
                        post.getImage(),
                        post.getDescription()))
                .collect(Collectors.toList());
    }

    public PostResponse getPostById(Long postId) {
        var post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalStateException("Post not found"));
        return new PostResponse(
                post.getId(),
                post.getImage(),
                post.getDescription()
        );
    }
}
