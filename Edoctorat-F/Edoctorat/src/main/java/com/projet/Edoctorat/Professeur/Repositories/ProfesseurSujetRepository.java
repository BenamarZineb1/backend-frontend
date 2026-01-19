package com.projet.Edoctorat.Professeur.Repositories;

import com.projet.Edoctorat.Professeur.Models.ProfesseurSujet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProfesseurSujetRepository extends JpaRepository<ProfesseurSujet, Long> {

    List<ProfesseurSujet> findByProfesseurId(Long professeurId);

    // 🔹 Pour le Directeur de Pôle
    List<ProfesseurSujet> findByPublierFalse();

    long countByProfesseurIdOrCoDirecteurId(Long professeurId, Long coDirecteurId);
}
