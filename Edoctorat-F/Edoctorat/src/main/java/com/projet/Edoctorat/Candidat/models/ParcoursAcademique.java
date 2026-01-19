package com.projet.Edoctorat.Candidat.models;

public class ParcoursAcademique {
    private String niveau; // DUT, Licence, Master, Cycle, Doctorat
    private String intitule;
    private String etablissement;
    private String pays;
    private String ville;
    private String dateObtention; // YYYY-MM-DD
    private String mention;
    private Double moyenne;
    private String specialite;
    
    // Pour les fichiers joints
    private String diplomePath;
    private String releveNotesPath;
    private String attestationPath;
    
    // Getters
    public String getNiveau() { return niveau; }
    public String getIntitule() { return intitule; }
    public String getEtablissement() { return etablissement; }
    public String getPays() { return pays; }
    public String getVille() { return ville; }
    public String getDateObtention() { return dateObtention; }
    public String getMention() { return mention; }
    public Double getMoyenne() { return moyenne; }
    public String getSpecialite() { return specialite; }
    public String getDiplomePath() { return diplomePath; }
    public String getReleveNotesPath() { return releveNotesPath; }
    public String getAttestationPath() { return attestationPath; }
    
    // Setters
    public void setNiveau(String niveau) { this.niveau = niveau; }
    public void setIntitule(String intitule) { this.intitule = intitule; }
    public void setEtablissement(String etablissement) { this.etablissement = etablissement; }
    public void setPays(String pays) { this.pays = pays; }
    public void setVille(String ville) { this.ville = ville; }
    public void setDateObtention(String dateObtention) { this.dateObtention = dateObtention; }
    public void setMention(String mention) { this.mention = mention; }
    public void setMoyenne(Double moyenne) { this.moyenne = moyenne; }
    public void setSpecialite(String specialite) { this.specialite = specialite; }
    public void setDiplomePath(String diplomePath) { this.diplomePath = diplomePath; }
    public void setReleveNotesPath(String releveNotesPath) { this.releveNotesPath = releveNotesPath; }
    public void setAttestationPath(String attestationPath) { this.attestationPath = attestationPath; }
}