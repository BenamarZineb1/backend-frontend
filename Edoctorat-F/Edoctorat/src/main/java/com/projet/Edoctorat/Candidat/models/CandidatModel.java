package com.projet.Edoctorat.Candidat.models;

import com.projet.Edoctorat.Auth.Models.AuthUser;
import jakarta.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "candidats")
public class CandidatModel {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String cne;
    private String cin;
    private String nomCandidatAr;
    private String prenomCandidatAr;
    
    // Champs pour nom/prénom en français (depuis inscription)
    private String nomCandidatFr;
    private String prenomCandidatFr;
    
    @Column(columnDefinition = "TEXT")
    private String adresse;
    
    @Column(columnDefinition = "TEXT")
    private String adresseAr;
    
    private String sexe;
    private String villeDeNaissance;
    private String villeDeNaissanceAr;
    private String ville;
    private Date dateDeNaissance;
    private String typeDeHandiCape;
    private String academie;
    private String telCandidat;
    private String pathCv;
    private String pathPhoto;
    private Integer etatDossier;
    private String situation_familiale;
    
    @Column(name = "user_id")
    private Integer userId;
    
    // Relation avec l'utilisateur authentifié
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private AuthUser authUser;
    
    private Boolean fonctionaire;
    
    // Getters
    public Long getId() { return id; }
    public String getCne() { return cne; }
    public String getCin() { return cin; }
    public String getNomCandidatAr() { return nomCandidatAr; }
    public String getPrenomCandidatAr() { return prenomCandidatAr; }
    public String getNomCandidatFr() { return nomCandidatFr; }
    public String getPrenomCandidatFr() { return prenomCandidatFr; }
    public String getAdresse() { return adresse; }
    public String getAdresseAr() { return adresseAr; }
    public String getSexe() { return sexe; }
    public String getVilleDeNaissance() { return villeDeNaissance; }
    public String getVilleDeNaissanceAr() { return villeDeNaissanceAr; }
    public String getVille() { return ville; }
    public Date getDateDeNaissance() { return dateDeNaissance; }
    public String getTypeDeHandiCape() { return typeDeHandiCape; }
    public String getAcademie() { return academie; }
    public String getTelCandidat() { return telCandidat; }
    public String getPathCv() { return pathCv; }
    public String getPathPhoto() { return pathPhoto; }
    public Integer getEtatDossier() { return etatDossier; }
    public String getSituation_familiale() { return situation_familiale; }
    public Integer getUserId() { return userId; }
    public AuthUser getAuthUser() { return authUser; }
    public Boolean getFonctionaire() { return fonctionaire; }
    
    // Setters
    public void setId(Long id) { this.id = id; }
    public void setCne(String cne) { this.cne = cne; }
    public void setCin(String cin) { this.cin = cin; }
    public void setNomCandidatAr(String nomCandidatAr) { this.nomCandidatAr = nomCandidatAr; }
    public void setPrenomCandidatAr(String prenomCandidatAr) { this.prenomCandidatAr = prenomCandidatAr; }
    public void setNomCandidatFr(String nomCandidatFr) { this.nomCandidatFr = nomCandidatFr; }
    public void setPrenomCandidatFr(String prenomCandidatFr) { this.prenomCandidatFr = prenomCandidatFr; }
    public void setAdresse(String adresse) { this.adresse = adresse; }
    public void setAdresseAr(String adresseAr) { this.adresseAr = adresseAr; }
    public void setSexe(String sexe) { this.sexe = sexe; }
    public void setVilleDeNaissance(String villeDeNaissance) { this.villeDeNaissance = villeDeNaissance; }
    public void setVilleDeNaissanceAr(String villeDeNaissanceAr) { this.villeDeNaissanceAr = villeDeNaissanceAr; }
    public void setVille(String ville) { this.ville = ville; }
    public void setDateDeNaissance(Date dateDeNaissance) { this.dateDeNaissance = dateDeNaissance; }
    public void setTypeDeHandiCape(String typeDeHandiCape) { this.typeDeHandiCape = typeDeHandiCape; }
    public void setAcademie(String academie) { this.academie = academie; }
    public void setTelCandidat(String telCandidat) { this.telCandidat = telCandidat; }
    public void setPathCv(String pathCv) { this.pathCv = pathCv; }
    public void setPathPhoto(String pathPhoto) { this.pathPhoto = pathPhoto; }
    public void setEtatDossier(Integer etatDossier) { this.etatDossier = etatDossier; }
    public void setSituation_familiale(String situation_familiale) { this.situation_familiale = situation_familiale; }
    public void setUserId(Integer userId) { this.userId = userId; }
    public void setAuthUser(AuthUser authUser) { this.authUser = authUser; }
    public void setFonctionaire(Boolean fonctionaire) { this.fonctionaire = fonctionaire; }
}