package com.studen.studentgrams.Features.user.endpoints;

import com.studen.studentgrams.Features.user.Request.UpdateProfilePicture;
import com.studen.studentgrams.Features.user.Request.UpdateUserRequest;
import com.studen.studentgrams.Features.user.User;
import com.studen.studentgrams.Features.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/User")
public class UserController {
    private final UserService userService;
    private final PasswordEncoder passwordencoder;

    @Autowired
    public UserController(UserService userService, PasswordEncoder passwordencoder) {
        this.userService = userService;
        this.passwordencoder = passwordencoder;
    }

    @GetMapping
    public List<User> GetAllUsers() {
        return userService.GetAllUsers();
    }

    @PutMapping("/ProfilePicture")
    ResponseEntity<?> UpdateProfilePicture(@PathVariable long id, @RequestBody UpdateProfilePicture request) {
        User user = userService.getUserById(id);
        user.setProfilePicture(request.profilePicture());
        userService.UpdateUser(id, user);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/UpdateUser")
    ResponseEntity<?> UpdateUserProfile(@AuthenticationPrincipal UserDetails userDetails, @RequestBody UpdateUserRequest request) {
        User user = (User) userDetails;
        user.setEmail(request.Email());
        user.setDisplayname(request.displayname());
        user.setFirstname(request.firstname());
        user.setLastname(request.lastname());
        user.setPassword(passwordencoder.encode(request.Password()));
        userService.UpdateUser(user.getId(), user);
        return ResponseEntity.ok().build();
    }
}
