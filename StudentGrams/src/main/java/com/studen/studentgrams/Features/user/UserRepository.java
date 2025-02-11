package com.studen.studentgrams.Features.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository

public interface UserRepository
        extends JpaRepository<User, Long> {

    @Query("select s from User s where s.displayname = ?1")
    Optional<User> DisplayNameCheck(String displayname);

    @Query("select s from User s where s.email = ?1")
    Optional<User> EmailNameCheck(String email);

    @Query("SELECT u FROM User u WHERE u.displayname LIKE ?1%")
    List<User> findByDisplaynameStartingWith(String search);
}

