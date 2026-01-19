package com.projet.Edoctorat.Scolarite.Services;

import com.projet.Edoctorat.Professeur.Models.ProfesseurInscription;
import com.projet.Edoctorat.Scolarite.Dto.DossierDto;
import java.util.List;
import java.util.Map;

public interface ScolariteService {

    List<DossierDto> getTousLesDossiers();

    DossierDto getDossierParCne(String cne);

    void validerDossier(Long candidatId);

    ProfesseurInscription commenterDossier(Long candidatId, String commentaire);
    
    // Méthodes avancées
    Map<String, Object> getStatistiques();
    
    List<DossierDto> rechercherDossiers(String nom, String cne, Boolean valide);
    
    String exporterDossiers();
    
    List<Map<String, Object>> getHistoriqueValidations();
}
