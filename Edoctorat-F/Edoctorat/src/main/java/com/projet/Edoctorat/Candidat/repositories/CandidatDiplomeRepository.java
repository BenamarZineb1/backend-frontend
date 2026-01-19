package com.projet.Edoctorat.Candidat.repositories;

import com.projet.Edoctorat.Candidat.models.CandidatDiplome;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CandidatDiplomeRepository extends JpaRepository<CandidatDiplome, Long> {
    List<CandidatDiplome> findByCandidatId(Long candidatId);
}
