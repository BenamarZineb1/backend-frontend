package com.projet.Edoctorat.Professeur.Services;

import com.projet.Edoctorat.DirecteurPole.Services.DirecteurPoleService;
import com.projet.Edoctorat.Professeur.Models.Professeur;
import com.projet.Edoctorat.Professeur.Models.ProfesseurSujet;
import com.projet.Edoctorat.Professeur.Repositories.ProfesseurRepository;
import com.projet.Edoctorat.Professeur.Repositories.ProfesseurSujetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProfesseurSujetService {

    private final ProfesseurSujetRepository repository;
    private final ProfesseurRepository professeurRepository;
    private final DirecteurPoleService directeurPoleService;

    // Proposer un sujet
    public ProfesseurSujet proposerSujet(Long professeurId, String titre, String description) {

        Professeur professeur = professeurRepository.findById(professeurId)
                .orElseThrow(() -> new RuntimeException("Professeur introuvable"));

        // Bloquer les PA
        if ("PA".equalsIgnoreCase(professeur.getGrade())) {
            throw new RuntimeException("Les PA ne peuvent pas proposer des sujets");
        }

        // Vérifier si dépôt ouvert
        if (!directeurPoleService.isDepotSujetOuvert()) {
            throw new RuntimeException("Le délai de dépôt des sujets est terminé");
        }

        // Limite 12 sujets max (professeur + codirecteur)
        long nbSujets = repository.countByProfesseurIdOrCoDirecteurId(professeurId, professeurId);
        if (nbSujets >= 12) {
            throw new RuntimeException("Limite atteinte : maximum 12 sujets");
        }

        // Création du sujet
        ProfesseurSujet sujet = new ProfesseurSujet();
        sujet.setTitre(titre);
        sujet.setDescription(description);
        sujet.setPublier(false);
        sujet.setProfesseur(professeur);

        return repository.save(sujet);
    }

    // Lister les sujets d’un professeur
    public List<ProfesseurSujet> getSujetsParProfesseur(Long professeurId) {
        return repository.findByProfesseurId(professeurId);
    }
}
