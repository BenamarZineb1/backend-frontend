package com.projet.Edoctorat.Candidat.repositories;

import com.projet.Edoctorat.Candidat.models.CandidatNotification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CandidatNotificationRepository
        extends JpaRepository<CandidatNotification, Long> {

    boolean existsByCommissionIdAndSujetId(Long commissionId, Long sujetId);

    // si besoin plus tard
    java.util.List<CandidatNotification> findByCandidat_Id(Long candidatId);
}
