package com.studen.studentgrams.Features.Favorite.endpoint;

import com.studen.studentgrams.Features.Favorite.FavoriteService;
import com.studen.studentgrams.Features.Post.Post;
import com.studen.studentgrams.Features.Post.endpoint.DTO.CreatePost;
import com.studen.studentgrams.Features.Post.endpoint.DTO.PostResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/favorites")
@RequiredArgsConstructor
public class FavoriteController {

    private final FavoriteService favoriteService;

    @PostMapping("/{postId}")
    public void favoritePost(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long postId) {
        Long userId = Long.valueOf(userDetails.getUsername());
        favoriteService.favoritePost(userId, postId);
    }

    @GetMapping("/checkstatus/{postId}")
    public ResponseEntity<Boolean> isPostFavorited(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long postId) {
        Long userId = Long.valueOf(userDetails.getUsername());
        boolean isFavorited = favoriteService.isPostFavorited(userId, postId);
        return ResponseEntity.ok(isFavorited);
    }


    @DeleteMapping("/{postId}")
    public void unfavoritePost(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long postId) {
        Long userId = Long.valueOf(userDetails.getUsername());
        favoriteService.unfavoritePost(userId, postId);
    }

    @GetMapping
    public List<PostResponse> getUserFavorites(@AuthenticationPrincipal UserDetails userDetails) {
        Long userId = Long.valueOf(userDetails.getUsername());
        List<Post> favorites = favoriteService.getUserFavorites(userId);

        return favorites.stream().map(post -> new PostResponse(
                post.getId(),
                Base64.getEncoder().encodeToString(post.getImage()),
                post.getDescription(),
                post.getUser().getId()
        )).collect(Collectors.toList());
    }
}
