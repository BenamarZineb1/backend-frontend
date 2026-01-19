package com.projet.Edoctorat.Professeur.Models;

import jakarta.persistence.*;

@Entity
@Table(name = "professeur_laboratoire")
public class ProfesseurLaboratoire {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nomLaboratoire;
    private String description;
    private String pathImage;
    private String initial;

    @ManyToOne
    @JoinColumn(name = "ced_id", nullable = false)
    private ProfesseurCed ced;

    @ManyToOne
    @JoinColumn(name = "directeur_id", nullable = false)
    private Professeur directeur;

    @ManyToOne
    @JoinColumn(name = "etablissement_id", nullable = false)
    private ProfesseurEtablissement etablissement;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomLaboratoire() {
        return nomLaboratoire;
    }

    public void setNomLaboratoire(String nomLaboratoire) {
        this.nomLaboratoire = nomLaboratoire;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPathImage() {
        return pathImage;
    }

    public void setPathImage(String pathImage) {
        this.pathImage = pathImage;
    }

    public String getInitial() {
        return initial;
    }

    public void setInitial(String initial) {
        this.initial = initial;
    }

    public ProfesseurCed getCed() {
        return ced;
    }

    public void setCed(ProfesseurCed ced) {
        this.ced = ced;
    }

    public Professeur getDirecteur() {
        return directeur;
    }

    public void setDirecteur(Professeur directeur) {
        this.directeur = directeur;
    }

    public ProfesseurEtablissement getEtablissement() {
        return etablissement;
    }

    public void setEtablissement(ProfesseurEtablissement etablissement) {
        this.etablissement = etablissement;
    }
}

