package com.projet.Edoctorat.DirecteurPole.Services;
import com.projet.Edoctorat.Candidat.models.CandidatModel;
import com.projet.Edoctorat.Candidat.repositories.CandidatRepository;
import com.projet.Edoctorat.DirecteurPole.Models.DirecteurPoleCalendrier;
import com.projet.Edoctorat.DirecteurPole.Repositories.DirecteurPoleCalendrierRepository;
import java.util.Date;
import com.projet.Edoctorat.DirecteurPole.Models.ActionCalendrier;
import com.projet.Edoctorat.Professeur.Models.ProfesseurExaminer;
import com.projet.Edoctorat.Professeur.Models.ProfesseurInscription;
import com.projet.Edoctorat.Professeur.Models.ProfesseurSujet;
import com.projet.Edoctorat.Professeur.Repositories.ProfesseurExaminerRepository;
import com.projet.Edoctorat.Professeur.Repositories.ProfesseurInscriptionRepository;
import com.projet.Edoctorat.Professeur.Repositories.ProfesseurSujetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DirecteurPoleServiceImpl implements DirecteurPoleService {

    private final CandidatRepository candidatRepository;
    private final ProfesseurSujetRepository sujetRepository;
    private final ProfesseurExaminerRepository examinerRepository;
    private final ProfesseurInscriptionRepository inscriptionRepository;
    private final DirecteurPoleCalendrierRepository calendrierRepository;

    // ===== CONSULTATION =====

    @Override
    public List<CandidatModel> getAllCandidats() {
        return candidatRepository.findAll();
    }

    @Override
    public List<ProfesseurSujet> getAllSujets() {
        return sujetRepository.findAll();
    }

    @Override
    public List<ProfesseurExaminer> getResultats() {
        return examinerRepository.findAll();
    }

    @Override
    public List<ProfesseurInscription> getInscriptions() {
        return inscriptionRepository.findAll();
    }

    // ===== CALENDRIER =====

    @Override
    public DirecteurPoleCalendrier saveCalendrier(DirecteurPoleCalendrier calendrier) {
        return calendrierRepository.save(calendrier);
    }

    @Override
    public List<DirecteurPoleCalendrier> getCalendrier() {
        return calendrierRepository.findAll();
    }

    //sujets
    @Override
    public List<ProfesseurSujet> getSujetsEnAttente() {
        return sujetRepository.findByPublierFalse();
    }

    @Override
    public void publierSujetsSelectionnes(List<Long> idsSujets) {

        List<ProfesseurSujet> sujets = sujetRepository.findAllById(idsSujets);

        sujets.forEach(sujet -> sujet.setPublier(true));

        sujetRepository.saveAll(sujets);
    }

    @Override
    public boolean isDepotSujetOuvert() {
        Date now = new Date();

        return calendrierRepository
                .existsByActionAndDateDebutLessThanEqualAndDateFinGreaterThanEqual(
                        ActionCalendrier.DEPOT_SUJET,
                        now,
                        now
                );
    }



    // ===== PUBLICATION =====

    @Override
    public String publierLP() {
        List<ProfesseurExaminer> resultats = examinerRepository.findAll();

        resultats.forEach(r -> {
            if (r.isValider()) {
                r.setPublier(true);
            }
        });

        examinerRepository.saveAll(resultats);
        return "Liste Principale publiée";
    }

    @Override
    public String publierLA() {
        List<ProfesseurExaminer> resultats = examinerRepository.findAll();

        resultats.forEach(r -> {
            if (!r.isValider()) {
                r.setPublier(true);
            }
        });

        examinerRepository.saveAll(resultats);
        return "Liste d’Attente publiée";
    }

}
