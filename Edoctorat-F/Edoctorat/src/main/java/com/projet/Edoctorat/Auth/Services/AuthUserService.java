package com.projet.Edoctorat.Auth.Services;

import com.projet.Edoctorat.Auth.Models.AuthUser;
import com.projet.Edoctorat.Auth.Repositories.AuthUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthUserService {
    
    private final AuthUserRepository authUserRepository;
    
    public Integer getUserIdByUsername(String username) {
        AuthUser user = authUserRepository.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé: " + username));
        return Math.toIntExact(user.getId());
    }
    
    public AuthUser getUserByUsername(String username) {
        return authUserRepository.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé: " + username));
    }
}