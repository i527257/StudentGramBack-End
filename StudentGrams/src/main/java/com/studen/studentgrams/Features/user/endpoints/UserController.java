package com.studen.studentgrams.Features.user.endpoints;

import com.studen.studentgrams.Features.user.Request.UpdateUserRequest;
import com.studen.studentgrams.Features.user.Request.UpdateUserRoleRequest;
import com.studen.studentgrams.Features.user.Request.UserResponse;
import com.studen.studentgrams.Features.user.User;
import com.studen.studentgrams.Features.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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

    @GetMapping("/users")
    public List<UserResponse> getUsers(@RequestParam(value = "search", required = false) String search) {
        return userService.GetUsers(search);
    }



    @PutMapping("/ProfilePicture")
    public ResponseEntity<?> updateProfilePicture(@AuthenticationPrincipal UserDetails userDetails,
                                                  @RequestParam("profilePicture") MultipartFile file) throws IOException, IOException {
        User user = (User) userDetails;
        byte[] profilePictureData = file.getBytes();
        user.setProfilePicture(profilePictureData);
        userService.UpdateUser(user.getId(), user);
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

    @GetMapping("/profile")
    public UserResponse getUserProfile(@AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.getUserById(Long.parseLong(userDetails.getUsername()));
        return userService.getUserProfileById(user.getId());
    }

    @GetMapping("/getuser/{id}")
    public UserResponse getUserPage(@PathVariable long id) {
        User user = userService.getUserById(id);
        return userService.getUserProfileById(user.getId());
    }

    @PutMapping("GrantUserAdmin/{id}")
    public ResponseEntity<?> UpdateUserRole(@PathVariable long id,
                                            @RequestBody UpdateUserRoleRequest request){
        User user = userService.getUserById(id);
        user.setAdmin(request.admin());
        userService.UpdateUser(user.getId(), user);
        return ResponseEntity.ok().build();
    }

}
