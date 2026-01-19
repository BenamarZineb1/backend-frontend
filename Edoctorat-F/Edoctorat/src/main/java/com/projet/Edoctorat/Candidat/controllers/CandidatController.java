package com.projet.Edoctorat.Candidat.controllers;

import com.projet.Edoctorat.Auth.Models.AuthUser;
import com.projet.Edoctorat.Auth.Services.AuthUserService;
import com.projet.Edoctorat.Candidat.models.CandidatModel;
import com.projet.Edoctorat.Candidat.models.CandidatUpdateDTO;
import com.projet.Edoctorat.Candidat.services.CandidatService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.sql.Date;
import java.util.List;

@RestController
@RequestMapping("/candidat")
@RequiredArgsConstructor
@CrossOrigin("*")
public class CandidatController {

    private final CandidatService candidatService;
    private final AuthUserService authUserService; // NEW

    // Créer un candidat
    @PostMapping
    public CandidatModel create(@RequestBody CandidatModel candidat) {
        return candidatService.create(candidat);
    }

    // Modifier / compléter les informations personnelles
    @PutMapping("/{id}")
    public CandidatModel update(@PathVariable Long id,
                                @RequestBody CandidatModel candidat) {
        candidat.setId(id);
        return candidatService.create(candidat);
    }

    // Lister tous les candidats
    @GetMapping
    public List<CandidatModel> getAll() {
        return candidatService.getAll();
    }

    // Récupérer par ID
    @GetMapping("/id/{id}")
    public CandidatModel getById(@PathVariable Long id) {
        return candidatService.getById(id);
    }
    //recuperer par cin
    @GetMapping("/cin/{cin}")
    public CandidatModel getByCin(@PathVariable String cin) {
        return candidatService.getByCin(cin);
    }

    // Récupérer par CNE
    @GetMapping("/cne/{cne}")
    public CandidatModel getByCne(@PathVariable String cne) {
        return candidatService.getByCne(cne);
    }


    // Supprimer un candidat
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        candidatService.delete(id);
    }

    // Upload CV
    @PostMapping("/{id}/cv")
    public CandidatModel uploadCv(@PathVariable Long id,
                                  @RequestParam("file") MultipartFile file)
            throws Exception {

        File dossier = new File("uploads/cv");
        if (!dossier.exists()) dossier.mkdirs();

        String path = "uploads/cv/" + file.getOriginalFilename();
        file.transferTo(new File(path));

        CandidatModel candidat = candidatService.getById(id);
        candidat.setPathCv(path);

        return candidatService.create(candidat);
    }

    // Upload Photo
    @PostMapping("/{id}/photo")
    public CandidatModel uploadPhoto(@PathVariable Long id,
                                     @RequestParam("file") MultipartFile file)
            throws Exception {

        File dossier = new File("uploads/photo");
        if (!dossier.exists()) dossier.mkdirs();

        String path = "uploads/photo/" + file.getOriginalFilename();
        file.transferTo(new File(path));

        CandidatModel candidat = candidatService.getById(id);
        candidat.setPathPhoto(path);

        return candidatService.create(candidat);
    }

    // Get current user profile (/candidat/me)
    @GetMapping("/me")
    public CandidatModel getCurrentUserProfile(Authentication authentication) {
        try {
            // Get username from JWT token
            String username = authentication.getName();
            System.out.println("👤 Récupération profil pour: " + username);
            
            // Get real user ID from database
            Integer userId = authUserService.getUserIdByUsername(username);
            System.out.println("🆔 User ID trouvé: " + userId);
            
            // Get real profile from database
            CandidatModel profile = candidatService.getCurrentUserProfile(userId);
            
            System.out.println("✅ Profil récupéré: " + profile.getId() + 
                             ", Nom: " + profile.getNomCandidatFr() + 
                             ", Prénom: " + profile.getPrenomCandidatFr() +
                             ", CNE: " + profile.getCne());
            
            return profile;
            
        } catch (Exception e) {
            System.err.println("❌ Erreur récupération profil: " + e.getMessage());
            
            // Fallback to mock data if real data not found
            System.out.println("⚠️ Fallback vers données mock");
            CandidatModel mockProfile = new CandidatModel();
            mockProfile.setId(1L);
            mockProfile.setCne("R123456789");
            mockProfile.setCin("AB123456");
            mockProfile.setNomCandidatFr("Fallback");
            mockProfile.setPrenomCandidatFr("User");
            mockProfile.setNomCandidatAr("تيست");
            mockProfile.setPrenomCandidatAr("يوزر");
            mockProfile.setAdresse("Adresse par défaut");
            mockProfile.setSexe("Féminin");
            mockProfile.setVilleDeNaissance("Casablanca");
            mockProfile.setDateDeNaissance(Date.valueOf("1995-06-15"));
            mockProfile.setTelCandidat("+212612345678");
            mockProfile.setUserId(1);
            mockProfile.setFonctionaire(false);
            
            return mockProfile;
        }
    }
    
    // Update current user profile (/candidat/me) - REAL DATABASE UPDATE
    @PutMapping("/me")
    public CandidatModel updateCurrentUserProfile(@RequestBody CandidatUpdateDTO updates, Authentication authentication) {
        try {
            // Get username from JWT token
            String username = authentication.getName();
            System.out.println("🔄 Mise à jour profil pour: " + username);
            
            // Get real user ID from database
            Integer userId = authUserService.getUserIdByUsername(username);
            System.out.println("🆔 User ID pour update: " + userId);
            
            System.out.println("🔄 Données reçues: " + updates);
            
            // Call service to update in database
            CandidatModel updatedProfile = candidatService.updateProfile(userId, updates);
            
            System.out.println("✅ Profil mis à jour dans la base de données");
            return updatedProfile;
            
        } catch (Exception e) {
            System.err.println("❌ Erreur mise à jour profil: " + e.getMessage());
            throw e;
        }
    }
}
