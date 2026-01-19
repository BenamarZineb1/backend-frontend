package com.projet.Edoctorat.Scolarite.Services;

import com.projet.Edoctorat.Candidat.models.CandidatModel;
import com.projet.Edoctorat.Candidat.repositories.*;
import com.projet.Edoctorat.Professeur.Models.ProfesseurInscription;
import com.projet.Edoctorat.Professeur.Repositories.ProfesseurInscriptionRepository;
import com.projet.Edoctorat.Scolarite.Dto.DossierDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.*;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ScolariteServiceImpl implements ScolariteService {
    
    private final CandidatRepository candidatRepo;
    private final ProfesseurInscriptionRepository inscriptionRepo;
    private final CandidatPostulerRepository postulerRepo;
    private final CandidatAnnexeRepository annexeRepo;
    
    @Override
    public List<DossierDto> getTousLesDossiers() {
        return candidatRepo.findAll()
                .stream()
                .map(this::buildDossier)
                .toList();
    }
    
    @Override
    public DossierDto getDossierParCne(String cne) {
        CandidatModel candidat = candidatRepo.findByCne(cne)
                .orElseThrow(() -> new RuntimeException("Candidat introuvable"));
        return buildDossier(candidat);
    }
    
    private DossierDto buildDossier(CandidatModel c) {
        DossierDto dto = new DossierDto();
        dto.setCandidatId(c.getId());
        dto.setCne(c.getCne());
        dto.setNom(c.getNomCandidatAr());
        dto.setPrenom(c.getPrenomCandidatAr());
        
        // 📄 Fiche de dépôt
        List<?> fiches = postulerRepo.findByCandidatId(c.getId());
        dto.setFicheDepot(fiches != null && !fiches.isEmpty());
        
        // 🧾 Reçu de dépôt
        inscriptionRepo.findByCandidatId(c.getId())
                .ifPresent(i -> {
                    dto.setRecuDepot(true);
                    dto.setValide(i.isValider());
                });
        
        // 💰 Demande de bourse
        dto.setDemandeBourse(
                annexeRepo.existsByDiplome_IdAndTypeAnnexe(c.getId(), "BOURSE")
        );
        
        return dto;
    }
    
    @Override
    public void validerDossier(Long candidatId) {
        var inscription = inscriptionRepo.findByCandidatId(candidatId)
                .orElseThrow(() -> new RuntimeException("Aucun dépôt trouvé"));
        inscription.setValider(true);
        inscriptionRepo.save(inscription);
        System.out.println("✅ Dossier " + candidatId + " validé par scolarité");
    }
    
    @Override
    public ProfesseurInscription commenterDossier(Long candidatId, String commentaire) {
        ProfesseurInscription inscription = inscriptionRepo.findByCandidatId(candidatId)
                .orElseThrow(() -> new RuntimeException("Aucun dossier trouvé pour ce candidat"));
        
        inscription.setRemarque(commentaire);
        ProfesseurInscription saved = inscriptionRepo.save(inscription);
        System.out.println("💬 Commentaire ajouté au dossier " + candidatId);
        return saved;
    }
    
    // Méthodes avancées
    
    @Override
    public Map<String, Object> getStatistiques() {
        Map<String, Object> stats = new HashMap<>();
        
        List<DossierDto> dossiers = getTousLesDossiers();
        
        stats.put("totalDossiers", dossiers.size());
        stats.put("dossiersComplets", dossiers.stream().filter(DossierDto::isFicheDepot).count());
        stats.put("dossiersValides", dossiers.stream().filter(DossierDto::isValide).count());
        stats.put("demandesBourse", dossiers.stream().filter(DossierDto::isDemandeBourse).count());
        
        // Pourcentage validation
        double tauxValidation = dossiers.isEmpty() ? 0 : 
            (double) dossiers.stream().filter(DossierDto::isValide).count() / dossiers.size() * 100;
        stats.put("tauxValidation", Math.round(tauxValidation * 100.0) / 100.0);
        
        System.out.println("📊 Statistiques scolarité générées");
        return stats;
    }
    
    @Override
    public List<DossierDto> rechercherDossiers(String nom, String cne, Boolean valide) {
        List<CandidatModel> candidats = candidatRepo.findAll();
        List<DossierDto> resultats = new ArrayList<>();
        
        for (CandidatModel candidat : candidats) {
            DossierDto dossier = buildDossier(candidat);
            
            boolean match = true;
            
            // Filtrer par nom
            if (nom != null && !nom.isEmpty()) {
                String nomComplet = (dossier.getNom() + " " + dossier.getPrenom()).toLowerCase();
                if (!nomComplet.contains(nom.toLowerCase())) {
                    match = false;
                }
            }
            
            // Filtrer par CNE
            if (cne != null && !cne.isEmpty()) {
                if (!dossier.getCne().toLowerCase().contains(cne.toLowerCase())) {
                    match = false;
                }
            }
            
            // Filtrer par validation
            if (valide != null) {
                if (dossier.isValide() != valide) {
                    match = false;
                }
            }
            
            if (match) {
                resultats.add(dossier);
            }
        }
        
        System.out.println("🔍 Recherche scolarité: " + resultats.size() + " résultats trouvés");
        return resultats;
    }
    
    @Override
    public String exporterDossiers() {
        List<DossierDto> dossiers = getTousLesDossiers();
        StringBuilder csv = new StringBuilder();
        
        // En-têtes
        csv.append("CNE,Nom,Prenom,Fiche_Depot,Recu_Depot,Valide,Demande_Bourse\n");
        
        // Données
        for (DossierDto dossier : dossiers) {
            csv.append(String.format("%s,%s,%s,%s,%s,%s,%s\n",
                dossier.getCne(),
                dossier.getNom(),
                dossier.getPrenom(),
                dossier.isFicheDepot(),
                dossier.isRecuDepot(),
                dossier.isValide(),
                dossier.isDemandeBourse()
            ));
        }
        
        System.out.println("📤 Export CSV scolarité effectué (" + dossiers.size() + " dossiers)");
        return csv.toString();
    }
    
    @Override
    public List<Map<String, Object>> getHistoriqueValidations() {
        List<Map<String, Object>> historique = new ArrayList<>();
        
        // Simulation d'historique (en réalité viendrait de la base de données)
        Map<String, Object> entry1 = new HashMap<>();
        entry1.put("date", LocalDateTime.now().minusDays(1));
        entry1.put("candidatId", 1L);
        entry1.put("cne", "R123456789");
        entry1.put("action", "VALIDATION");
        entry1.put("utilisateur", "Scolarité");
        historique.add(entry1);
        
        Map<String, Object> entry2 = new HashMap<>();
        entry2.put("date", LocalDateTime.now().minusDays(2));
        entry2.put("candidatId", 2L);
        entry2.put("cne", "R987654321");
        entry2.put("action", "COMMENTAIRE");
        entry2.put("utilisateur", "Scolarité");
        historique.add(entry2);
        
        System.out.println("📜 Historique validations scolarité récupéré");
        return historique;
    }
}