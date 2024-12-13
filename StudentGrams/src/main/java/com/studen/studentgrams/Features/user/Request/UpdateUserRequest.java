package com.studen.studentgrams.Features.user.Request;

public record UpdateUserRequest(
        String firstname,
        String lastname,
        String Email,
        String displayname,
        String Password
) {
}
