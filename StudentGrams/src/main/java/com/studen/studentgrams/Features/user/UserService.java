package com.studen.studentgrams.Features.user;

import com.studen.studentgrams.Config.JWTService;
import com.studen.studentgrams.Features.user.Authentication.AuthenticationResponse;
import com.studen.studentgrams.Features.user.Authentication.dto.AuthenticationRequest;
import com.studen.studentgrams.Features.user.Authentication.dto.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTService jwtService;
    private final AuthenticationManager authenticationManager;


    public List<User> GetAllUsers() {
        return userRepository.findAll();
    }

    public void addNewUser(User user) {
        Optional<User> byUsername = userRepository.findByUsername(user.getDisplayname());
        if (byUsername.isPresent()) {
            throw new IllegalStateException("Username already exists");
        }
        userRepository.save(user);
    }

    public AuthenticationResponse register(RegisterRequest request) {
        var user = User.builder().
                displayname(request.displayname()).
                password(passwordEncoder.encode(request.password())).
                email(request.email()).
                firstname(request.firstName()).
                lastname(request.lastName()).
                profilePicture(request.profilePicturel()).
                admin(false).
                build();
        userRepository.save(user);
        var jwtToken = jwtService.GenerateToken(user);
        return AuthenticationResponse.builder().
                token(jwtToken).
                build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.
                getDisplayname(),
                request.getPassword()));
        var user = userRepository.findByUsername(request.getDisplayname()).orElseThrow();
        var jwtToken = jwtService.GenerateToken(user);
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
}
