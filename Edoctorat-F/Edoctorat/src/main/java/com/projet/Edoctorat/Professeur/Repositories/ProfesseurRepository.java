package com.projet.Edoctorat.Professeur.Repositories;

import com.projet.Edoctorat.Professeur.Models.Professeur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface ProfesseurRepository extends JpaRepository<Professeur, Long> {

    // Récupérer un professeur par CIN
    Optional<Professeur> findByCin(String cin);

    // Récupérer un professeur par NUMSOM
    Optional<Professeur> findByNumSOM(String numSOM);

    // 🔴 INDISPENSABLE : Pour trouver le prof via son compte de connexion
    // Attention : Vérifie le type de l'ID dans AuthUser. Si c'est Long, mets Long ici. Si c'est Integer, laisse Integer.
    Optional<Professeur> findByUserId(Integer userId);
}