package com.projet.Edoctorat.Candidat.repositories;

import com.projet.Edoctorat.Candidat.models.CandidatAnnexe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CandidatAnnexeRepository
        extends JpaRepository<CandidatAnnexe, Long> {

    boolean existsByDiplome_IdAndTypeAnnexe(Long diplomeId, String typeAnnexe);

}
