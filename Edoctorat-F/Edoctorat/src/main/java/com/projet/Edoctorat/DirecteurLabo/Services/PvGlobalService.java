package com.projet.Edoctorat.DirecteurLabo.Services;

import com.projet.Edoctorat.Professeur.Models.ProfesseurExaminer;
import com.projet.Edoctorat.Professeur.Repositories.ProfesseurExaminerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PvGlobalService {

    private final ProfesseurExaminerRepository examinerRepository;

    public ResponseEntity<byte[]> genererPvGlobal() {

        List<ProfesseurExaminer> examens = examinerRepository.findAll();

        StringBuilder pv = new StringBuilder();
        pv.append("PV GLOBAL - DIRECTEUR DE LABO\n\n");

        for (ProfesseurExaminer e : examens) {
            pv.append("Commission: ").append(e.getCommission().getId())
                    .append(" | Sujet: ").append(e.getSujet().getId())
                    .append(" | Candidat: ").append(e.getCandidat().getId())
                    .append(" | Décision: ").append(e.getDecision())
                    .append(" | Dossier: ").append(e.getNoteDossier())
                    .append(" | Entretien: ").append(e.getNoteEntretien())
                    .append("\n");
        }

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=pv_global.txt")
                .body(pv.toString().getBytes());
    }
}
