package com.projet.Edoctorat.Auth.Repositories;

import com.projet.Edoctorat.Auth.Models.AuthUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthUserRepository extends JpaRepository<AuthUser, Long> {
    Optional<AuthUser> findByEmail(String email);
    Optional<AuthUser> findByUsername(String username);
}
