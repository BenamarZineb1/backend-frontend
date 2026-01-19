package com.projet.Edoctorat.Auth.Controllers;

import com.projet.Edoctorat.Auth.Models.AuthUser;
import com.projet.Edoctorat.Auth.Models.LoginRequest;
import com.projet.Edoctorat.Auth.Models.RegisterRequestDTO;
import com.projet.Edoctorat.Auth.Services.AuthService;
import com.projet.Edoctorat.security.JwtUtil; // Import JwtUtil
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {

    private final AuthService authService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil; // Injection de JwtUtil

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequestDTO registerData) {
        try {
            System.out.println("📝 Inscription reçue: " + registerData.getEmail());
            System.out.println("📝 Données complètes: CNE=" + registerData.getCne() + 
                             ", Téléphone=" + registerData.getTelephone() + 
                             ", DateNaiss=" + registerData.getDateNaissance());
            
            // Convertir DTO en AuthUser
            AuthUser user = registerData.toAuthUser();
            
            // Appeler le service avec toutes les données
            Object result = authService.registerWithDetails(user, registerData);
            
            System.out.println("✅ Inscription traitée avec succès");
            return ResponseEntity.ok(result);
            
        } catch (Exception e) {
            System.err.println("❌ Erreur inscription: " + e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            // 1. Authentification (Vérifie username/password)
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );

            // 2. Mise à jour du contexte
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // 3. Génération du Token
            String jwt = jwtUtil.generateToken(authentication);

            // 4. Récupérer les infos utilisateur
            AuthUser user = authService.findByUsername(request.getUsername());

            // 5. Réponse JSON avec détails utilisateur
            Map<String, Object> response = new HashMap<>();
            response.put("token", jwt);
            response.put("message", "Connecte avec succès");
            response.put("role", user.getRole());
            
            Map<String, Object> userInfo = new HashMap<>();
            userInfo.put("id", user.getId());
            userInfo.put("username", user.getUsername());
            userInfo.put("email", user.getEmail());
            userInfo.put("firstName", user.getFirstName());
            userInfo.put("lastName", user.getLastName());
            userInfo.put("role", user.getRole());
            
            response.put("user", userInfo);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Identifiants invalides");
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(401).body(errorResponse);
        }
    }
}