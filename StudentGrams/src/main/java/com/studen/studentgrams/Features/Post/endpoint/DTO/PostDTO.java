package com.studen.studentgrams.Features.Post.endpoint.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostDTO {
    private Long id;
    private byte[] image;
    private String description;
    private Long userId;


}
