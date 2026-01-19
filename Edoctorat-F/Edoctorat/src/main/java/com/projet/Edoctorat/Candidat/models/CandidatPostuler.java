package com.projet.Edoctorat.Candidat.models;

import com.projet.Edoctorat.Professeur.Models.ProfesseurSujet;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "candidat_postuler")
public class CandidatPostuler {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String pathFile;

    @ManyToOne
    @JoinColumn(name = "candidat_id", nullable = false)
    private CandidatModel candidat;

    @ManyToOne
    @JoinColumn(name = "sujet_id", nullable = false)
    private ProfesseurSujet sujet;
}
