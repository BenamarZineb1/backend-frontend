package com.projet.Edoctorat.Candidat.controllers;

import com.projet.Edoctorat.Candidat.models.CandidatNotification;
import com.projet.Edoctorat.Candidat.services.CandidatNotificationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notifications")
public class CandidatNotificationController {
    private final CandidatNotificationService service;

    public CandidatNotificationController(CandidatNotificationService service) {
        this.service = service;
    }

    @GetMapping("/candidat/{id}")
    public List<CandidatNotification> getByCandidat(@PathVariable Long id) {
        return service.getByCandidatId(id);
    }
}
