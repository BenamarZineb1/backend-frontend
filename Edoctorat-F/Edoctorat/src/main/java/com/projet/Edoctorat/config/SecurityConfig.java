package com.projet.Edoctorat.config;

import com.projet.Edoctorat.security.JwtAuthenticationFilter;
import com.projet.Edoctorat.Auth.Services.AuthUserDetailsService; // Vérifie l'import
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthUserDetailsService userDetailsService;
    private final CorsConfigurationSource corsConfigurationSource;

    @Bean("securityFilterChain")
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource)) // Enable CORS
                .csrf(csrf -> csrf.disable()) // Désactiver CSRF pour les API REST
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Pas de session serveur (JWT)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/**").permitAll() // Autoriser Login/Register
                        .requestMatchers("/api/public/**").permitAll()
                        .requestMatchers("/actuator/health").permitAll() // Endpoint de santé public
                        .requestMatchers("/actuator/**").permitAll() // Tous les endpoints actuator
                        .requestMatchers("/test", "/api/test", "/hello").permitAll() // Endpoints de test
                        .requestMatchers("/candidat/me").authenticated()
                        .requestMatchers("/candidat/**").authenticated()
                        .requestMatchers("/directeur/ced/**").authenticated()
                        .requestMatchers("/directeur-labo/**").authenticated()
                        .requestMatchers("/directeur/pole/**").authenticated()
                        .requestMatchers("/professeur/**").authenticated()
                        .requestMatchers("/scolarite/**").authenticated()
                        .anyRequest().authenticated() // Tout le reste nécessite un token
                )
                .authenticationProvider(authenticationProvider()) // Utiliser notre provider BDD
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class); // Ajouter notre filtre JWT

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}