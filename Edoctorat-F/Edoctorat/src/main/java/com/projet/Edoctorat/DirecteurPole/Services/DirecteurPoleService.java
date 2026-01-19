package com.projet.Edoctorat.DirecteurPole.Services;

import com.projet.Edoctorat.Candidat.models.CandidatModel;
import com.projet.Edoctorat.DirecteurPole.Models.DirecteurPoleCalendrier;
import com.projet.Edoctorat.Professeur.Models.ProfesseurExaminer;
import com.projet.Edoctorat.Professeur.Models.ProfesseurInscription;
import com.projet.Edoctorat.Professeur.Models.ProfesseurSujet;

import java.util.List;

public interface DirecteurPoleService {

    // Consultation
    List<CandidatModel> getAllCandidats();
    List<ProfesseurSujet> getAllSujets();
    List<ProfesseurExaminer> getResultats();
    List<ProfesseurInscription> getInscriptions();
    boolean isDepotSujetOuvert();

    // Calendrier
    DirecteurPoleCalendrier saveCalendrier(DirecteurPoleCalendrier calendrier);
    List<DirecteurPoleCalendrier> getCalendrier();
    // Sujets
    List<ProfesseurSujet> getSujetsEnAttente();
    void publierSujetsSelectionnes(List<Long> idsSujets);

    // Publication (logique métier)
    String publierLP();
    String publierLA();
}
