package com.projet.Edoctorat.Notification.Services;

import com.projet.Edoctorat.Notification.Models.Notification;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class NotificationService {
    
    private List<Notification> notifications = new ArrayList<>();
    private long idCounter = 1;
    
    // Créer notification pour candidat
    public Notification creerNotificationCandidat(String titre, String message, 
                                                Long candidatId, String type) {
        Notification notif = new Notification();
        notif.setId(idCounter++);
        notif.setTitre(titre);
        notif.setMessage(message);
        notif.setType(type);
        notif.setDestinataireId(candidatId);
        notif.setDestinataireRole("CANDIDAT");
        notif.setDateCreation(java.time.LocalDateTime.now());
        notifications.add(notif);
        
        System.out.println("🔔 Notification créée: " + titre + " pour candidat " + candidatId);
        return notif;
    }
    
    // Créer notification pour professeur
    public Notification creerNotificationProfesseur(String titre, String message,
                                                   Long professeurId, String type) {
        Notification notif = new Notification();
        notif.setId(idCounter++);
        notif.setTitre(titre);
        notif.setMessage(message);
        notif.setType(type);
        notif.setDestinataireId(professeurId);
        notif.setDestinataireRole("PROFESSEUR");
        notif.setDateCreation(java.time.LocalDateTime.now());
        notifications.add(notif);
        
        System.out.println("🔔 Notification créée: " + titre + " pour professeur " + professeurId);
        return notif;
    }
    
    // Notifications non lues pour utilisateur
    public List<Notification> getNotificationsNonLues(Long userId, String role) {
        return notifications.stream()
            .filter(n -> n.getDestinataireId().equals(userId))
            .filter(n -> n.getDestinataireRole().equals(role))
            .filter(n -> n.getStatut() == Notification.StatutNotification.NON_LUE)
            .sorted((n1, n2) -> n2.getDateCreation().compareTo(n1.getDateCreation()))
            .collect(Collectors.toList());
    }
    
    // Marquer comme lue
    public void marquerCommeLue(Long notificationId) {
        notifications.stream()
            .filter(n -> n.getId().equals(notificationId))
            .findFirst()
            .ifPresent(n -> {
                n.setStatut(Notification.StatutNotification.LUE);
                n.setDateLecture(java.time.LocalDateTime.now());
                System.out.println("✅ Notification " + notificationId + " marquée comme lue");
            });
    }
    
    // Stats notifications
    public Map<String, Object> getStatistiquesNotifications(Long userId, String role) {
        Map<String, Object> stats = new HashMap<>();
        
        List<Notification> userNotifs = notifications.stream()
            .filter(n -> n.getDestinataireId().equals(userId))
            .filter(n -> n.getDestinataireRole().equals(role))
            .collect(Collectors.toList());
        
        stats.put("total", userNotifs.size());
        stats.put("nonLues", userNotifs.stream()
            .filter(n -> n.getStatut() == Notification.StatutNotification.NON_LUE)
            .count());
        stats.put("importantes", userNotifs.stream()
            .filter(n -> n.getImportante() != null && n.getImportante())
            .count());
        
        return stats;
    }
    
    // Notifications par type
    public List<Notification> getNotificationsParType(Long userId, String role, String type) {
        return notifications.stream()
            .filter(n -> n.getDestinataireId().equals(userId))
            .filter(n -> n.getDestinataireRole().equals(role))
            .filter(n -> n.getType().equals(type))
            .sorted((n1, n2) -> n2.getDateCreation().compareTo(n1.getDateCreation()))
            .collect(Collectors.toList());
    }
}