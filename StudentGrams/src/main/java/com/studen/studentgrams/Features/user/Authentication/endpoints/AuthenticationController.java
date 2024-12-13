package com.studen.studentgrams.Features.user.Authentication.endpoints;

import com.studen.studentgrams.Features.user.Authentication.AuthenticationResponse;
import com.studen.studentgrams.Features.user.Authentication.dto.AuthenticationRequest;
import com.studen.studentgrams.Features.user.Authentication.dto.RegisterRequest;
import com.studen.studentgrams.Features.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final UserService Service;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest Request) {
        return ResponseEntity.ok(Service.register(Request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest Request) {
        return ResponseEntity.ok(Service.authenticate(Request));
    }
}
