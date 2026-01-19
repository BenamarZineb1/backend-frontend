package com.projet.Edoctorat.Scolarite.Controllers;

import com.projet.Edoctorat.Scolarite.Dto.DossierDto;
import com.projet.Edoctorat.Scolarite.Services.ScolariteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/scolarite")
@RequiredArgsConstructor
@CrossOrigin("*")
public class ScolariteController {
    
    private final ScolariteService scolariteService;
    
    /**
     * 🔹 Voir tous les dossiers des doctorants
     */
    @GetMapping("/dossiers")
    public ResponseEntity<List<DossierDto>> getTousLesDossiers() {
        return ResponseEntity.ok(scolariteService.getTousLesDossiers());
    }
    
    /**
     * 🔹 Voir le dossier d'un doctorant par CNE
     */
    @GetMapping("/dossier/{cne}")
    public ResponseEntity<DossierDto> getDossierParCne(@PathVariable String cne) {
        return ResponseEntity.ok(scolariteService.getDossierParCne(cne));
    }
    
    /**
     * 🔹 Valider le dossier d'un doctorant
     * (validation du reçu de dépôt)
     */
    @PutMapping("/valider/{candidatId}")
    public ResponseEntity<String> validerDossier(@PathVariable Long candidatId) {
        scolariteService.validerDossier(candidatId);
        return ResponseEntity.ok("Dossier validé avec succès");
    }
    
    /**
     * 🔹 Commenter le dossier d'un doctorant
     */
    @PutMapping("/commenter/{candidatId}")
    public ResponseEntity<String> commenter(
            @PathVariable Long candidatId,
            @RequestParam String commentaire) {
        scolariteService.commenterDossier(candidatId, commentaire);
        return ResponseEntity.ok("Commentaire ajouté avec succès");
    }
    
    /**
     * 🔹 Statistiques scolarité
     */
    @GetMapping("/stats")
    public ResponseEntity<Map<String, Object>> getStatistiques() {
        return ResponseEntity.ok(scolariteService.getStatistiques());
    }
    
    /**
     * 🔹 Rechercher dossiers par critères
     */
    @GetMapping("/recherche")
    public ResponseEntity<List<DossierDto>> rechercherDossiers(
            @RequestParam(required = false) String nom,
            @RequestParam(required = false) String cne,
            @RequestParam(required = false) Boolean valide) {
        return ResponseEntity.ok(scolariteService.rechercherDossiers(nom, cne, valide));
    }
    
    /**
     * 🔹 Exporter liste dossiers
     */
    @GetMapping("/export")
    public ResponseEntity<String> exporterDossiers() {
        String export = scolariteService.exporterDossiers();
        return ResponseEntity.ok(export);
    }
    
    /**
     * 🔹 Historique validations
     */
    @GetMapping("/historique")
    public ResponseEntity<List<Map<String, Object>>> getHistoriqueValidations() {
        return ResponseEntity.ok(scolariteService.getHistoriqueValidations());
    }
    
    /**
     * 🔹 Tests
     */
    @PostMapping("/test/validation")
    public ResponseEntity<String> testValidation() {
        return ResponseEntity.ok("Test validation effectué");
    }
}