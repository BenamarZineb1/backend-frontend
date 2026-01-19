package com.projet.Edoctorat.Candidat.services;

import com.projet.Edoctorat.Candidat.models.CandidatModel;
import com.projet.Edoctorat.Candidat.models.CandidatUpdateDTO;
import com.projet.Edoctorat.Candidat.repositories.CandidatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CandidatService {

    private final CandidatRepository candidatRepository;

    // ================= CRUD =================

    // Créer ou mettre à jour un candidat
    public CandidatModel create(CandidatModel candidat) {
        return candidatRepository.save(candidat);
    }

    // Récupérer tous les candidats
    public List<CandidatModel> getAll() {
        return candidatRepository.findAll();
    }

    // Récupérer par ID
    public CandidatModel getById(Long id) {
        return candidatRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Candidat introuvable avec ID : " + id));
    }

    // Récupérer par CNE
    public CandidatModel getByCne(String cne) {
        return candidatRepository.findByCne(cne)
                .orElseThrow(() ->
                        new RuntimeException("Candidat introuvable avec CNE : " + cne));
    }

    // ✅ NOUVEAU : Récupérer par CIN (lecture seule pour professeur)
    public CandidatModel getByCin(String cin) {
        return candidatRepository.findByCin(cin)
                .orElseThrow(() ->
                        new RuntimeException("Candidat introuvable avec CIN : " + cin));
    }

    // Supprimer un candidat
    public void delete(Long id) {
        if (!candidatRepository.existsById(id)) {
            throw new RuntimeException("Impossible de supprimer : candidat inexistant");
        }
        candidatRepository.deleteById(id);
    }
    
    // Récupérer par user_id
    public Optional<CandidatModel> getByUserId(Integer userId) {
        return candidatRepository.findByUserId(userId);
    }
    
    // Récupérer le profil du user courant
    public CandidatModel getCurrentUserProfile(Integer userId) {
        return getByUserId(userId)
            .orElseThrow(() -> new RuntimeException("Profil candidat non trouvé pour user_id: " + userId));
    }
    
    // Mettre à jour le profil candidat
    public CandidatModel updateProfile(Integer userId, CandidatUpdateDTO updates) {
        CandidatModel candidat = getByUserId(userId)
            .orElseThrow(() -> new RuntimeException("Candidat non trouvé pour user_id: " + userId));
        
        // Appliquer les mises à jour
        if (updates.getCin() != null) candidat.setCin(updates.getCin());
        if (updates.getNomCandidatAr() != null) candidat.setNomCandidatAr(updates.getNomCandidatAr());
        if (updates.getPrenomCandidatAr() != null) candidat.setPrenomCandidatAr(updates.getPrenomCandidatAr());
        if (updates.getAdresse() != null) candidat.setAdresse(updates.getAdresse());
        if (updates.getSexe() != null) candidat.setSexe(updates.getSexe());
        if (updates.getVilleDeNaissance() != null) candidat.setVilleDeNaissance(updates.getVilleDeNaissance());
        if (updates.getVilleDeNaissanceAr() != null) candidat.setVilleDeNaissanceAr(updates.getVilleDeNaissanceAr());
        if (updates.getDateDeNaissance() != null) {
            try {
                candidat.setDateDeNaissance(java.sql.Date.valueOf(updates.getDateDeNaissance()));
            } catch (Exception e) {
                // Ignorer date invalide
            }
        }
        if (updates.getTelCandidat() != null) candidat.setTelCandidat(updates.getTelCandidat());
        if (updates.getSituation_familiale() != null) candidat.setSituation_familiale(updates.getSituation_familiale());
        if (updates.getFonctionaire() != null) candidat.setFonctionaire(updates.getFonctionaire());
        
        return candidatRepository.save(candidat);
    }
}
