package com.projet.Edoctorat.Commission.Services;

import com.projet.Edoctorat.Commission.Models.Commission;
import com.projet.Edoctorat.Commission.Models.Evaluation;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CommissionService {
    
    private List<Commission> commissions = new ArrayList<>();
    private List<Evaluation> evaluations = new ArrayList<>();
    private long commissionIdCounter = 1;
    private long evaluationIdCounter = 1;
    
    public CommissionService() {
        initialiserCommissionsTest();
    }
    
    private void initialiserCommissionsTest() {
        // Commission de test
        Commission c1 = new Commission();
        c1.setId(commissionIdCounter++);
        c1.setTitre("Commission d'examen - Informatique");
        c1.setDescription("Évaluation des candidatures en informatique");
        c1.setDateCommission(LocalDateTime.now().plusDays(5));
        c1.setLieu("Amphi A - Campus principal");
        c1.setHeure("10:00");
        c1.setMembresIds("1,2,3,4,5");
        c1.setSujetsIds("1,2");
        c1.setLaboratoireId(1L);
        c1.setDirecteurLaboId(1L);
        commissions.add(c1);
        
        // Évaluations de test
        Evaluation e1 = new Evaluation();
        e1.setId(evaluationIdCounter++);
        e1.setCommissionId(1L);
        e1.setCandidatId(1L);
        e1.setSujetId(1L);
        e1.setProfesseurEvaluateurId(1L);
        e1.setNoteDossier(15.5);
        e1.setNoteEntretien(17.0);
        e1.calculerNoteFinale();
        e1.determinerDecisionAutomatique();
        e1.setCommentaire("Très bon dossier, excellent entretien");
        e1.setValidee(true);
        e1.setPubliee(false);
        evaluations.add(e1);
    }
    
    // CRUD Commissions
    public Commission creerCommission(Commission commission) {
        commission.setId(commissionIdCounter++);
        commission.setStatut(Commission.StatutCommission.PLANIFIEE);
        commissions.add(commission);
        System.out.println("🏛️ Commission créée: " + commission.getTitre());
        return commission;
    }
    
    public List<Commission> getCommissionsParLaboratoire(Long laboratoireId) {
        return commissions.stream()
            .filter(c -> c.getLaboratoireId().equals(laboratoireId))
            .sorted(Comparator.comparing(Commission::getDateCommission))
            .collect(Collectors.toList());
    }
    
    public List<Commission> getCommissionsParStatut(Commission.StatutCommission statut) {
        return commissions.stream()
            .filter(c -> c.getStatut() == statut)
            .collect(Collectors.toList());
    }
    
    public Commission getCommissionById(Long id) {
        return commissions.stream()
            .filter(c -> c.getId().equals(id))
            .findFirst()
            .orElse(null);
    }
    
    public void ajouterMembreCommission(Long commissionId, Long professeurId) {
        commissions.stream()
            .filter(c -> c.getId().equals(commissionId))
            .findFirst()
            .ifPresent(c -> {
                List<Long> membres = new ArrayList<>(c.getMembresList());
                if (!membres.contains(professeurId) && c.peutAjouterMembre()) {
                    membres.add(professeurId);
                    c.setMembresIds(String.join(",", membres.stream().map(String::valueOf).toList()));
                    System.out.println("👥 Membre ajouté à commission " + commissionId);
                }
            });
    }
    
    // Gestion des évaluations
    public Evaluation creerEvaluation(Evaluation evaluation) {
        evaluation.setId(evaluationIdCounter++);
        evaluation.setDateEvaluation(LocalDateTime.now());
        evaluations.add(evaluation);
        System.out.println("📝 Évaluation créée pour candidat " + evaluation.getCandidatId());
        return evaluation;
    }
    
    public List<Evaluation> getEvaluationsParCommission(Long commissionId) {
        return evaluations.stream()
            .filter(e -> e.getCommissionId().equals(commissionId))
            .collect(Collectors.toList());
    }
    
    public List<Evaluation> getEvaluationsParCandidat(Long candidatId) {
        return evaluations.stream()
            .filter(e -> e.getCandidatId().equals(candidatId))
            .collect(Collectors.toList());
    }
    
    public Evaluation getEvaluationById(Long id) {
        return evaluations.stream()
            .filter(e -> e.getId().equals(id))
            .findFirst()
            .orElse(null);
    }
    
    public void validerEvaluation(Long evaluationId) {
        evaluations.stream()
            .filter(e -> e.getId().equals(evaluationId))
            .findFirst()
            .ifPresent(e -> {
                e.setValidee(true);
                e.setDateValidation(LocalDateTime.now());
                System.out.println("✅ Évaluation " + evaluationId + " validée");
            });
    }
    
    // Publication résultats
    public void publierResultatsCommission(Long commissionId) {
        commissions.stream()
            .filter(c -> c.getId().equals(commissionId))
            .findFirst()
            .ifPresent(c -> {
                c.setStatut(Commission.StatutCommission.TERMINEE);
                c.setResultatsPublies(true);
                c.setDatePublicationResultats(LocalDateTime.now());
                
                // Publier toutes les évaluations de la commission
                evaluations.stream()
                    .filter(e -> e.getCommissionId().equals(commissionId))
                    .forEach(e -> {
                        e.setPubliee(true);
                        e.setDatePublication(LocalDateTime.now());
                    });
                
                System.out.println("📢 Résultats commission " + commissionId + " publiés");
            });
    }
    
    // Statistiques
    public Map<String, Object> getStatistiquesCommissions(Long laboratoireId) {
        Map<String, Object> stats = new HashMap<>();
        
        List<Commission> commissionsLabo = commissions.stream()
            .filter(c -> c.getLaboratoireId().equals(laboratoireId))
            .collect(Collectors.toList());
        
        stats.put("totalCommissions", commissionsLabo.size());
        stats.put("commissionsPlanifiees", 
                 commissionsLabo.stream().filter(c -> c.getStatut() == Commission.StatutCommission.PLANIFIEE).count());
        stats.put("commissionsTerminees", 
                 commissionsLabo.stream().filter(c -> c.getStatut() == Commission.StatutCommission.TERMINEE).count());
        stats.put("evaluationsTotales", 
                 evaluations.stream().filter(e -> commissionsLabo.stream().anyMatch(c -> c.getId().equals(e.getCommissionId()))).count());
        
        return stats;
    }
    
    // Résultats par décision
    public Map<String, Long> getResultatsParDecision(Long commissionId) {
        return evaluations.stream()
            .filter(e -> e.getCommissionId().equals(commissionId))
            .filter(Evaluation::getValidee)
            .collect(Collectors.groupingBy(
                e -> e.getDecision().name(),
                Collectors.counting()
            ));
    }
}