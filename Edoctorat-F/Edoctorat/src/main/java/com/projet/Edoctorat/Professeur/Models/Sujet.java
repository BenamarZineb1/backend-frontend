package com.projet.Edoctorat.Professeur.Models;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "professeur_sujet")
public class Sujet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String titre;
    private String description;
    private String motsCles; // séparés par virgules
    private String domaineRecherche;
    private String specialite;
    
    // IDs pour relations (sans objets complexes)
    private Long professeurId;
    private Long laboratoireId;
    private Long formationDoctoraleId;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dateDepot;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dateLimiteDepot;
    
    private Boolean active = true;
    private Integer nbCandidatsMax = 3;
    private Integer nbCandidatsActuels = 0;
    
    // Statut dynamique
    public enum StatutSujet {
        OUVERT,          // Accepte des candidatures
        FERME,           // Plus de candidatures
        EN_COMMISSION,   // En cours d'évaluation
        POURVU,          // Sujet attribué
        ANNULE           // Annulé
    }
    
    @Enumerated(EnumType.STRING)
    private StatutSujet statut = StatutSujet.OUVERT;
    
    // Méthodes utilitaires
    public boolean peutAccepterCandidats() {
        return active && 
               statut == StatutSujet.OUVERT && 
               nbCandidatsActuels < nbCandidatsMax &&
               (dateLimiteDepot == null || LocalDateTime.now().isBefore(dateLimiteDepot));
    }
    
    public void incrementerCandidats() {
        this.nbCandidatsActuels++;
        if (this.nbCandidatsActuels >= this.nbCandidatsMax) {
            this.statut = StatutSujet.FERME;
        }
    }
    
    public List<String> getMotsClesList() {
        if (motsCles == null || motsCles.trim().isEmpty()) {
            return List.of();
        }
        return List.of(motsCles.split(","));
    }
}