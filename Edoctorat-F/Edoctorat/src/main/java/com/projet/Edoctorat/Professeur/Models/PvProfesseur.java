package com.projet.Edoctorat.Professeur.Models;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class PvProfesseur {

    private List<ProfesseurExaminer> listePrincipale;
    private List<ProfesseurExaminer> listeAttente;
}
