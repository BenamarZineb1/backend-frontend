package com.projet.Edoctorat.DirecteurPole.Controllers;

import com.projet.Edoctorat.DirecteurPole.Services.ConvocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/directeur-pole/convocation")
@RequiredArgsConstructor
public class ConvocationController {

    private final ConvocationService service;

    @PostMapping("/ajouter")
    public void convoquerCandidat(
            @RequestParam Long commissionId,
            @RequestParam Long sujetId,
            @RequestParam Long candidatId
    ) {
        service.convoquerCandidat(commissionId, sujetId, candidatId);
    }

    @PostMapping("/envoyer-notification")
    public void envoyerNotification(
            @RequestParam Long commissionId,
            @RequestParam Long sujetId
    ) {
        service.envoyerNotification(commissionId, sujetId);
    }
}
