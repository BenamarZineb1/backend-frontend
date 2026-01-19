package com.projet.Edoctorat.Professeur.Models;

import jakarta.persistence.*;

@Entity
@Table(name = "professeur_etablissement")
public class ProfesseurEtablissement {

    @Id
    private String idEtablissement;

    private String nomEtablissement;

    // Getters and Setters
    public String getIdEtablissement() {
        return idEtablissement;
    }

    public void setIdEtablissement(String idEtablissement) {
        this.idEtablissement = idEtablissement;
    }

    public String getNomEtablissement() {
        return nomEtablissement;
    }

    public void setNomEtablissement(String nomEtablissement) {
        this.nomEtablissement = nomEtablissement;
    }
}