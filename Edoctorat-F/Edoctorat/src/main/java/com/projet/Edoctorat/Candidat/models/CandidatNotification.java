package com.projet.Edoctorat.Candidat.models;

import com.projet.Edoctorat.Professeur.Models.ProfesseurCommission;
import com.projet.Edoctorat.Professeur.Models.ProfesseurSujet;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "candidat_notification")
public class CandidatNotification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type;

    @ManyToOne
    @JoinColumn(name = "candidat_id", nullable = false)
    private CandidatModel candidat;

    @ManyToOne
    @JoinColumn(name = "commission_id")
    private ProfesseurCommission commission;

    @ManyToOne
    @JoinColumn(name = "sujet_id")
    private ProfesseurSujet sujet;
}
