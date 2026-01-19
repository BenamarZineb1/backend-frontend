package com.projet.Edoctorat.Professeur.Models;

import jakarta.persistence.*;

import java.util.Date;


@Entity
@Table(name = "professeur_formationdoctorale")
public class ProfesseurFormationDoctorale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String pathImage;
    private String initiale;
    private String titre;
    private String axeDeRecherche;
    private Date dateAccreditation;

    @ManyToOne
    @JoinColumn(name = "ced_id", nullable = false)
    private ProfesseurCed ced;

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

    public String getPathImage() {
        return pathImage;
    }

    public void setPathImage(String pathImage) {
        this.pathImage = pathImage;
    }

    public String getInitiale() {
        return initiale;
    }

    public void setInitiale(String initiale) {
        this.initiale = initiale;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getAxeDeRecherche() {
        return axeDeRecherche;
    }

    public void setAxeDeRecherche(String axeDeRecherche) {
        this.axeDeRecherche = axeDeRecherche;
    }

    public Date getDateAccreditation() {
        return dateAccreditation;
    }

    public void setDateAccreditation(Date dateAccreditation) {
        this.dateAccreditation = dateAccreditation;
    }

    public ProfesseurCed getCed() {
        return ced;
    }

    public void setCed(ProfesseurCed ced) {
        this.ced = ced;
    }

    public ProfesseurEtablissement getEtablissement() {
        return etablissement;
    }

    public void setEtablissement(ProfesseurEtablissement etablissement) {
        this.etablissement = etablissement;
    }
}

