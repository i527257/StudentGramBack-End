package com.studen.studentgrams.Features.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository

public interface UserRepository
        extends JpaRepository<User, Long> {

    @Query("select s from User s where s.displayname = ?1")
    Optional<User> findByUsername(String username);


}

