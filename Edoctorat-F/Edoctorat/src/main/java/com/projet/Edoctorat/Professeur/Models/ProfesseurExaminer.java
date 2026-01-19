package com.projet.Edoctorat.Professeur.Models;

import jakarta.persistence.*;
import com.projet.Edoctorat.Candidat.models.CandidatModel;

@Entity
@Table(name = "professeur_examiner")
public class ProfesseurExaminer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String decision;
    private float noteDossier;
    private int noteEntretien;
    private boolean publier;
    private boolean valider;

    @ManyToOne
    @JoinColumn(name = "commission_id", nullable = false)
    private ProfesseurCommission commission;

    @ManyToOne
    @JoinColumn(name = "sujet_id", nullable = false)
    private ProfesseurSujet sujet;

    @ManyToOne
    @JoinColumn(name = "candidat_id", nullable = false)
    private CandidatModel candidat;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDecision() {
        return decision;
    }

    public void setDecision(String decision) {
        this.decision = decision;
    }

    public float getNoteDossier() {
        return noteDossier;
    }

    public void setNoteDossier(float noteDossier) {
        this.noteDossier = noteDossier;
    }

    public int getNoteEntretien() {
        return noteEntretien;
    }

    public void setNoteEntretien(int noteEntretien) {
        this.noteEntretien = noteEntretien;
    }

    public boolean isPublier() {
        return publier;
    }

    public void setPublier(boolean publier) {
        this.publier = publier;
    }

    public boolean isValider() {
        return valider;
    }

    public void setValider(boolean valider) {
        this.valider = valider;
    }

    public ProfesseurCommission getCommission() {
        return commission;
    }

    public void setCommission(ProfesseurCommission commission) {
        this.commission = commission;
    }

    public ProfesseurSujet getSujet() {
        return sujet;
    }

    public void setSujet(ProfesseurSujet sujet) {
        this.sujet = sujet;
    }

    public CandidatModel getCandidat() {
        return candidat;
    }

    public void setCandidat(CandidatModel candidat) {
        this.candidat = candidat;
    }
}

