package com.projet.Edoctorat.Professeur.Models;
import jakarta.persistence.*;

@Entity
@Table(name = "professeur_ced")
public class ProfesseurCed {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String pathImage;
    private String initiale;
    private String titre;

    private Long directeur_id;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Long getDirecteur_id() {
        return directeur_id;
    }

    public void setDirecteur_id(Long directeur_id) {
        this.directeur_id = directeur_id;
    }
}