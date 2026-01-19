package com.projet.Edoctorat.Candidat.controllers;

import com.projet.Edoctorat.Candidat.models.CandidatModel;
import com.projet.Edoctorat.Candidat.models.CandidatDiplome;
import com.projet.Edoctorat.Candidat.models.CandidatModel;
import com.projet.Edoctorat.Candidat.services.CandidatDiplomeService;
import com.projet.Edoctorat.Candidat.services.CandidatService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/candidat/diplome")
@RequiredArgsConstructor
@CrossOrigin("*")
public class CandidatDiplomeController {

    private final CandidatDiplomeService diplomeService;
    private final CandidatService candidatService;

    // Ajouter un diplôme via CNE
    @PostMapping("/add/{cne}")
    public CandidatDiplome addDiplome(@PathVariable String cne, @RequestBody CandidatDiplome diplome) {
        // On récupère le candidat depuis le service
        CandidatModel candidat = candidatService.getByCne(cne);
        diplome.setCandidat(candidat);
        return diplomeService.addDiplome(diplome);
    }

    // Lister tous les diplômes d’un candidat via CNE
    @GetMapping("/list/{cne}")
    public List<CandidatDiplome> listDiplomes(@PathVariable String cne) {
        // On récupère l'id du candidat
        CandidatModel candidat = candidatService.getByCne(cne);
        return diplomeService.getByCandidatId(candidat.getId());
    }
}
