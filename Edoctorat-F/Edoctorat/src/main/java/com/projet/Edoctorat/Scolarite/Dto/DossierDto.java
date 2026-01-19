package com.projet.Edoctorat.Scolarite.Dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class DossierDto {

    private Long candidatId;

    private String cne;
    private String nom;
    private String prenom;

    private String sujet;
    private String formationDoctorale;
    private String ced;
    private String pole;

    private boolean ficheDepot;
    private boolean recuDepot;
    private boolean demandeBourse;

    private boolean valide;
    private LocalDate dateDepot;
    private String commentaire;
}
