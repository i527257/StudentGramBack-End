package com.studen.studentgrams;

import com.studen.studentgrams.Features.Post.Post;
import com.studen.studentgrams.Features.Post.PostRepository;
import com.studen.studentgrams.Features.Post.PostService;
import com.studen.studentgrams.Features.Post.endpoint.DTO.PostResponse;
import com.studen.studentgrams.Features.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class PostServiceTest {

    @InjectMocks
    private PostService postService;

    @Mock
    private PostRepository postRepository;

    @Mock
    private User mockUser;

    private Post mockPost;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Mock user setup
        when(mockUser.getId()).thenReturn(1L);
        mockUser = User.builder()
                .id(1L)
                .firstname("John")
                .lastname("Doe")
                .email("john.doe@example.com")
                .password("password")
                .displayname("john_doe")
                .admin(false)
                .profilePicture(new byte[0])
                .build();

        mockPost = new Post(
                new byte[]{1, 2, 3, 4, 5},
                "This is a post description",
                mockUser,
                1L,
                LocalDateTime.now()
        );
    }

    @Test
    void testGetPostById_Success() {
        when(postRepository.findById(1L)).thenReturn(Optional.of(mockPost));
        PostResponse postResponse = postService.getPostById(1L);
        assertNotNull(postResponse);
        assertEquals(1L, postResponse.id()); // Ensure the ID matches
        assertEquals("This is a post description", postResponse.description());
        assertNotNull(postResponse.image());
    }

    @Test
    void testGetPostById_PostNotFound() {
        when(postRepository.findById(1L)).thenReturn(Optional.empty());
        Exception exception = assertThrows(IllegalStateException.class, () -> {
            postService.getPostById(1L);
        });
        assertEquals("Post not found", exception.getMessage());
    }
}
