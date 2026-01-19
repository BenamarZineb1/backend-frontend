package com.projet.Edoctorat.DirecteurCED.Controllers;

import com.projet.Edoctorat.DirecteurCED.Services.DirecteurCedService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/directeur-ced")
@RequiredArgsConstructor
@CrossOrigin("*")
public class DirecteurCedController {
    
    private final DirecteurCedService directeurCedService;
    
    // ✅ Consulter les candidats
    @GetMapping("/candidats")
    public ResponseEntity<List<Map<String, Object>>> getCandidats() {
        return ResponseEntity.ok(directeurCedService.getAllCandidats());
    }
    
    // ✅ Consulter les sujets
    @GetMapping("/sujets")
    public ResponseEntity<List<Map<String, Object>>> getSujets() {
        return ResponseEntity.ok(directeurCedService.getAllSujets());
    }
    
    // ✅ Consulter les résultats
    @GetMapping("/resultats")
    public ResponseEntity<List<Map<String, Object>>> getResultats() {
        return ResponseEntity.ok(directeurCedService.getResultats());
    }
    
    // ✅ Consulter les inscrits
    @GetMapping("/inscriptions")
    public ResponseEntity<List<Map<String, Object>>> getInscriptions() {
        return ResponseEntity.ok(directeurCedService.getInscriptions());
    }
    
    // ✅ Statistiques globales
    @GetMapping("/stats")
    public ResponseEntity<Map<String, Object>> getStatistiques() {
        return ResponseEntity.ok(directeurCedService.getStatistiquesGlobales());
    }
    
    // ✅ Commissions globales
    @GetMapping("/commissions")
    public ResponseEntity<List<Object>> getCommissions() {
        return ResponseEntity.ok((List<Object>) (List<?>) directeurCedService.getCommissionsGlobales());
    }
    
    // ✅ Générer rapport global
    @PostMapping("/rapport-global")
    public ResponseEntity<Map<String, Object>> genererRapportGlobal() {
        return ResponseEntity.ok(directeurCedService.genererRapportGlobal());
    }
    
    // ✅ Tests
    @PostMapping("/test/data")
    public ResponseEntity<String> testData() {
        directeurCedService.getAllCandidats();
        directeurCedService.getAllSujets();
        return ResponseEntity.ok("Données de test chargées");
    }
}