package com.studen.studentgrams.Features.Post.endpoint.DTO;

public record CreatePostRequest(
        Long userId,
        String Description,
        byte[] image

) {

}
