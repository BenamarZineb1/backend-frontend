package com.projet.Edoctorat.Candidat.services;

import com.projet.Edoctorat.Candidat.models.CandidatPostuler;
import com.projet.Edoctorat.Candidat.repositories.CandidatPostulerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CandidatPostulerService {
    private final CandidatPostulerRepository repository;

    public CandidatPostulerService(CandidatPostulerRepository repository) {
        this.repository = repository;
    }

    public List<CandidatPostuler> getByCandidatId(Long id) {
        return repository.findByCandidatId(id);
    }

    public CandidatPostuler save(CandidatPostuler p) {
        return repository.save(p);
    }
}
