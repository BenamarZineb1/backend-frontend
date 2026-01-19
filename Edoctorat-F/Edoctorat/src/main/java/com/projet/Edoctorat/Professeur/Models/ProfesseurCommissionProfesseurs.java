package com.projet.Edoctorat.Professeur.Models;

import jakarta.persistence.*;

@Entity
@Table(name = "professeur_commission_professeurs")
public class ProfesseurCommissionProfesseurs {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "commission_id", nullable = false)
    private ProfesseurCommission commission;

    @ManyToOne
    @JoinColumn(name = "professeur_id", nullable = false)
    private Professeur professeur;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ProfesseurCommission getCommission() {
        return commission;
    }

    public void setCommission(ProfesseurCommission commission) {
        this.commission = commission;
    }

    public Professeur getProfesseur() {
        return professeur;
    }

    public void setProfesseur(Professeur professeur) {
        this.professeur = professeur;
    }
}
