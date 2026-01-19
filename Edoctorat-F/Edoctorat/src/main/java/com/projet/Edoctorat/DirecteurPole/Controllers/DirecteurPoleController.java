package com.projet.Edoctorat.DirecteurPole.Controllers;

import com.projet.Edoctorat.DirecteurPole.Models.DirecteurPoleCalendrier;
import com.projet.Edoctorat.DirecteurPole.Services.DirecteurPoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/directeur-pole")
@RequiredArgsConstructor
@CrossOrigin("*")
public class DirecteurPoleController {
    
    private final DirecteurPoleService directeurPoleService;
    
    // ===== CONSULTATION =====
    
    @GetMapping("/candidats")
    public ResponseEntity<List<Object>> getCandidats() {
        List<Object> result = directeurPoleService.getAllCandidats().stream()
            .map(item -> (Object) item)
            .collect(java.util.stream.Collectors.toList());
        return ResponseEntity.ok(result);
    }
    
    @GetMapping("/sujets")
    public ResponseEntity<List<Object>> getSujets() {
        List<Object> result = directeurPoleService.getAllSujets().stream()
            .map(item -> (Object) item)
            .collect(java.util.stream.Collectors.toList());
        return ResponseEntity.ok(result);
    }
    
    @GetMapping("/resultats")
    public ResponseEntity<List<Object>> getResultats() {
        List<Object> result = directeurPoleService.getResultats().stream()
            .map(item -> (Object) item)
            .collect(java.util.stream.Collectors.toList());
        return ResponseEntity.ok(result);
    }
    
    @GetMapping("/inscriptions")
    public ResponseEntity<List<Object>> getInscriptions() {
        List<Object> result = directeurPoleService.getInscriptions().stream()
            .map(item -> (Object) item)
            .collect(java.util.stream.Collectors.toList());
        return ResponseEntity.ok(result);
    }
    
    // ===== CALENDRIER =====
    
    @PostMapping("/calendrier")
    public ResponseEntity<DirecteurPoleCalendrier> addCalendrier(
            @RequestBody DirecteurPoleCalendrier calendrier) {
        return ResponseEntity.ok(directeurPoleService.saveCalendrier(calendrier));
    }
    
    @GetMapping("/calendrier")
    public ResponseEntity<List<DirecteurPoleCalendrier>> getCalendrier() {
        return ResponseEntity.ok(directeurPoleService.getCalendrier());
    }
    
    @GetMapping("/calendrier/depot-sujet-ouvert")
    public boolean isDepotSujetOuvert() {
        return directeurPoleService.isDepotSujetOuvert();
    }
    
    // ===== GESTION DES SUJETS =====
    
    @GetMapping("/sujets/en-attente")
    public ResponseEntity<List<Object>> sujetsEnAttente() {
        List<Object> result = directeurPoleService.getSujetsEnAttente().stream()
            .map(item -> (Object) item)
            .collect(java.util.stream.Collectors.toList());
        return ResponseEntity.ok(result);
    }
    
    @PutMapping("/sujets/publier")
    public ResponseEntity<String> publierSujets(@RequestBody List<Long> idsSujets) {
        directeurPoleService.publierSujetsSelectionnes(idsSujets);
        return ResponseEntity.ok("Sujets sélectionnés publiés avec succès");
    }
    
    // ===== PUBLICATION FINALE =====
    
    @PostMapping("/publier-lp")
    public ResponseEntity<String> publierLP() {
        return ResponseEntity.ok(directeurPoleService.publierLP());
    }
    
    @PostMapping("/publier-la")
    public ResponseEntity<String> publierLA() {
        return ResponseEntity.ok(directeurPoleService.publierLA());
    }
    
    // ===== FONCTIONNALITES AVANCEES =====
    
    @PostMapping("/publier-resultats-finaux")
    public ResponseEntity<String> publierResultatsFinaux() {
        // Publier tous les résultats validés
        String resultatLP = directeurPoleService.publierLP();
        String resultatLA = directeurPoleService.publierLA();
        return ResponseEntity.ok("Publication finale terminée: " + resultatLP + ", " + resultatLA);
    }
    
    @GetMapping("/stats-publication")
    public ResponseEntity<Map<String, Object>> getStatsPublication() {
        Map<String, Object> stats = new java.util.HashMap<>();
        stats.put("sujetsPublies", directeurPoleService.getAllSujets().stream()
            .filter(s -> ((Map<String, Object>) s).get("publier") != null && 
                         (Boolean) ((Map<String, Object>) s).get("publier"))
            .count());
        stats.put("resultatsPublies", directeurPoleService.getResultats().stream()
            .filter(r -> ((Map<String, Object>) r).get("publier") != null && 
                         (Boolean) ((Map<String, Object>) r).get("publier"))
            .count());
        return ResponseEntity.ok(stats);
    }
    
    // ===== TESTS =====
    
    @PostMapping("/test/publication")
    public ResponseEntity<String> testPublication() {
        return ResponseEntity.ok("Test publication effectué");
    }
}