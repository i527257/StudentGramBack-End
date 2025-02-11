package com.studen.studentgrams.Features.Favorite;

import com.studen.studentgrams.Features.Post.Post;
import com.studen.studentgrams.Features.Post.PostRepository;
import com.studen.studentgrams.Features.user.User;
import com.studen.studentgrams.Features.user.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FavoriteService {

    private final FavoriteRepository favoriteRepository;
    private final UserService userService;
    private final PostRepository postRepository;

    @Autowired
    public FavoriteService(FavoriteRepository favoriteRepository, UserService userService, PostRepository postRepository) {
        this.favoriteRepository = favoriteRepository;
        this.userService = userService;
        this.postRepository = postRepository;
    }

    public void favoritePost(Long userId, Long postId) {
        User user = userService.getUserById(userId);
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalStateException("Post not found"));
        if (!favoriteRepository.existsByUserAndPost(user, post)) {
            Favorite favorite = new Favorite();
            favorite.setUser(user);
            favorite.setPost(post);
            favoriteRepository.save(favorite);
        }
    }


    public void unfavoritePost(Long userId, Long postId) {
        User user = userService.getUserById(userId);
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("Post not found"));
        Favorite favorite = favoriteRepository.findByUserAndPost(user, post)
                .orElseThrow(() -> new EntityNotFoundException("Favorite entry not found"));
        favoriteRepository.delete(favorite);
    }



    public List<Post> getUserFavorites(Long userId) {
        User user = userService.getUserById(userId);
        List<Favorite> favorites = favoriteRepository.findByUser(user);
        return favorites.stream()
                .map(Favorite::getPost)
                .collect(Collectors.toList());
    }

    public boolean isPostFavorited(Long userId, Long postId) {
        User user = userService.getUserById(userId);
        Post post = postRepository.findById(postId).orElseThrow(() -> new IllegalStateException("Post not found"));
        return favoriteRepository.existsByUserAndPost(user, post);
    }

}
