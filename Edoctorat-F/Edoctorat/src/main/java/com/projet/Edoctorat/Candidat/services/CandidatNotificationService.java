package com.projet.Edoctorat.Candidat.services;

import com.projet.Edoctorat.Candidat.models.CandidatNotification;
import com.projet.Edoctorat.Candidat.repositories.CandidatNotificationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CandidatNotificationService {

    private final CandidatNotificationRepository repository;

    public CandidatNotificationService(CandidatNotificationRepository repository) {
        this.repository = repository;
    }

    public List<CandidatNotification> getByCandidatId(Long id) {
        return repository.findByCandidat_Id(id);
    }
}
