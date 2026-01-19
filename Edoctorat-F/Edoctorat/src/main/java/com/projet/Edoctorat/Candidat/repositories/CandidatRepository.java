package com.projet.Edoctorat.Candidat.repositories;
import java.util.Optional;
import com.projet.Edoctorat.Candidat.models.CandidatModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CandidatRepository extends JpaRepository<CandidatModel, Long> {
    Optional<CandidatModel> findByCin(String cin);

    Optional<CandidatModel> findByCne(String cne);
    
    Optional<CandidatModel> findByUserId(Integer userId); // NEW
}
