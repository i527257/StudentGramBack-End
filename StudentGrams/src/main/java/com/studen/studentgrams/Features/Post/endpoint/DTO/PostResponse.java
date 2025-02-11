package com.studen.studentgrams.Features.Post.endpoint.DTO;

public record PostResponse(
        Long id,
        String image,
        String description,
        Long user_id) {
}
