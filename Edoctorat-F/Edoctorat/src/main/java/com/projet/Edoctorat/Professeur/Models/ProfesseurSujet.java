package com.projet.Edoctorat.Professeur.Models;

import jakarta.persistence.*;

@Entity
@Table(name = "professeur_sujet")
public class ProfesseurSujet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titre;
    private String description;
    private boolean publier;

    @ManyToOne
    @JoinColumn(name = "professeur_id", nullable = false)
    private Professeur professeur;

    @ManyToOne
    @JoinColumn(name = "coDirecteur_id")
    private Professeur coDirecteur;

    @ManyToOne
    @JoinColumn(name = "formationDoctorale_id", nullable = false)
    private ProfesseurFormationDoctorale formationDoctorale;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isPublier() {
        return publier;
    }

    public void setPublier(boolean publier) {
        this.publier = publier;
    }

    public Professeur getProfesseur() {
        return professeur;
    }

    public void setProfesseur(Professeur professeur) {
        this.professeur = professeur;
    }

    public Professeur getCoDirecteur() {
        return coDirecteur;
    }

    public void setCoDirecteur(Professeur coDirecteur) {
        this.coDirecteur = coDirecteur;
    }

    public ProfesseurFormationDoctorale getFormationDoctorale() {
        return formationDoctorale;
    }

    public void setFormationDoctorale(ProfesseurFormationDoctorale formationDoctorale) {
        this.formationDoctorale = formationDoctorale;
    }
}

