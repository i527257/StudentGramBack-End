package com.studen.studentgrams.Features.Post.endpoint.DTO;

public record CreatePost(Long id,
                         byte[] image,
                         String description) {
}
