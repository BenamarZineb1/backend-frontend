package com.projet.Edoctorat.Commission.Models;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "evaluations")
public class Evaluation {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private Long commissionId;
    private Long candidatId;
    private Long sujetId;
    private Long professeurEvaluateurId;
    
    // Notes
    private Double noteDossier;
    private Double noteEntretien;
    private Double noteFinale;
    
    @Enumerated(EnumType.STRING)
    private DecisionEvaluation decision;
    
    private String commentaire;
    private Boolean validee = false;
    private LocalDateTime dateValidation;
    private Boolean publiee = false;
    private LocalDateTime datePublication;
    private LocalDateTime dateEvaluation;
    
    // Getters et Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public Long getCommissionId() { return commissionId; }
    public void setCommissionId(Long commissionId) { this.commissionId = commissionId; }
    
    public Long getCandidatId() { return candidatId; }
    public void setCandidatId(Long candidatId) { this.candidatId = candidatId; }
    
    public Long getSujetId() { return sujetId; }
    public void setSujetId(Long sujetId) { this.sujetId = sujetId; }
    
    public Long getProfesseurEvaluateurId() { return professeurEvaluateurId; }
    public void setProfesseurEvaluateurId(Long professeurEvaluateurId) { this.professeurEvaluateurId = professeurEvaluateurId; }
    
    public Double getNoteDossier() { return noteDossier; }
    public void setNoteDossier(Double noteDossier) { this.noteDossier = noteDossier; }
    
    public Double getNoteEntretien() { return noteEntretien; }
    public void setNoteEntretien(Double noteEntretien) { this.noteEntretien = noteEntretien; }
    
    public Double getNoteFinale() { return noteFinale; }
    public void setNoteFinale(Double noteFinale) { this.noteFinale = noteFinale; }
    
    public DecisionEvaluation getDecision() { return decision; }
    public void setDecision(DecisionEvaluation decision) { this.decision = decision; }
    
    public String getCommentaire() { return commentaire; }
    public void setCommentaire(String commentaire) { this.commentaire = commentaire; }
    
    public Boolean getValidee() { return validee; }
    public void setValidee(Boolean validee) { this.validee = validee; }
    
    public LocalDateTime getDateValidation() { return dateValidation; }
    public void setDateValidation(LocalDateTime dateValidation) { this.dateValidation = dateValidation; }
    
    public Boolean getPubliee() { return publiee; }
    public void setPubliee(Boolean publiee) { this.publiee = publiee; }
    
    public LocalDateTime getDatePublication() { return datePublication; }
    public void setDatePublication(LocalDateTime datePublication) { this.datePublication = datePublication; }
    
    public LocalDateTime getDateEvaluation() { return dateEvaluation; }
    public void setDateEvaluation(LocalDateTime dateEvaluation) { this.dateEvaluation = dateEvaluation; }
    
    // Calcul automatique de la note finale
    public void calculerNoteFinale() {
        if (noteDossier != null && noteEntretien != null) {
            noteFinale = (noteDossier * 0.4) + (noteEntretien * 0.6);
        }
    }
    
    // Détermination automatique de la décision
    public void determinerDecisionAutomatique() {
        if (noteFinale != null) {
            if (noteFinale >= 16.0) {
                decision = DecisionEvaluation.ADMIS;
            } else if (noteFinale >= 12.0) {
                decision = DecisionEvaluation.LISTE_ATTENTE;
            } else {
                decision = DecisionEvaluation.REFUSE;
            }
        }
    }
    
    public enum DecisionEvaluation {
        ADMIS,
        LISTE_ATTENTE,
        REFUSE
    }
}