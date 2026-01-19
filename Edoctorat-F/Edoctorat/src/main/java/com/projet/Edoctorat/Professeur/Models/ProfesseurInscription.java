package com.projet.Edoctorat.Professeur.Models;

import jakarta.persistence.*;
import com.projet.Edoctorat.Candidat.models.CandidatModel;

import java.util.Date;

@Entity
@Table(name = "professeur_inscription")
public class ProfesseurInscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date dateDiposeDossier;
    private String remarque;
    private boolean valider;

    @ManyToOne
    @JoinColumn(name = "candidat_id", nullable = false)
    private CandidatModel candidat;

    @ManyToOne
    @JoinColumn(name = "sujet_id", nullable = false)
    private ProfesseurSujet sujet;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDateDiposeDossier() {
        return dateDiposeDossier;
    }

    public void setDateDiposeDossier(Date dateDiposeDossier) {
        this.dateDiposeDossier = dateDiposeDossier;
    }

    public String getRemarque() {
        return remarque;
    }

    public void setRemarque(String remarque) {
        this.remarque = remarque;
    }

    public boolean isValider() {
        return valider;
    }

    public void setValider(boolean valider) {
        this.valider = valider;
    }

    public CandidatModel getCandidat() {
        return candidat;
    }

    public void setCandidat(CandidatModel candidat) {
        this.candidat = candidat;
    }

    public ProfesseurSujet getSujet() {
        return sujet;
    }

    public void setSujet(ProfesseurSujet sujet) {
        this.sujet = sujet;
    }
}

