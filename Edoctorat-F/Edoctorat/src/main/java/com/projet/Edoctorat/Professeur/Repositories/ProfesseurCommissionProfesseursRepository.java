package com.projet.Edoctorat.Professeur.Repositories;

import com.projet.Edoctorat.Professeur.Models.Professeur;
import com.projet.Edoctorat.Professeur.Models.ProfesseurCommission;
import com.projet.Edoctorat.Professeur.Models.ProfesseurCommissionProfesseurs;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProfesseurCommissionProfesseursRepository
        extends JpaRepository<ProfesseurCommissionProfesseurs, Long> {

    // Liste tous les membres d'une commission (par entité)
    List<ProfesseurCommissionProfesseurs> findByCommission(ProfesseurCommission commission);

    // Vérifie si un professeur est déjà dans une commission (par entité)
    boolean existsByCommissionAndProfesseur(ProfesseurCommission commission, Professeur professeur);
}
