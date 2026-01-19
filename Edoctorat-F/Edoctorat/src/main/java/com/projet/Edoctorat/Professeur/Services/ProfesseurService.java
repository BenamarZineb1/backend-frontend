package com.projet.Edoctorat.Professeur.Services;

import com.projet.Edoctorat.Auth.Models.AuthUser;
import com.projet.Edoctorat.Auth.Repositories.AuthUserRepository;
import com.projet.Edoctorat.Professeur.Models.Professeur;
import com.projet.Edoctorat.Professeur.Models.ProfesseurSujet;
import com.projet.Edoctorat.Professeur.Repositories.ProfesseurRepository;
import com.projet.Edoctorat.Professeur.Repositories.ProfesseurSujetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProfesseurService {

    private final AuthUserRepository authUserRepository;
    private final ProfesseurRepository professeurRepository;
    private final ProfesseurSujetRepository sujetRepository;

    // Récupérer le professeur par email
    public AuthUser getProfesseurByEmail(String email) {
        return authUserRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Professeur non trouvé"));
    }

    // Récupérer les sujets d’un professeur
    public List<ProfesseurSujet> getSujets(String email) {
        AuthUser authUser = getProfesseurByEmail(email);
        return sujetRepository.findByProfesseurId(authUser.getId());
    }

    // Ajouter un nouveau sujet pour un professeur
    public ProfesseurSujet ajouterSujet(String email, ProfesseurSujet sujet) {
        AuthUser authUser = getProfesseurByEmail(email);
        Professeur professeur = professeurRepository.findById(authUser.getId())
                .orElseThrow(() -> new RuntimeException("Professeur introuvable"));
        sujet.setProfesseur(professeur); // ✅ corrigé
        return sujetRepository.save(sujet);
    }
}
