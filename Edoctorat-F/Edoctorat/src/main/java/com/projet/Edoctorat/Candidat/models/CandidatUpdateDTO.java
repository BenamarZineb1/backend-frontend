package com.projet.Edoctorat.Candidat.models;

public class CandidatUpdateDTO {
    private String cin;
    private String nomCandidatAr;
    private String prenomCandidatAr;
    private String adresse;
    private String sexe;
    private String villeDeNaissance;
    private String villeDeNaissanceAr;
    private String dateDeNaissance;
    private String telCandidat;
    private String situation_familiale;
    private Boolean fonctionaire;
    
    // Getters
    public String getCin() { return cin; }
    public String getNomCandidatAr() { return nomCandidatAr; }
    public String getPrenomCandidatAr() { return prenomCandidatAr; }
    public String getAdresse() { return adresse; }
    public String getSexe() { return sexe; }
    public String getVilleDeNaissance() { return villeDeNaissance; }
    public String getVilleDeNaissanceAr() { return villeDeNaissanceAr; }
    public String getDateDeNaissance() { return dateDeNaissance; }
    public String getTelCandidat() { return telCandidat; }
    public String getSituation_familiale() { return situation_familiale; }
    public Boolean getFonctionaire() { return fonctionaire; }
    
    // Setters
    public void setCin(String cin) { this.cin = cin; }
    public void setNomCandidatAr(String nomCandidatAr) { this.nomCandidatAr = nomCandidatAr; }
    public void setPrenomCandidatAr(String prenomCandidatAr) { this.prenomCandidatAr = prenomCandidatAr; }
    public void setAdresse(String adresse) { this.adresse = adresse; }
    public void setSexe(String sexe) { this.sexe = sexe; }
    public void setVilleDeNaissance(String villeDeNaissance) { this.villeDeNaissance = villeDeNaissance; }
    public void setVilleDeNaissanceAr(String villeDeNaissanceAr) { this.villeDeNaissanceAr = villeDeNaissanceAr; }
    public void setDateDeNaissance(String dateDeNaissance) { this.dateDeNaissance = dateDeNaissance; }
    public void setTelCandidat(String telCandidat) { this.telCandidat = telCandidat; }
    public void setSituation_familiale(String situation_familiale) { this.situation_familiale = situation_familiale; }
    public void setFonctionaire(Boolean fonctionaire) { this.fonctionaire = fonctionaire; }
}