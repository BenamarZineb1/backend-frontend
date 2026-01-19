package com.projet.Edoctorat.DirecteurPole.Services;

import com.projet.Edoctorat.Candidat.models.CandidatModel;
import com.projet.Edoctorat.Candidat.models.CandidatNotification;
import com.projet.Edoctorat.Candidat.repositories.CandidatNotificationRepository;
import com.projet.Edoctorat.Candidat.repositories.CandidatRepository;
import com.projet.Edoctorat.Professeur.Models.ProfesseurCommission;
import com.projet.Edoctorat.Professeur.Models.ProfesseurExaminer;
import com.projet.Edoctorat.Professeur.Models.ProfesseurSujet;
import com.projet.Edoctorat.Professeur.Repositories.ProfesseurCommissionRepository;
import com.projet.Edoctorat.Professeur.Repositories.ProfesseurExaminerRepository;
import com.projet.Edoctorat.Professeur.Repositories.ProfesseurSujetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class ConvocationService {

    private final ProfesseurExaminerRepository examinerRepository;
    private final CandidatRepository candidatRepository;
    private final ProfesseurCommissionRepository commissionRepository;
    private final ProfesseurSujetRepository sujetRepository;
    private final CandidatNotificationRepository notificationRepository;

    // =============================
    // AJOUTER UN CANDIDAT À LA CONVOCATION
    // =============================
    public void convoquerCandidat(Long commissionId, Long sujetId, Long candidatId) {

        // 🔒 bloqué si notification déjà envoyée
        if (notificationRepository.existsByCommissionIdAndSujetId(commissionId, sujetId)) {
            throw new RuntimeException("Convocation verrouillée (notification envoyée)");
        }

        // Récupérer les entités
        ProfesseurCommission commission = commissionRepository.findById(commissionId)
                .orElseThrow(() -> new RuntimeException("Commission introuvable"));
        ProfesseurSujet sujet = sujetRepository.findById(sujetId)
                .orElseThrow(() -> new RuntimeException("Sujet introuvable"));
        CandidatModel candidat = candidatRepository.findById(candidatId)
                .orElseThrow(() -> new RuntimeException("Candidat introuvable"));

        // Créer l'examiner
        ProfesseurExaminer examiner = new ProfesseurExaminer();
        examiner.setCommission(commission);
        examiner.setSujet(sujet);
        examiner.setCandidat(candidat);
        examiner.setValider(false);
        examiner.setPublier(false);

        examinerRepository.save(examiner);
    }

    // =============================
    // LISTE DES CANDIDATS CONVOQUÉS
    // =============================
    public List<CandidatModel> getCandidatsConvoques(Long commissionId, Long sujetId) {

        return examinerRepository.findByCommissionIdAndSujetId(commissionId, sujetId)
                .stream()
                .map(ProfesseurExaminer::getCandidat)
                .toList();
    }

    // =============================
    // ENVOI DE NOTIFICATION (IRRÉVERSIBLE)
    // =============================
    public void envoyerNotification(Long commissionId, Long sujetId) {

        // 🔒 si déjà envoyée → STOP
        if (notificationRepository.existsByCommissionIdAndSujetId(commissionId, sujetId)) {
            throw new RuntimeException("Notification déjà envoyée");
        }

        ProfesseurCommission commission = commissionRepository.findById(commissionId)
                .orElseThrow(() -> new RuntimeException("Commission introuvable"));
        ProfesseurSujet sujet = sujetRepository.findById(sujetId)
                .orElseThrow(() -> new RuntimeException("Sujet introuvable"));

        List<ProfesseurExaminer> convoques = examinerRepository.findByCommissionIdAndSujetId(commissionId, sujetId);

        for (ProfesseurExaminer e : convoques) {

            CandidatNotification notif = new CandidatNotification();
            notif.setType("CONVOCATION_ENTRETIEN");
            notif.setCommission(commission);
            notif.setSujet(sujet);
            notif.setCandidat(e.getCandidat());

            notificationRepository.save(notif);
        }
    }
}
