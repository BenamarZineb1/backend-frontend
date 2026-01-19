package com.projet.Edoctorat.DirecteurCED.Services;

import com.projet.Edoctorat.Commission.Models.Commission;
import java.util.List;
import java.util.Map;

public interface DirecteurCedService {

    List<Map<String, Object>> getAllCandidats();

    List<Map<String, Object>> getAllSujets();

    List<Map<String, Object>> getResultats();

    List<Map<String, Object>> getInscriptions();
    
    // Méthodes avancées
    Map<String, Object> getStatistiquesGlobales();
    
    List<Commission> getCommissionsGlobales();
    
    Map<String, Object> genererRapportGlobal();
}
