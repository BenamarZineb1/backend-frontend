package com.projet.Edoctorat.Professeur.Models;

import lombok.Data;

@Data
public class ExaminerDecisionRequest {

    private Long examinerId;     // id de professeur_examiner
    private String decision;     // ACCEPTE / ATTENTE / REFUSE
    private float noteDossier;
    private int noteEntretien;
}
