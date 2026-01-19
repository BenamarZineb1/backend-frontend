package com.projet.Edoctorat.Professeur.Services;

import com.projet.Edoctorat.Professeur.Models.ProfesseurExaminer;
import com.projet.Edoctorat.Professeur.Models.PvProfesseur;
import com.projet.Edoctorat.Professeur.Repositories.ProfesseurExaminerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PvProfesseurService {

    private final ProfesseurExaminerRepository repository;

    // ✅ Générer PV professeur
    public PvProfesseur genererPvProfesseur(Long commissionId) {

        List<ProfesseurExaminer> evaluations = repository.findByCommissionId(commissionId);

        List<ProfesseurExaminer> listePrincipale = evaluations.stream()
                .filter(e -> "ACCEPTE".equalsIgnoreCase(e.getDecision()))
                .toList();

        List<ProfesseurExaminer> listeAttente = evaluations.stream()
                .filter(e -> "ATTENTE".equalsIgnoreCase(e.getDecision()))
                .toList();

        return new PvProfesseur(listePrincipale, listeAttente);
    }
}
