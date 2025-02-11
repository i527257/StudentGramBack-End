package com.studen.studentgrams.Features.user;

import com.studen.studentgrams.Config.JWTService;
import com.studen.studentgrams.Features.user.Authentication.AuthenticationResponse;
import com.studen.studentgrams.Features.user.Authentication.dto.AuthenticationRequest;
import com.studen.studentgrams.Features.user.Authentication.dto.RegisterRequest;
import com.studen.studentgrams.Features.user.Request.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTService jwtService;
    private final AuthenticationManager authenticationManager;

    public void addNewUser(User user) {
        Optional<User> byDisplayname = userRepository.DisplayNameCheck(user.getDisplayname());
        if (byDisplayname.isPresent()) {
            throw new IllegalStateException("Displayname already exists");
        }

        Optional<User> byEmail = userRepository.EmailNameCheck(user.getEmail());
        if (byEmail.isPresent()) {
            throw new IllegalStateException("Email already exists");
        }
        userRepository.save(user);
    }

    public AuthenticationResponse register(RegisterRequest request) {
        byte[] profilePictureData = Base64.getDecoder().decode(request.profilePicture().split(",")[1]);

        var user = User.builder()
                .displayname(request.displayname())
                .password(passwordEncoder.encode(request.password()))
                .email(request.email())
                .firstname(request.firstName())
                .lastname(request.lastName())
                .profilePicture(profilePictureData)
                .admin(false)
                .build();

        userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }


    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        var user = userRepository.DisplayNameCheck(request.getDisplayname()).orElseThrow();
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                user.getId(),
                request.getPassword()));
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder().
                token(jwtToken).
                build();
    }

    public User getUserById(long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("User not found"));
    }

    public void UpdateUser(long id, User Newuser) {
        Optional<User> getUserByID = userRepository.findById(id);

        if (getUserByID.isPresent()) {
            User UserToUpdate = getUserByID.get();
            UserToUpdate.setFirstname(Newuser.getFirstname());
            UserToUpdate.setLastname(Newuser.getLastname());
            UserToUpdate.setProfilePicture(Newuser.getProfilePicture());
            UserToUpdate.setAdmin(Newuser.getAdmin());
            UserToUpdate.setEmail(Newuser.getEmail());
            UserToUpdate.setPassword(passwordEncoder.encode(Newuser.getPassword()));
            UserToUpdate.setDisplayname(Newuser.getDisplayname());
            userRepository.save(UserToUpdate);
        } else {
            throw new IllegalStateException("User not found");
        }
    }

    public List<UserResponse> GetUsers(String search) {
        List<User> users;
        if (search == null || search.isEmpty()) {
            users = userRepository.findAll();
        } else {
            users = userRepository.findByDisplaynameStartingWith(search);
        }

        return users.stream().map(user -> {
            String base64Image = null;
            if (user.getProfilePicture() != null) {
                base64Image = Base64.getEncoder().encodeToString(user.getProfilePicture());
            }
            return new UserResponse(
                    user.getId(),
                    user.getDisplayname(),
                    base64Image,
                    user.getFirstname(),
                    user.getLastname(),
                    user.getEmail()
            );
        }).collect(Collectors.toList());
    }

    public UserResponse getUserProfileById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("User not found"));
        String base64Image = null;
        if (user.getProfilePicture() != null) {
            base64Image = Base64.getEncoder().encodeToString(user.getProfilePicture());
        }

        return new UserResponse(user.getId(), user.getDisplayname(), base64Image, user.getFirstname(), user.getLastname(), user.getEmail());
    }


}
