package com.studen.studentgrams.Features.Comment.endpoint.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CommentDTO {
    private Long id;
    private String message;
    private Long userId;
    private String username;
    private byte[] profilePicture;
    
    public CommentDTO(Long id, String message, Long userId, String username, byte[] profilePicture) {
        this.id = id;
        this.message = message;
        this.userId = userId;
        this.username = username;
        this.profilePicture = profilePicture;
    }
}

