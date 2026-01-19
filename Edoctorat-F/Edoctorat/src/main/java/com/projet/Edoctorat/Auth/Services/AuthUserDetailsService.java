package com.projet.Edoctorat.Auth.Services;

import com.projet.Edoctorat.Auth.Models.AuthUser;
import com.projet.Edoctorat.Auth.Repositories.AuthUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthUserDetailsService implements UserDetailsService {

    private final AuthUserRepository authUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Try by email first, then by username
        AuthUser user = authUserRepository.findByEmail(username)
                .orElseGet(() -> authUserRepository.findByUsername(username)
                        .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé: " + username)));

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .authorities("ROLE_" + user.getRole())
                .build();
    }
}
