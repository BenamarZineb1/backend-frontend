package com.projet.Edoctorat.Professeur.Services;

import com.projet.Edoctorat.Professeur.Models.Sujet;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class SujetService {
    
    // Données en mémoire pour démonstration (à remplacer par DB)
    private List<Sujet> sujets = new ArrayList<>();
    private long idCounter = 1;
    
    public SujetService() {
        // Données de test
        initialiserSujetsTest();
    }
    
    private void initialiserSujetsTest() {
        // Sujet 1
        Sujet s1 = new Sujet();
        s1.setId(idCounter++);
        s1.setTitre("Intelligence Artificielle appliquée à la médecine");
        s1.setDescription("Développement de systèmes IA pour diagnostic médical");
        s1.setMotsCles("IA, machine learning, santé, diagnostic");
        s1.setDomaineRecherche("Informatique");
        s1.setSpecialite("Intelligence Artificielle");
        s1.setProfesseurId(1L);
        s1.setLaboratoireId(1L);
        s1.setFormationDoctoraleId(1L);
        s1.setDateDepot(java.time.LocalDateTime.now().minusDays(5));
        s1.setActive(true);
        s1.setStatut(Sujet.StatutSujet.OUVERT);
        sujets.add(s1);
        
        // Sujet 2
        Sujet s2 = new Sujet();
        s2.setId(idCounter++);
        s2.setTitre("Énergies renouvelables et transition écologique");
        s2.setDescription("Optimisation des panneaux solaires");
        s2.setMotsCles("énergie, solaire, environnement, durabilité");
        s2.setDomaineRecherche("Physique");
        s2.setSpecialite("Énergétique");
        s2.setProfesseurId(2L);
        s2.setLaboratoireId(2L);
        s2.setFormationDoctoraleId(2L);
        s2.setDateDepot(java.time.LocalDateTime.now().minusDays(3));
        s2.setActive(true);
        s2.setStatut(Sujet.StatutSujet.OUVERT);
        sujets.add(s2);
    }
    
    public List<Sujet> rechercherSujetsDynamique(String motsCles, String professeur, 
                                               String formation, String laboratoire, String domaine) {
        return sujets.stream()
            .filter(s -> s.getActive() && s.getStatut() == Sujet.StatutSujet.OUVERT)
            .filter(s -> motsCles == null || 
                        s.getTitre().toLowerCase().contains(motsCles.toLowerCase()) ||
                        s.getDescription().toLowerCase().contains(motsCles.toLowerCase()) ||
                        s.getMotsCles().toLowerCase().contains(motsCles.toLowerCase()))
            .filter(s -> domaine == null || 
                        s.getDomaineRecherche().toLowerCase().contains(domaine.toLowerCase()))
            .collect(Collectors.toList());
    }
    
    public List<String> getSuggestionsMotsCles(String prefix) {
        if (prefix == null || prefix.length() < 2) return List.of();
        
        return sujets.stream()
            .flatMap(s -> s.getMotsClesList().stream())
            .map(String::trim)
            .filter(mot -> mot.toLowerCase().startsWith(prefix.toLowerCase()))
            .distinct()
            .limit(10)
            .sorted()
            .collect(Collectors.toList());
    }
    
    public Map<String, Object> getStatistiques() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalSujets", sujets.size());
        stats.put("sujetsOuverts", 
                 sujets.stream().filter(s -> s.getStatut() == Sujet.StatutSujet.OUVERT).count());
        stats.put("sujetsFermes", 
                 sujets.stream().filter(s -> s.getStatut() == Sujet.StatutSujet.FERME).count());
        stats.put("domaines", 
                 sujets.stream().map(Sujet::getDomaineRecherche).distinct().count());
        return stats;
    }
    
    public List<Sujet> getSujetsRecents(int limit) {
        return sujets.stream()
            .sorted((s1, s2) -> s2.getDateDepot().compareTo(s1.getDateDepot()))
            .limit(limit)
            .collect(Collectors.toList());
    }
    
    public List<Map<String, Object>> getDomainesPopulaires() {
        Map<String, Long> domaines = sujets.stream()
            .collect(Collectors.groupingBy(Sujet::getDomaineRecherche, Collectors.counting()));
        
        return domaines.entrySet().stream()
            .map(entry -> {
                Map<String, Object> map = new HashMap<>();
                map.put("domaine", entry.getKey());
                map.put("count", entry.getValue());
                return map;
            })
            .sorted((a, b) -> ((Long)b.get("count")).compareTo((Long)a.get("count")))
            .collect(Collectors.toList());
    }
}