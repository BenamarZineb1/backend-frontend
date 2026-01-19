package com.projet.Edoctorat.Professeur.Repositories;

import com.projet.Edoctorat.Professeur.Models.ProfesseurInscription;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfesseurInscriptionRepository
        extends JpaRepository<ProfesseurInscription, Long> {

    Optional<ProfesseurInscription> findByCandidatId(Long candidatId);
}
