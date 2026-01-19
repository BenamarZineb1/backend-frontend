package com.projet.Edoctorat.DirecteurPole.Models;

import jakarta.persistence.*;
import lombok.Data;
import java.util.Date;

@Entity
@Table(name = "directeur_pole_calendrier")
@Data
public class DirecteurPoleCalendrier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private ActionCalendrier action;

    @Enumerated(EnumType.STRING)
    private PourCalendrier pour;

    @Temporal(TemporalType.DATE)
    private Date dateDebut;

    @Temporal(TemporalType.DATE)
    private Date dateFin;
}
