package com.projet.Edoctorat.Professeur.Controllers;

import com.projet.Edoctorat.Professeur.Models.PvProfesseur;
import com.projet.Edoctorat.Professeur.Services.PvProfesseurService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/professeur/pv")
@RequiredArgsConstructor
@CrossOrigin("*")
public class PvProfesseurController {

    private final PvProfesseurService service;

    @GetMapping("/commission/{commissionId}")
    public ResponseEntity<byte[]> pvProfesseur(@PathVariable Long commissionId) {

        PvProfesseur pv = service.genererPvProfesseur(commissionId);

        // Convertir le PV en texte
        StringBuilder sb = new StringBuilder();
        sb.append("PV PROFESSEUR - COMMISSION ").append(commissionId).append("\n\n");

        sb.append("Liste principale :\n");
        pv.getListePrincipale().forEach(e ->
                sb.append("Candidat : ")
                        .append(e.getCandidat().getPrenomCandidatAr())
                        .append(" ")
                        .append(e.getCandidat().getNomCandidatAr())
                        .append(" | Décision : ").append(e.getDecision())
                        .append("\n")
        );

        sb.append("\nListe en attente :\n");
        pv.getListeAttente().forEach(e ->
                sb.append("Candidat : ")
                        .append(e.getCandidat().getPrenomCandidatAr())
                        .append(" ")
                        .append(e.getCandidat().getNomCandidatAr())
                        .append(" | Décision : ").append(e.getDecision())
                        .append("\n")
        );

        byte[] bytes = sb.toString().getBytes();

        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=pv_professeur.txt")
                .body(bytes);
    }
}
