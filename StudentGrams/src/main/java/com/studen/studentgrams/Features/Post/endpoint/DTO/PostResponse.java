package com.studen.studentgrams.Features.Post.endpoint.DTO;

public record PostResponse(
        Long id,
        byte[] image,
        String description) {
}
