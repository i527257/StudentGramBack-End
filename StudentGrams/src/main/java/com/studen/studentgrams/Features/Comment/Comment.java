package com.studen.studentgrams.Features.Comment;

import com.studen.studentgrams.Features.Post.Post;
import com.studen.studentgrams.Features.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Entity
@Table
@Data
@AllArgsConstructor
public class Comment {

    @Id
    @SequenceGenerator(
            name = "Comment_Sequence",
            sequenceName = "Comment_Sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "Comment_Sequence"
    )
    private Long id;

    @Column(nullable = false, length = 300)
    private String message;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @Transient
    private String displayname;

    @Transient
    private byte[] profilePicture;

    public Comment() {
    }

    public Comment(String message, User user, Post post) {
        this.message = message;
        this.user = user;
        this.post = post;
    }
}
