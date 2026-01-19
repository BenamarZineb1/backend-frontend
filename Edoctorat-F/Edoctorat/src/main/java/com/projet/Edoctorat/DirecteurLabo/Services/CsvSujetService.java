package com.projet.Edoctorat.DirecteurLabo.Services;

import com.projet.Edoctorat.Professeur.Models.Professeur;
import com.projet.Edoctorat.Professeur.Models.ProfesseurSujet;
import com.projet.Edoctorat.Professeur.Repositories.ProfesseurRepository;
import com.projet.Edoctorat.Professeur.Repositories.ProfesseurSujetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;

@Service
@RequiredArgsConstructor
public class CsvSujetService {

    private final ProfesseurSujetRepository repository;
    private final ProfesseurRepository professeurRepository;

    public void importSujetsFromCsv(MultipartFile file) {

        try (BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()))) {

            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");

                ProfesseurSujet sujet = new ProfesseurSujet();
                sujet.setTitre(data[0]);
                sujet.setDescription(data[1]);

                Long profId = Long.parseLong(data[2]);
                Professeur professeur = professeurRepository.findById(profId)
                        .orElseThrow(() -> new RuntimeException("Professeur introuvable"));
                sujet.setProfesseur(professeur); // ✅ corrigé

                repository.save(sujet);
            }

        } catch (Exception e) {
            throw new RuntimeException("Erreur CSV : " + e.getMessage());
        }
    }
}
