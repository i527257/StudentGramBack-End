package com.studen.studentgrams.Features.Favorite;

import com.studen.studentgrams.Features.Post.Post;
import com.studen.studentgrams.Features.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
    boolean existsByUserAndPost(User user, Post post);
    List<Favorite> findByUser(User user);
    Optional<Favorite> findByUserAndPost(User user, Post post);
}
