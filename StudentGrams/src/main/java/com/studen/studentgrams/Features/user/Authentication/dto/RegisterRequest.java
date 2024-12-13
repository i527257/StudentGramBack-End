package com.studen.studentgrams.Features.user.Authentication.dto;

import lombok.Builder;


@Builder
public record RegisterRequest(String firstName,
                              String lastName,
                              String email,
                              String password,
                              String displayname,
                              boolean admin,
                              byte[] profilePicturel) {

}
