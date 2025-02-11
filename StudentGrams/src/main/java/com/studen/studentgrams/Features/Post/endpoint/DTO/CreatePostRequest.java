package com.studen.studentgrams.Features.Post.endpoint.DTO;

public record CreatePostRequest(
        String Description,
        byte[] image

) {

}
