package com.projet.Edoctorat.Commission.Models;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Arrays;
import java.util.stream.Collectors;

@Entity
@Table(name = "commissions")
public class Commission {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String titre;
    private String description;
    private LocalDateTime dateCommission;
    private String lieu;
    private String heure;
    private String membresIds;
    private String sujetsIds;
    private Long laboratoireId;
    private Long directeurLaboId;
    
    @Enumerated(EnumType.STRING)
    private StatutCommission statut = StatutCommission.PLANIFIEE;
    
    private Boolean resultatsPublies = false;
    private LocalDateTime datePublicationResultats;
    
    // Getters et Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getTitre() { return titre; }
    public void setTitre(String titre) { this.titre = titre; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public LocalDateTime getDateCommission() { return dateCommission; }
    public void setDateCommission(LocalDateTime dateCommission) { this.dateCommission = dateCommission; }
    
    public String getLieu() { return lieu; }
    public void setLieu(String lieu) { this.lieu = lieu; }
    
    public String getHeure() { return heure; }
    public void setHeure(String heure) { this.heure = heure; }
    
    public String getMembresIds() { return membresIds; }
    public void setMembresIds(String membresIds) { this.membresIds = membresIds; }
    
    public String getSujetsIds() { return sujetsIds; }
    public void setSujetsIds(String sujetsIds) { this.sujetsIds = sujetsIds; }
    
    public Long getLaboratoireId() { return laboratoireId; }
    public void setLaboratoireId(Long laboratoireId) { this.laboratoireId = laboratoireId; }
    
    public Long getDirecteurLaboId() { return directeurLaboId; }
    public void setDirecteurLaboId(Long directeurLaboId) { this.directeurLaboId = directeurLaboId; }
    
    public StatutCommission getStatut() { return statut; }
    public void setStatut(StatutCommission statut) { this.statut = statut; }
    
    public Boolean getResultatsPublies() { return resultatsPublies; }
    public void setResultatsPublies(Boolean resultatsPublies) { this.resultatsPublies = resultatsPublies; }
    
    public LocalDateTime getDatePublicationResultats() { return datePublicationResultats; }
    public void setDatePublicationResultats(LocalDateTime datePublicationResultats) { this.datePublicationResultats = datePublicationResultats; }
    
    // Méthodes utilitaires
    public List<Long> getMembresList() {
        if (membresIds == null || membresIds.isEmpty()) {
            return List.of();
        }
        return Arrays.stream(membresIds.split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .map(Long::parseLong)
                .collect(Collectors.toList());
    }
    
    public List<Long> getSujetsList() {
        if (sujetsIds == null || sujetsIds.isEmpty()) {
            return List.of();
        }
        return Arrays.stream(sujetsIds.split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .map(Long::parseLong)
                .collect(Collectors.toList());
    }
    
    public boolean peutAjouterMembre() {
        return getMembresList().size() < 10;
    }
    
    public enum StatutCommission {
        PLANIFIEE,
        EN_COURS,
        TERMINEE
    }
}