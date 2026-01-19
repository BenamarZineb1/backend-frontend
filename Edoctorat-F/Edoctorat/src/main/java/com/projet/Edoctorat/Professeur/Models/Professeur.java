package com.projet.Edoctorat.Professeur.Models;

import jakarta.persistence.*;
import com.projet.Edoctorat.Auth.Models.AuthUser;

@Entity
@Table(name = "professeur_professeur")
public class Professeur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String cin;
    private String telProfesseur;
    private String pathPhoto;
    private String grade;
    private String numSOM;
    private int nombreEncadrer;
    private int nombreProposer;

    @ManyToOne
    @JoinColumn(name = "etablissement_id", nullable = false)
    private ProfesseurEtablissement etablissement;

    @ManyToOne
    @JoinColumn(name = "labo_id")
    private ProfesseurLaboratoire laboratoire;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private AuthUser user;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCin() {
        return cin;
    }

    public void setCin(String cin) {
        this.cin = cin;
    }

    public String getTelProfesseur() {
        return telProfesseur;
    }

    public void setTelProfesseur(String telProfesseur) {
        this.telProfesseur = telProfesseur;
    }

    public String getPathPhoto() {
        return pathPhoto;
    }

    public void setPathPhoto(String pathPhoto) {
        this.pathPhoto = pathPhoto;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getNumSOM() {
        return numSOM;
    }

    public void setNumSOM(String numSOM) {
        this.numSOM = numSOM;
    }

    public int getNombreEncadrer() {
        return nombreEncadrer;
    }

    public void setNombreEncadrer(int nombreEncadrer) {
        this.nombreEncadrer = nombreEncadrer;
    }

    public int getNombreProposer() {
        return nombreProposer;
    }

    public void setNombreProposer(int nombreProposer) {
        this.nombreProposer = nombreProposer;
    }

    public ProfesseurEtablissement getEtablissement() {
        return etablissement;
    }

    public void setEtablissement(ProfesseurEtablissement etablissement) {
        this.etablissement = etablissement;
    }

    public ProfesseurLaboratoire getLaboratoire() {
        return laboratoire;
    }

    public void setLaboratoire(ProfesseurLaboratoire laboratoire) {
        this.laboratoire = laboratoire;
    }

    public AuthUser getUser() {
        return user;
    }

    public void setUser(AuthUser user) {
        this.user = user;
    }
}

