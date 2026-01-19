package com.projet.Edoctorat.Auth.Services;

import com.projet.Edoctorat.Auth.Models.AuthUser;
import com.projet.Edoctorat.Auth.Models.RegisterRequestDTO;
import com.projet.Edoctorat.Auth.Repositories.AuthUserRepository;
import com.projet.Edoctorat.Candidat.models.CandidatModel;
import com.projet.Edoctorat.Candidat.repositories.CandidatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthUserRepository authUserRepository;
    private final CandidatRepository candidatRepository;

    // CORRECTION ICI : On utilise l'interface PasswordEncoder, pas la classe BCrypt
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public AuthUser register(AuthUser user) {
        // On encrypte le mot de passe avant de sauvegarder
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        
        // Sauvegarder l'utilisateur
        AuthUser savedUser = authUserRepository.save(user);
        
        // Créer automatiquement le profil candidat vide
        CandidatModel candidat = new CandidatModel();
        candidat.setUserId(Math.toIntExact(savedUser.getId())); // Convert Long to Integer
        candidat.setEtatDossier(0); // 0 = dossier incomplet
        candidat.setFonctionaire(false);
        
        candidatRepository.save(candidat);
        
        return savedUser;
    }
    
    @Transactional
    public AuthUser registerWithDetails(AuthUser user, RegisterRequestDTO details) {
        // Encrypter le mot de passe
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        
        // Sauvegarder l'utilisateur
        AuthUser savedUser = authUserRepository.save(user);
        
        // Créer le profil candidat avec les détails
        CandidatModel candidat = new CandidatModel();
        candidat.setUserId(Math.toIntExact(savedUser.getId()));
        candidat.setEtatDossier(0); // Dossier incomplet
        candidat.setFonctionaire(false);
        
        // Remplir avec les données fournies
        if (details.getCne() != null) candidat.setCne(details.getCne());
        if (details.getNumDoc() != null) candidat.setCin(details.getNumDoc());
        if (details.getTelephone() != null) candidat.setTelCandidat(details.getTelephone());
        if (details.getDateNaissance() != null) {
            try {
                candidat.setDateDeNaissance(java.sql.Date.valueOf(details.getDateNaissance()));
            } catch (Exception e) {
                // Ignorer date invalide
            }
        }
        if (details.getLieuNaissance() != null) candidat.setVilleDeNaissance(details.getLieuNaissance());
        
        // Ajouter nom et prénom en français
        if (user.getFirstName() != null) candidat.setPrenomCandidatFr(user.getFirstName());
        if (user.getLastName() != null) candidat.setNomCandidatFr(user.getLastName());
        
        candidatRepository.save(candidat);
        
        System.out.println("✅ Profil candidat créé avec détails: " + savedUser.getEmail());
        return savedUser;
    }

    public AuthUser findByUsername(String username) {
        return authUserRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public AuthUser findByEmail(String email) {
        return authUserRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    // Tu peux ajouter d'autres méthodes ici si nécessaire
}