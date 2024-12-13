package com.studen.studentgrams.Features.user;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    // Constructor to inject the UserRepository
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        // Convert userId (String) to Long for database lookup
        Long id = Long.parseLong(userId);

        // Fetch the user from the database by their userId
        return userRepository.findById(id)
                .map(user -> new User(
                        user.getDisplayname(),
                        user.getPassword(),
                        user.getAuthorities()))  // Return authorities (roles) here
                .orElseThrow(() -> new UsernameNotFoundException("User not found with id: " + userId));
    }
}
