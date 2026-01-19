package com.projet.Edoctorat.DirecteurPole.Repositories;
import com.projet.Edoctorat.DirecteurPole.Models.ActionCalendrier;
import com.projet.Edoctorat.DirecteurPole.Models.DirecteurPoleCalendrier;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Date;
public interface DirecteurPoleCalendrierRepository
        extends JpaRepository<DirecteurPoleCalendrier, Long> {
    boolean existsByActionAndDateDebutLessThanEqualAndDateFinGreaterThanEqual(
            ActionCalendrier action,
            Date dateDebut,
            Date dateFin
    );
}
