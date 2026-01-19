package com.projet.Edoctorat.Candidat.services;

import com.projet.Edoctorat.Candidat.models.ParcoursAcademique;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ValidationParcoursService {
    
    // Critères d'admissibilité pour doctorat
    public enum ParcoursValide {
        DUT_LICENCE_MASTER,    // Standard
        DUT_CYCLE,             // Court
        DUT_LICENCE_CYCLE,     // Mixte
        DOCTORAT_DIRECT,       // Spécial
        CYCLE_SEUL             // Très court
    }
    
    public ValidationResult validerParcours(List<ParcoursAcademique> parcours) {
        ValidationResult result = new ValidationResult();
        
        if (parcours == null || parcours.isEmpty()) {
            result.setValide(false);
            result.setMessage("Aucun parcours académique fourni");
            return result;
        }
        
        // Compter les différents niveaux
        long dutCount = parcours.stream().filter(p -> "DUT".equals(p.getNiveau())).count();
        long licenceCount = parcours.stream().filter(p -> "Licence".equals(p.getNiveau())).count();
        long masterCount = parcours.stream().filter(p -> "Master".equals(p.getNiveau())).count();
        long cycleCount = parcours.stream().filter(p -> "Cycle".equals(p.getNiveau())).count();
        long doctoratCount = parcours.stream().filter(p -> "Doctorat".equals(p.getNiveau())).count();
        
        // Vérifier les combinaisons valides
        if (dutCount >= 1 && licenceCount >= 1 && masterCount >= 1) {
            result.setValide(true);
            result.setTypeAdmission(ParcoursValide.DUT_LICENCE_MASTER);
            result.setMessage("Parcours standard validé (DUT + Licence + Master)");
        }
        else if (dutCount >= 1 && cycleCount >= 1 && masterCount == 0 && licenceCount == 0) {
            result.setValide(true);
            result.setTypeAdmission(ParcoursValide.DUT_CYCLE);
            result.setMessage("Parcours court validé (DUT + Cycle)");
        }
        else if (dutCount >= 1 && licenceCount >= 1 && cycleCount >= 1 && masterCount == 0) {
            result.setValide(true);
            result.setTypeAdmission(ParcoursValide.DUT_LICENCE_CYCLE);
            result.setMessage("Parcours mixte validé (DUT + Licence + Cycle)");
        }
        else if (doctoratCount >= 1) {
            result.setValide(true);
            result.setTypeAdmission(ParcoursValide.DOCTORAT_DIRECT);
            result.setMessage("Doctorat direct validé");
        }
        else if (cycleCount >= 1 && dutCount == 0 && licenceCount == 0 && masterCount == 0) {
            result.setValide(true);
            result.setTypeAdmission(ParcoursValide.CYCLE_SEUL);
            result.setMessage("Parcours très court validé (Cycle seul)");
        }
        else {
            result.setValide(false);
            result.setMessage("Combinaison de parcours non valide pour doctorat");
        }
        
        // Vérifier les moyennes minimales
        if (result.isValide()) {
            boolean moyennesOk = parcours.stream()
                .filter(p -> p.getMoyenne() != null)
                .allMatch(p -> p.getMoyenne() >= 12.0);
            
            if (!moyennesOk) {
                result.setValide(false);
                result.setMessage("Certaines moyennes sont inférieures au seuil requis (12/20)");
            }
        }
        
        return result;
    }
    
    // Calcul du score d'admissibilité
    public double calculerScoreAdmissibilite(List<ParcoursAcademique> parcours) {
        double score = 0.0;
        
        for (ParcoursAcademique p : parcours) {
            if (p.getMoyenne() != null) {
                // Pondération par niveau
                double coefficient = getCoefficientNiveau(p.getNiveau());
                score += p.getMoyenne() * coefficient;
            }
        }
        
        return Math.min(score, 20.0); // Maximum 20
    }
    
    private double getCoefficientNiveau(String niveau) {
        switch (niveau.toUpperCase()) {
            case "DUT": return 0.5;
            case "LICENCE": return 1.0;
            case "MASTER": return 1.5;
            case "CYCLE": return 0.8;
            case "DOCTORAT": return 2.0;
            default: return 0.3;
        }
    }
}