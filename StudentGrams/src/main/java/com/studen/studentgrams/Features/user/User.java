package com.studen.studentgrams.Features.user;

import com.studen.studentgrams.Features.Favorite.Favorite;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Builder
@Entity
@Table
@Data
public class User implements UserDetails {
    @Id
    @SequenceGenerator(
            name = "User_Sequence",
            sequenceName = "User_Sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "User_Sequence"
    )
    private Long id;

    private String firstname;
    private String lastname;
    @Column(unique = true)
    private String email;
    private String password;
    @Column(unique = true)
    private String displayname;
    private Boolean admin;

    @Lob
    private byte[] profilePicture;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Favorite> favorites;

    public User() {
    }

    // Constructor with favorites initialization
    public User(Long id, String firstname, String lastname, String email, String password, String displayname, Boolean admin, byte[] profilePicture, List<Favorite> favorites) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.displayname = displayname;
        this.admin = admin;
        this.profilePicture = profilePicture;
        this.favorites = favorites;
    }

    public User(String firstname, String lastname, String email, String password, String displayname, Boolean admin, byte[] profilePicture) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.displayname = displayname;
        this.admin = admin;
        this.profilePicture = profilePicture;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (Boolean.TRUE.equals(admin)) {
            return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"));
        } else {
            return List.of(new SimpleGrantedAuthority("ROLE_USER"));
        }
    }

    @Override
    public String getUsername() {
        return id.toString();
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }

    @Override
    public String getPassword()
    {
        return this.password;
    }
}
