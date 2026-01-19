package com.projet.Edoctorat.Candidat.controllers;

import com.projet.Edoctorat.Candidat.models.CandidatPostuler;
import com.projet.Edoctorat.Candidat.services.CandidatPostulerService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/candidat/postuler")
public class CandidatPostulerController {
    private final CandidatPostulerService service;

    public CandidatPostulerController(CandidatPostulerService service) {
        this.service = service;
    }

    @GetMapping("/candidat/{id}")
    public List<CandidatPostuler> getByCandidat(@PathVariable Long id) {
        return service.getByCandidatId(id);
    }

    @PostMapping
    public CandidatPostuler create(@RequestBody CandidatPostuler p) {
        return service.save(p);
    }
}
