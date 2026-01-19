package com.projet.Edoctorat.Candidat.models;

import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="candidat_diplome")
public class CandidatDiplome {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String intitule;
    private String type;
    private java.sql.Date dateCommission;
    private String mention;
    private String pays;
    private String etablissement;
    private String specialite;
    private String ville;
    private String province;
    private Double moyen_generale;

    @ManyToOne
    @JoinColumn(name="candidat_id")
    private CandidatModel candidat;
}
