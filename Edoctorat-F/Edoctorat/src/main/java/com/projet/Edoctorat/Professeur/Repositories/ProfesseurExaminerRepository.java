package com.projet.Edoctorat.Professeur.Repositories;

import com.projet.Edoctorat.Professeur.Models.ProfesseurExaminer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProfesseurExaminerRepository
        extends JpaRepository<ProfesseurExaminer, Long> {

    // Tous les candidats évalués par une commission
    List<ProfesseurExaminer> findByCommissionId(Long commissionId);

    // Candidats par sujet
    List<ProfesseurExaminer> findBySujetId(Long sujetId);

    // Candidats convoqués pour une commission + sujet
    List<ProfesseurExaminer> findByCommissionIdAndSujetId(
            Long commissionId,
            Long sujetId
    );

    // PV envoyés au directeur de labo
    List<ProfesseurExaminer> findByPublierTrue();
}
