package com.projet.Edoctorat.Calendrier.Models;

import lombok.Data;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "calendrier_evenements")
public class EvenementCalendrier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String titre;
    private String description;
    private String type; // ENTRETIEN, DEPOT, COMMISSION, DEADLINE
    
    @Enumerated(EnumType.STRING)
    private StatutEvenement statut = StatutEvenement.PLANIFIE;
    
    private LocalDateTime dateDebut;
    private LocalDateTime dateFin;
    
    private String lieu;
    private Boolean online = false;
    
    // Participants
    private String participantsIds; // IDs séparés par virgules
    private String rolesConcernes; // CANDIDAT, PROFESSEUR, etc.
    
    // Rappels
    private Boolean rappelActif = true;
    private Integer rappelMinutesAvant = 60; // 1h avant par défaut
    
    // Liens
    private Long sujetId; // Optionnel
    private Long commissionId; // Optionnel
    
    public enum StatutEvenement {
        PLANIFIE,
        EN_COURS,
        TERMINE,
        ANNULE
    }
    
    public boolean estDansLeFutur() {
        return dateDebut != null && dateDebut.isAfter(LocalDateTime.now());
    }
    
    public boolean estEnCours() {
        LocalDateTime now = LocalDateTime.now();
        return dateDebut != null && dateFin != null && 
               now.isAfter(dateDebut) && now.isBefore(dateFin);
    }
}