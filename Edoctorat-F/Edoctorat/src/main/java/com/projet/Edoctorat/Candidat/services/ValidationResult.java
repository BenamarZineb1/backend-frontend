package com.projet.Edoctorat.Candidat.services;

import com.projet.Edoctorat.Candidat.services.ValidationParcoursService.ParcoursValide;

public class ValidationResult {
    private boolean valide;
    private String message;
    private ParcoursValide typeAdmission;
    private double scoreAdmissibilite;
    
    // Getters
    public boolean isValide() { return valide; }
    public String getMessage() { return message; }
    public ParcoursValide getTypeAdmission() { return typeAdmission; }
    public double getScoreAdmissibilite() { return scoreAdmissibilite; }
    
    // Setters
    public void setValide(boolean valide) { this.valide = valide; }
    public void setMessage(String message) { this.message = message; }
    public void setTypeAdmission(ParcoursValide typeAdmission) { this.typeAdmission = typeAdmission; }
    public void setScoreAdmissibilite(double scoreAdmissibilite) { this.scoreAdmissibilite = scoreAdmissibilite; }
}