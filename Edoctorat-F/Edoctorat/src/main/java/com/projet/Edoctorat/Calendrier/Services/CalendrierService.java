package com.projet.Edoctorat.Calendrier.Services;

import com.projet.Edoctorat.Calendrier.Models.EvenementCalendrier;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CalendrierService {
    
    private List<EvenementCalendrier> evenements = new ArrayList<>();
    private long idCounter = 1;
    
    public CalendrierService() {
        initialiserEvenementsTest();
    }
    
    private void initialiserEvenementsTest() {
        // Entretien futur
        EvenementCalendrier e1 = new EvenementCalendrier();
        e1.setId(idCounter++);
        e1.setTitre("Entretien candidature - Intelligence Artificielle");
        e1.setDescription("Entretien avec le candidat pour le sujet IA médicale");
        e1.setType("ENTRETIEN");
        e1.setDateDebut(LocalDateTime.now().plusDays(3).withHour(10).withMinute(0));
        e1.setDateFin(LocalDateTime.now().plusDays(3).withHour(11).withMinute(30));
        e1.setLieu("Salle B101 - Bâtiment principal");
        e1.setRolesConcernes("CANDIDAT,PROFESSEUR");
        e1.setParticipantsIds("1,2");
        evenements.add(e1);
        
        // Deadline dépôt
        EvenementCalendrier e2 = new EvenementCalendrier();
        e2.setId(idCounter++);
        e2.setTitre("Deadline dépôt dossiers");
        e2.setDescription("Fin de la période de dépôt des dossiers de candidature");
        e2.setType("DEADLINE");
        e2.setDateDebut(LocalDateTime.now().plusDays(10).withHour(23).withMinute(59));
        e2.setDateFin(LocalDateTime.now().plusDays(10).withHour(23).withMinute(59));
        e2.setRolesConcernes("CANDIDAT");
        evenements.add(e2);
        
        // Commission en cours
        EvenementCalendrier e3 = new EvenementCalendrier();
        e3.setId(idCounter++);
        e3.setTitre("Commission d'examen - Physique");
        e3.setDescription("Commission d'évaluation des candidatures en physique");
        e3.setType("COMMISSION");
        e3.setDateDebut(LocalDateTime.now().minusHours(1));
        e3.setDateFin(LocalDateTime.now().plusHours(2));
        e3.setLieu("Amphi A - Campus principal");
        e3.setRolesConcernes("PROFESSEUR");
        evenements.add(e3);
    }
    
    public List<EvenementCalendrier> getEvenementsFuturs() {
        return evenements.stream()
            .filter(EvenementCalendrier::estDansLeFutur)
            .sorted(Comparator.comparing(EvenementCalendrier::getDateDebut))
            .collect(Collectors.toList());
    }
    
    public List<EvenementCalendrier> getEvenementsParPeriode(LocalDateTime debut, LocalDateTime fin) {
        return evenements.stream()
            .filter(e -> e.getDateDebut().isAfter(debut) && e.getDateDebut().isBefore(fin))
            .sorted(Comparator.comparing(EvenementCalendrier::getDateDebut))
            .collect(Collectors.toList());
    }
    
    public List<EvenementCalendrier> getEvenementsParRole(String role) {
        return evenements.stream()
            .filter(e -> e.getRolesConcernes().contains(role))
            .sorted(Comparator.comparing(EvenementCalendrier::getDateDebut))
            .collect(Collectors.toList());
    }
    
    public List<EvenementCalendrier> getEvenementsParType(String type) {
        return evenements.stream()
            .filter(e -> e.getType().equals(type))
            .sorted(Comparator.comparing(EvenementCalendrier::getDateDebut))
            .collect(Collectors.toList());
    }
    
    public Map<String, Object> getStatistiquesCalendrier() {
        Map<String, Object> stats = new HashMap<>();
        
        stats.put("totalEvenements", evenements.size());
        stats.put("evenementsFuturs", evenements.stream().filter(EvenementCalendrier::estDansLeFutur).count());
        stats.put("evenementsEnCours", evenements.stream().filter(EvenementCalendrier::estEnCours).count());
        stats.put("types", evenements.stream().map(EvenementCalendrier::getType).distinct().count());
        
        return stats;
    }
    
    public EvenementCalendrier creerEvenement(EvenementCalendrier evenement) {
        evenement.setId(idCounter++);
        evenement.setStatut(EvenementCalendrier.StatutEvenement.PLANIFIE);
        evenements.add(evenement);
        
        System.out.println("📅 Événement créé: " + evenement.getTitre());
        return evenement;
    }
    
    public List<Map<String, Object>> getAgendaSimplifie(int jours) {
        LocalDateTime maintenant = LocalDateTime.now();
        LocalDateTime future = maintenant.plusDays(jours);
        
        return evenements.stream()
            .filter(e -> e.getDateDebut().isAfter(maintenant) && e.getDateDebut().isBefore(future))
            .sorted(Comparator.comparing(EvenementCalendrier::getDateDebut))
            .map(e -> {
                Map<String, Object> item = new HashMap<>();
                item.put("id", e.getId());
                item.put("titre", e.getTitre());
                item.put("date", e.getDateDebut().toString());
                item.put("type", e.getType());
                item.put("lieu", e.getLieu());
                return item;
            })
            .collect(Collectors.toList());
    }
}