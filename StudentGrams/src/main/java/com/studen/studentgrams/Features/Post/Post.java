package com.studen.studentgrams.Features.Post;

import com.studen.studentgrams.Features.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Builder
@Entity
@Table
@Data
@AllArgsConstructor
public class Post {
    @Id
    @SequenceGenerator(
            name = "Post_Sequence",
            sequenceName = "Post_Sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "Post_Sequence"
    )
    private Long id;

    @Column(nullable = false)
    private byte[] image;

    @Column(nullable = false, length = 300)
    private String description;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    public Post() {
    }

    public Post(byte[] image, String description, User user) {
        this.image = image;
        this.description = description;
        this.user = user;
    }

    public Long getUserId() {
        return user.getId();
    }
}
