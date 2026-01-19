package com.projet.Edoctorat.Commission.Controllers;

import com.projet.Edoctorat.Commission.Models.Commission;
import com.projet.Edoctorat.Commission.Models.Evaluation;
import com.projet.Edoctorat.Commission.Services.CommissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/commissions")
@RequiredArgsConstructor
@CrossOrigin("*")
public class CommissionController {
    
    private final CommissionService commissionService;
    
    // Créer une commission
    @PostMapping
    public Commission creerCommission(@RequestBody Commission commission) {
        return commissionService.creerCommission(commission);
    }
    
    // Récupérer commissions par laboratoire
    @GetMapping("/laboratoire/{laboratoireId}")
    public List<Commission> getCommissionsParLaboratoire(@PathVariable Long laboratoireId) {
        return commissionService.getCommissionsParLaboratoire(laboratoireId);
    }
    
    // Récupérer commissions par statut
    @GetMapping("/statut/{statut}")
    public List<Commission> getCommissionsParStatut(@PathVariable Commission.StatutCommission statut) {
        return commissionService.getCommissionsParStatut(statut);
    }
    
    // Récupérer commission par ID
    @GetMapping("/{id}")
    public Commission getCommissionById(@PathVariable Long id) {
        return commissionService.getCommissionById(id);
    }
    
    // Ajouter membre à commission
    @PostMapping("/{commissionId}/membres/{professeurId}")
    public void ajouterMembreCommission(
            @PathVariable Long commissionId,
            @PathVariable Long professeurId) {
        commissionService.ajouterMembreCommission(commissionId, professeurId);
    }
    
    // CRUD Évaluations
    @PostMapping("/evaluations")
    public Evaluation creerEvaluation(@RequestBody Evaluation evaluation) {
        return commissionService.creerEvaluation(evaluation);
    }
    
    @GetMapping("/evaluations/commission/{commissionId}")
    public List<Evaluation> getEvaluationsParCommission(@PathVariable Long commissionId) {
        return commissionService.getEvaluationsParCommission(commissionId);
    }
    
    @GetMapping("/evaluations/candidat/{candidatId}")
    public List<Evaluation> getEvaluationsParCandidat(@PathVariable Long candidatId) {
        return commissionService.getEvaluationsParCandidat(candidatId);
    }
    
    @GetMapping("/evaluations/{id}")
    public Evaluation getEvaluationById(@PathVariable Long id) {
        return commissionService.getEvaluationById(id);
    }
    
    // Valider une évaluation
    @PutMapping("/evaluations/{id}/valider")
    public void validerEvaluation(@PathVariable Long id) {
        commissionService.validerEvaluation(id);
    }
    
    // Publier résultats commission
    @PutMapping("/{id}/publier-resultats")
    public void publierResultatsCommission(@PathVariable Long id) {
        commissionService.publierResultatsCommission(id);
    }
    
    // Statistiques
    @GetMapping("/laboratoire/{laboratoireId}/stats")
    public Map<String, Object> getStatistiquesCommissions(@PathVariable Long laboratoireId) {
        return commissionService.getStatistiquesCommissions(laboratoireId);
    }
    
    // Résultats par décision
    @GetMapping("/{commissionId}/resultats")
    public Map<String, Long> getResultatsParDecision(@PathVariable Long commissionId) {
        return commissionService.getResultatsParDecision(commissionId);
    }
    
    // Tests (pour démo)
    @PostMapping("/test/commission")
    public Commission creerCommissionTest() {
        Commission commission = new Commission();
        commission.setTitre("Commission Test - Mathématiques");
        commission.setDescription("Commission de test pour démonstration");
        commission.setDateCommission(java.time.LocalDateTime.now().plusDays(7));
        commission.setLieu("Salle de conférence");
        commission.setHeure("14:00");
        commission.setLaboratoireId(1L);
        commission.setDirecteurLaboId(1L);
        return commissionService.creerCommission(commission);
    }
    
    @PostMapping("/test/evaluation")
    public Evaluation creerEvaluationTest() {
        Evaluation evaluation = new Evaluation();
        evaluation.setCommissionId(1L);
        evaluation.setCandidatId(1L);
        evaluation.setSujetId(1L);
        evaluation.setProfesseurEvaluateurId(1L);
        evaluation.setNoteDossier(16.0);
        evaluation.setNoteEntretien(18.0);
        evaluation.calculerNoteFinale();
        evaluation.determinerDecisionAutomatique();
        evaluation.setCommentaire("Évaluation de test");
        return commissionService.creerEvaluation(evaluation);
    }
}