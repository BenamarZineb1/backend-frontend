package com.projet.Edoctorat.Candidat.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "candidat_annexe")
public class CandidatAnnexe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String typeAnnexe;
    private String titre;
    private String pathFile;

    // Relation avec Diplome
    @ManyToOne
    @JoinColumn(name = "diplome_id", nullable = false)
    private CandidatDiplome diplome;
}
