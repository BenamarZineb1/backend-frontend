package com.projet.Edoctorat.DirecteurLabo.Services;

import com.projet.Edoctorat.Candidat.models.CandidatModel;
import com.projet.Edoctorat.Candidat.repositories.CandidatRepository;
import com.projet.Edoctorat.Professeur.Models.Professeur;
import com.projet.Edoctorat.Professeur.Models.ProfesseurCommission;
import com.projet.Edoctorat.Professeur.Models.ProfesseurCommissionProfesseurs;
import com.projet.Edoctorat.Professeur.Models.ProfesseurSujet;
import com.projet.Edoctorat.Professeur.Repositories.ProfesseurCommissionProfesseursRepository;
import com.projet.Edoctorat.Professeur.Repositories.ProfesseurCommissionRepository;
import com.projet.Edoctorat.Professeur.Repositories.ProfesseurRepository;
import com.projet.Edoctorat.Professeur.Repositories.ProfesseurSujetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DirecteurLaboServiceImpl implements DirecteurLaboService {

    private final ProfesseurSujetRepository sujetRepository;
    private final ProfesseurCommissionRepository commissionRepository;
    private final ProfesseurCommissionProfesseursRepository commissionProfRepository;
    private final ProfesseurRepository professeurRepository;
    private final CandidatRepository candidatRepository;

    // ================== 1️⃣ AFFECTER SUJET ==================
    @Override
    public void affecterSujet(Long sujetId, Long professeurId) {

        ProfesseurSujet sujet = sujetRepository.findById(sujetId)
                .orElseThrow(() -> new RuntimeException("Sujet introuvable"));

        Professeur professeur = professeurRepository.findById(professeurId)
                .orElseThrow(() -> new RuntimeException("Professeur introuvable"));

        sujet.setProfesseur(professeur);
        sujetRepository.save(sujet);
    }

    // ================== 2️⃣ CANDIDATS DU LABO ==================
    @Override
    public List<CandidatModel> candidatsDuLabo(Long laboId) {
        return candidatRepository.findAll();
    }

    // ================== 3️⃣ CRÉER COMMISSION ==================
    @Override
    public ProfesseurCommission creerCommission(ProfesseurCommission commission) {
        return commissionRepository.save(commission);
    }

    // ================== 4️⃣ AJOUT SUJET À COMMISSION ==================
    @Override
    public void ajouterSujetACommission(Long commissionId, Long sujetId) {

        ProfesseurSujet sujet = sujetRepository.findById(sujetId)
                .orElseThrow(() -> new RuntimeException("Sujet introuvable"));

        ProfesseurCommission commission = commissionRepository.findById(commissionId)
                .orElseThrow(() -> new RuntimeException("Commission introuvable"));

        Professeur professeur = sujet.getProfesseur();

        if (!commissionProfRepository.existsByCommissionAndProfesseur(commission, professeur)) {
            ProfesseurCommissionProfesseurs m = new ProfesseurCommissionProfesseurs();
            m.setCommission(commission);
            m.setProfesseur(professeur);
            commissionProfRepository.save(m);
        }
    }

    // ================== 5️⃣ AJOUT MANUEL MEMBRE ==================
    @Override
    public void ajouterMembreCommission(Long commissionId, Long professeurId) {

        ProfesseurCommission commission = commissionRepository.findById(commissionId)
                .orElseThrow(() -> new RuntimeException("Commission introuvable"));

        Professeur professeur = professeurRepository.findById(professeurId)
                .orElseThrow(() -> new RuntimeException("Professeur introuvable"));

        List<ProfesseurCommissionProfesseurs> membres =
                commissionProfRepository.findByCommission(commission);

        if (membres.size() >= 5) {
            throw new RuntimeException("Commission complète (5 membres max)");
        }

        if (!commissionProfRepository.existsByCommissionAndProfesseur(commission, professeur)) {
            ProfesseurCommissionProfesseurs m = new ProfesseurCommissionProfesseurs();
            m.setCommission(commission);
            m.setProfesseur(professeur);
            commissionProfRepository.save(m);
        }
    }
}
