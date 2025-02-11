package com.studen.studentgrams.Features.Comment.endpoint.DTO;

public record CommentResponse(
        Long commentId,
        String displayname,
        String profilePicture,
        String message,
        Long user_ID
) {
}
