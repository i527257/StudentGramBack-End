package com.studen.studentgrams.Features.Comment.endpoint.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateCommentRequest(
        @NotBlank(message = "Message cannot be blank")
        @Size(max = 300, message = "Message must not exceed 300 characters")
        String message
) {
}
