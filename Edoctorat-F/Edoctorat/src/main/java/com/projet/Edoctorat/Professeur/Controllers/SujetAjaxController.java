package com.projet.Edoctorat.Professeur.Controllers;

import com.projet.Edoctorat.Professeur.Models.Sujet;
import com.projet.Edoctorat.Professeur.Services.SujetService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/sujets")
@RequiredArgsConstructor
@CrossOrigin("*")
public class SujetAjaxController {
    
    private final SujetService sujetService;
    
    // Recherche dynamique avec filtres multiples
    @GetMapping("/recherche")
    public List<Sujet> rechercherSujets(
            @RequestParam(required = false) String motsCles,
            @RequestParam(required = false) String professeur,
            @RequestParam(required = false) String formation,
            @RequestParam(required = false) String laboratoire,
            @RequestParam(required = false) String domaine) {
        
        System.out.println("🔍 Recherche sujets - Mots clés: " + motsCles + 
                          ", Prof: " + professeur + ", Formation: " + formation);
        
        return sujetService.rechercherSujetsDynamique(motsCles, professeur, formation, laboratoire, domaine);
    }
    
    // Suggestions AJAX pour mots-clés
    @GetMapping("/suggestions/mots-cles")
    public List<String> getSuggestionsMotsCles(@RequestParam String prefix) {
        return sujetService.getSuggestionsMotsCles(prefix);
    }
    
    // Statistiques dynamiques
    @GetMapping("/stats")
    public Map<String, Object> getStatistiques() {
        return sujetService.getStatistiques();
    }
    
    // Sujets récents (actualité)
    @GetMapping("/recents")
    public List<Sujet> getSujetsRecents(@RequestParam(defaultValue = "10") int limit) {
        return sujetService.getSujetsRecents(limit);
    }
    
    // Sujets par domaine (nuage de tags dynamique)
    @GetMapping("/domaines")
    public List<Map<String, Object>> getDomainesPopulaires() {
        return sujetService.getDomainesPopulaires();
    }
}