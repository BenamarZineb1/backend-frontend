package com.projet.Edoctorat.Professeur.Controllers;

import com.projet.Edoctorat.Professeur.Models.ProfesseurSujet;
import com.projet.Edoctorat.Professeur.Models.ProposerSujetRequest;
import com.projet.Edoctorat.Professeur.Services.ProfesseurSujetService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/professeur/sujets")
@RequiredArgsConstructor
public class ProfesseurSujetController {

    private final ProfesseurSujetService service;

    // Proposer un sujet
    @PostMapping("/proposer")
    public ProfesseurSujet proposerSujet(@RequestBody ProposerSujetRequest request) {
        return service.proposerSujet(request.getProfesseurId(), request.getTitre(), request.getDescription());
    }

    // Lister les sujets d’un professeur
    @GetMapping("/mes-sujets/{professeurId}")
    public List<ProfesseurSujet> mesSujets(@PathVariable Long professeurId) {
        return service.getSujetsParProfesseur(professeurId);
    }
}
