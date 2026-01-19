package com.projet.Edoctorat.security;

import com.projet.Edoctorat.Auth.Services.AuthUserDetailsService; // Assure-toi que ce chemin est bon
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthUserDetailsService userDetailsService; // Ton service qui charge l'utilisateur depuis la BDD

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        // 1. Récupérer le token depuis la requête
        String token = getJwtFromRequest(request);

        // 2. Valider le token
        if (StringUtils.hasText(token) && jwtUtil.validateToken(token)) {
            // 3. Récupérer le nom d'utilisateur
            String username = jwtUtil.getUsernameFromJWT(token);

            // 4. Charger l'utilisateur complet depuis la BDD
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            // 5. Créer l'objet d'authentification
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities()
            );

            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            // 6. Enregistrer l'utilisateur dans le contexte de sécurité
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }

        filterChain.doFilter(request, response);
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}