package com.projet.Edoctorat.DirecteurCED.Services;

import com.projet.Edoctorat.Commission.Models.Commission;
import com.projet.Edoctorat.Commission.Services.CommissionService;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class DirecteurCedServiceImpl implements DirecteurCedService {
    
    private final CommissionService commissionService;
    
    public DirecteurCedServiceImpl(CommissionService commissionService) {
        this.commissionService = commissionService;
    }
    
    @Override
    public List<Map<String, Object>> getAllCandidats() {
        List<Map<String, Object>> candidats = new ArrayList<>();
        
        Map<String, Object> c1 = new HashMap<>();
        c1.put("id", 1L);
        c1.put("nom", "Ben Amar");
        c1.put("prenom", "Zineb");
        c1.put("email", "zineb.benamar@doctorat.ma");
        c1.put("cne", "R123456789");
        c1.put("etatDossier", "COMPLET");
        c1.put("laboratoire", "Informatique");
        candidats.add(c1);
        
        Map<String, Object> c2 = new HashMap<>();
        c2.put("id", 2L);
        c2.put("nom", "El Mansouri");
        c2.put("prenom", "Ahmed");
        c2.put("email", "ahmed.elmansouri@doctorat.ma");
        c2.put("cne", "R987654321");
        c2.put("etatDossier", "COMPLET");
        c2.put("laboratoire", "Physique");
        candidats.add(c2);
        
        System.out.println("📋 Directeur CED - Récupération de " + candidats.size() + " candidats");
        return candidats;
    }
    
    @Override
    public List<Map<String, Object>> getAllSujets() {
        List<Map<String, Object>> sujets = new ArrayList<>();
        
        Map<String, Object> s1 = new HashMap<>();
        s1.put("id", 1L);
        s1.put("titre", "IA médicale");
        s1.put("description", "Application de l'intelligence artificielle en médecine");
        s1.put("laboratoire", "Informatique");
        s1.put("professeur", "Dr. Martin Dupont");
        s1.put("etat", "ATTRIBUE");
        sujets.add(s1);
        
        Map<String, Object> s2 = new HashMap<>();
        s2.put("id", 2L);
        s2.put("titre", "Énergies renouvelables");
        s2.put("description", "Optimisation des systèmes énergétiques durables");
        s2.put("laboratoire", "Physique");
        s2.put("professeur", "Pr. Fatima Zahra");
        s2.put("etat", "LIBRE");
        sujets.add(s2);
        
        System.out.println("📚 Directeur CED - Récupération de " + sujets.size() + " sujets");
        return sujets;
    }
    
    @Override
    public List<Map<String, Object>> getResultats() {
        List<Map<String, Object>> resultats = new ArrayList<>();
        
        Map<String, Object> r1 = new HashMap<>();
        r1.put("id", 1L);
        r1.put("candidat", "Zineb Ben Amar");
        r1.put("sujet", "IA médicale");
        r1.put("decision", "ADMIS");
        r1.put("noteFinale", 16.5);
        r1.put("commission", "Commission Informatique");
        resultats.add(r1);
        
        System.out.println("📊 Directeur CED - Récupération de " + resultats.size() + " résultats");
        return resultats;
    }
    
    @Override
    public List<Map<String, Object>> getInscriptions() {
        List<Map<String, Object>> inscriptions = new ArrayList<>();
        
        Map<String, Object> i1 = new HashMap<>();
        i1.put("id", 1L);
        i1.put("candidat", "Zineb Ben Amar");
        i1.put("sujet", "IA médicale");
        i1.put("statut", "VALIDEE");
        i1.put("date", "2024-01-15");
        inscriptions.add(i1);
        
        System.out.println("📝 Directeur CED - Récupération de " + inscriptions.size() + " inscriptions");
        return inscriptions;
    }
    
    // Nouvelles fonctionnalités avancées
    public Map<String, Object> getStatistiquesGlobales() {
        Map<String, Object> stats = new HashMap<>();
        
        // Stats de base
        stats.put("totalCandidats", getAllCandidats().size());
        stats.put("totalSujets", getAllSujets().size());
        stats.put("totalInscriptions", getInscriptions().size());
        stats.put("totalResultats", getResultats().size());
        
        // Stats commissions
        Map<String, Object> commissionStats = commissionService.getStatistiquesCommissions(1L);
        stats.putAll(commissionStats);
        
        // Stats par décision
        Map<String, Long> decisions = getResultats().stream()
            .collect(java.util.stream.Collectors.groupingBy(
                r -> (String) r.get("decision"),
                java.util.stream.Collectors.counting()
            ));
        stats.put("resultatsParDecision", decisions);
        
        return stats;
    }
    
    public List<Commission> getCommissionsGlobales() {
        return commissionService.getCommissionsParLaboratoire(1L);
    }
    
    public Map<String, Object> genererRapportGlobal() {
        Map<String, Object> rapport = new HashMap<>();
        rapport.put("dateGeneration", new Date());
        rapport.put("periode", "Année universitaire 2023-2024");
        
        // Agréger toutes les données
        rapport.put("statistiques", getStatistiquesGlobales());
        rapport.put("commissions", getCommissionsGlobales().size());
        rapport.put("candidats", getAllCandidats().size());
        rapport.put("sujets", getAllSujets().size());
        
        System.out.println("📈 Rapport global généré par Directeur CED");
        return rapport;
    }
}