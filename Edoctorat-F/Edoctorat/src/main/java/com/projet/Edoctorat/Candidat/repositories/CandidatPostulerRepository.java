package com.projet.Edoctorat.Candidat.repositories;

import com.projet.Edoctorat.Candidat.models.CandidatPostuler;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CandidatPostulerRepository
        extends JpaRepository<CandidatPostuler, Long> {

    List<CandidatPostuler> findByCandidatId(Long candidatId);
}
