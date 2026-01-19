package com.projet.Edoctorat.Professeur.Models;

import lombok.Data;

@Data
public class ProposerSujetRequest {
    private Long professeurId;
    private String titre;
    private String description;
}
