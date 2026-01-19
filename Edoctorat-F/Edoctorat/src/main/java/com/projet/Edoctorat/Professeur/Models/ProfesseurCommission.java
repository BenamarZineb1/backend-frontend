package com.projet.Edoctorat.Professeur.Models;

import jakarta.persistence.*;
import java.util.Date;
import java.time.LocalTime;

@Entity
@Table(name = "professeur_commission")
public class ProfesseurCommission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date dateCommission;
    private String lieu;
    private LocalTime heure;

    @ManyToOne
    @JoinColumn(name = "labo_id", nullable = false)
    private ProfesseurLaboratoire laboratoire;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDateCommission() {
        return dateCommission;
    }

    public void setDateCommission(Date dateCommission) {
        this.dateCommission = dateCommission;
    }

    public String getLieu() {
        return lieu;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    public LocalTime getHeure() {
        return heure;
    }

    public void setHeure(LocalTime heure) {
        this.heure = heure;
    }

    public ProfesseurLaboratoire getLaboratoire() {
        return laboratoire;
    }

    public void setLaboratoire(ProfesseurLaboratoire laboratoire) {
        this.laboratoire = laboratoire;
    }
}

