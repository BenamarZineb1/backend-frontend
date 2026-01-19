package com.projet.Edoctorat.Auth.Models;

import lombok.Data;

@Data
public class RegisterRequestDTO {
    // Données utilisateur
    private String email;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String role;
    
    // Données candidat complètes
    private String cne;
    private String numDoc; // CIN ou Passport
    private String telephone;
    private String dateNaissance;
    private String lieuNaissance;
    private String photoProfil; // Base64 ou URL
    
    // Méthode helper pour créer AuthUser
    public AuthUser toAuthUser() {
        AuthUser user = new AuthUser();
        user.setEmail(this.email);
        user.setUsername(this.username);
        user.setPassword(this.password);
        user.setFirstName(this.firstName);
        user.setLastName(this.lastName);
        user.setRole(this.role);
        return user;
    }
}