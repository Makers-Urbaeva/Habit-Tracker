package com.example.repository;

import com.example.dto.response.UserProfileResponse;
import com.example.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);

    @Query("select new com.example.dto.response.UserProfileResponse(u.id,u.fullName,u.icon,u.email) " +
            "from User u where u.id=?1")
    Optional<UserProfileResponse> getUserById(Long id);
}
