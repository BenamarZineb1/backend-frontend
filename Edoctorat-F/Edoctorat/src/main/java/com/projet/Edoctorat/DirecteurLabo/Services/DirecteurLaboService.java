package com.projet.Edoctorat.DirecteurLabo.Services;

import com.projet.Edoctorat.Candidat.models.CandidatModel;
import com.projet.Edoctorat.Professeur.Models.ProfesseurCommission;
import java.util.List;

public interface DirecteurLaboService {
    
    // Affecter un sujet à un professeur
    void affecterSujet(Long sujetId, Long professeurId);
    
    // Récupérer les candidats du laboratoire
    List<CandidatModel> candidatsDuLabo(Long laboId);
    
    // Créer une commission
    ProfesseurCommission creerCommission(ProfesseurCommission commission);
    
    // Ajouter un sujet à une commission
    void ajouterSujetACommission(Long commissionId, Long sujetId);
    
    // Ajouter un membre à une commission
    void ajouterMembreCommission(Long commissionId, Long professeurId);
}