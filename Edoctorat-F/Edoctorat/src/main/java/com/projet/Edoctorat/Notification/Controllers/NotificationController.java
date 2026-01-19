package com.projet.Edoctorat.Notification.Controllers;

import com.projet.Edoctorat.Notification.Models.Notification;
import com.projet.Edoctorat.Notification.Services.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
@CrossOrigin("*")
public class NotificationController {
    
    private final NotificationService notificationService;
    
    // Récupérer notifications non lues
    @GetMapping("/non-lues")
    public List<Notification> getNotificationsNonLues(
            @RequestParam Long userId,
            @RequestParam String role) {
        return notificationService.getNotificationsNonLues(userId, role);
    }
    
    // Marquer notification comme lue
    @PutMapping("/{id}/lire")
    public void marquerCommeLue(@PathVariable Long id) {
        notificationService.marquerCommeLue(id);
    }
    
    // Statistiques notifications
    @GetMapping("/stats")
    public Map<String, Object> getStatistiques(
            @RequestParam Long userId,
            @RequestParam String role) {
        return notificationService.getStatistiquesNotifications(userId, role);
    }
    
    // Notifications par type
    @GetMapping("/type/{type}")
    public List<Notification> getNotificationsParType(
            @PathVariable String type,
            @RequestParam Long userId,
            @RequestParam String role) {
        return notificationService.getNotificationsParType(userId, role, type);
    }
    
    // Créer notification test (pour démo)
    @PostMapping("/test/candidat")
    public Notification creerNotificationTestCandidat(
            @RequestParam String titre,
            @RequestParam String message,
            @RequestParam Long candidatId) {
        return notificationService.creerNotificationCandidat(titre, message, candidatId, "GENERAL");
    }
    
    @PostMapping("/test/professeur")
    public Notification creerNotificationTestProfesseur(
            @RequestParam String titre,
            @RequestParam String message,
            @RequestParam Long professeurId) {
        return notificationService.creerNotificationProfesseur(titre, message, professeurId, "GENERAL");
    }
}