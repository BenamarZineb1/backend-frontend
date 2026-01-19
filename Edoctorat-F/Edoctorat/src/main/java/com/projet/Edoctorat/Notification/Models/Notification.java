package com.projet.Edoctorat.Notification.Models;

import lombok.Data;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "notifications")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String titre;
    private String message;
    private String type; // ENTRETIEN, RESULTAT, COMMISSION, GENERAL
    
    @Enumerated(EnumType.STRING)
    private StatutNotification statut = StatutNotification.NON_LUE;
    
    private Long destinataireId; // ID de l'utilisateur
    private String destinataireRole; // CANDIDAT, PROFESSEUR, etc.
    
    private Long sujetId; // Optionnel - lien vers sujet concerné
    private Long commissionId; // Optionnel - lien vers commission
    
    private LocalDateTime dateCreation;
    private LocalDateTime dateLecture;
    
    private Boolean importante = false;
    private String lienAction; // URL pour action spécifique
    
    public enum StatutNotification {
        NON_LUE,
        LUE,
        ARCHIVEE
    }
    
    @PrePersist
    protected void onCreate() {
        dateCreation = LocalDateTime.now();
    }
}