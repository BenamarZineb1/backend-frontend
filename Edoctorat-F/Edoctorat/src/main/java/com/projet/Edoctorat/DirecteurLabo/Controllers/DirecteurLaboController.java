package com.projet.Edoctorat.DirecteurLabo.Controllers;

import com.projet.Edoctorat.Commission.Models.Commission;
import com.projet.Edoctorat.DirecteurLabo.Services.DirecteurLaboService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/directeur-labo")
@RequiredArgsConstructor
@CrossOrigin("*")
public class DirecteurLaboController {
    
    private final DirecteurLaboService directeurLaboService;
    
    // Affecter des sujets à un professeur
    @PostMapping("/{laboratoireId}/affecter-sujets")
    public void affecterSujetsAuProfesseur(
            @PathVariable Long laboratoireId,
            @RequestParam Long professeurId,
            @RequestBody List<Long> sujetIds) {
        directeurLaboService.affecterSujetsAuProfesseur(professeurId, sujetIds);
    }
    
    // Voir tous les candidats du laboratoire
    @GetMapping("/{laboratoireId}/candidats")
    public List<Map<String, Object>> getCandidatsDuLaboratoire(@PathVariable Long laboratoireId) {
        return directeurLaboService.getCandidatsDuLaboratoire(laboratoireId);
    }
    
    // Créer une commission avec sujets
    @PostMapping("/{laboratoireId}/commissions")
    public Commission creerCommissionAvecSujets(
            @PathVariable Long laboratoireId,
            @RequestBody Commission commission,
            @RequestParam List<Long> sujetIds) {
        return directeurLaboService.creerCommissionAvecSujets(commission, sujetIds);
    }
    
    // Programmer des sujets dans une commission existante
    @PostMapping("/commissions/{commissionId}/programmer-sujets")
    public void programmerSujetsDansCommission(
            @PathVariable Long commissionId,
            @RequestBody List<Long> sujetIds) {
        directeurLaboService.programmerSujetsDansCommission(commissionId, sujetIds);
    }
    
    // Statistiques laboratoire
    @GetMapping("/{laboratoireId}/stats")
    public Map<String, Object> getStatistiquesLaboratoire(@PathVariable Long laboratoireId) {
        return directeurLaboService.getStatistiquesLaboratoire(laboratoireId);
    }
    
    // Générer PV global
    @PostMapping("/{laboratoireId}/generer-pv")
    public Map<String, Object> genererPvGlobal(@PathVariable Long laboratoireId) {
        return directeurLaboService.genererPvGlobal(laboratoireId);
    }
    
    // Tests
    @PostMapping("/test/affectation")
    public void testAffectation() {
        directeurLaboService.affecterSujetsAuProfesseur(1L, List.of(1L, 2L, 3L));
    }
}