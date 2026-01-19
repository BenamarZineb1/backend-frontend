package com.projet.Edoctorat.Candidat.services;

import com.projet.Edoctorat.Candidat.models.CandidatDiplome;
import com.projet.Edoctorat.Candidat.repositories.CandidatDiplomeRepository;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CandidatDiplomeService {

    private final CandidatDiplomeRepository diplomeRepository;

    public CandidatDiplome addDiplome(CandidatDiplome diplome) {
        return diplomeRepository.save(diplome);
    }

    // On récupère par id du candidat pour simplifier
    public List<CandidatDiplome> getByCandidatId(Long candidatId) {
        return diplomeRepository.findByCandidatId(candidatId);
    }
}
