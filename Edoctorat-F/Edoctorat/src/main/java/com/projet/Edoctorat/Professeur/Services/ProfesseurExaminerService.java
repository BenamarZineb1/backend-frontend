package com.projet.Edoctorat.Professeur.Services;

import com.projet.Edoctorat.Professeur.Models.ExaminerDecisionRequest;
import com.projet.Edoctorat.Professeur.Models.ProfesseurExaminer;
import com.projet.Edoctorat.Professeur.Repositories.ProfesseurExaminerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProfesseurExaminerService {

    private final ProfesseurExaminerRepository repository;

    // Examiner : saisir décision + notes
    public ProfesseurExaminer evaluerCandidat(ExaminerDecisionRequest request) {

        ProfesseurExaminer examiner = repository.findById(request.getExaminerId())
                .orElseThrow(() -> new RuntimeException("Évaluation introuvable"));

        examiner.setDecision(request.getDecision());
        examiner.setNoteDossier(request.getNoteDossier());
        examiner.setNoteEntretien(request.getNoteEntretien());
        examiner.setValider(true);

        return repository.save(examiner);
    }

    // Récupérer les évaluations d’un professeur (commission)
    public List<ProfesseurExaminer> getEvaluationsParCommission(Long commissionId) {
        return repository.findByCommissionId(commissionId);
    }

    // Envoyer le PV au directeur de labo
    public void envoyerPvAuDirecteur(Long commissionId) {
        List<ProfesseurExaminer> evaluations =
                repository.findByCommissionId(commissionId);

        for (ProfesseurExaminer e : evaluations) {
            e.setPublier(true);
        }

        repository.saveAll(evaluations);
    }

    // Directeur de labo : PV global
    public List<ProfesseurExaminer> getPvGlobal() {
        return repository.findByPublierTrue();
    }
}
