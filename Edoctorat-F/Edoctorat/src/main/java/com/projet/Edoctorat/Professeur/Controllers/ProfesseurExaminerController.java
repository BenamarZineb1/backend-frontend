package com.projet.Edoctorat.Professeur.Controllers;

import com.projet.Edoctorat.Professeur.Models.ExaminerDecisionRequest;
import com.projet.Edoctorat.Professeur.Models.ProfesseurExaminer;
import com.projet.Edoctorat.Professeur.Models.PvProfesseur;
import com.projet.Edoctorat.Professeur.Services.ProfesseurExaminerService;
import com.projet.Edoctorat.Professeur.Services.PvProfesseurService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/examiner")
@RequiredArgsConstructor
@CrossOrigin("*")
public class ProfesseurExaminerController {

    private final ProfesseurExaminerService examinerService;
    private final PvProfesseurService pvService;

    // Examiner : saisir décision + notes
    @PutMapping("/evaluer")
    public ProfesseurExaminer evaluer(@RequestBody ExaminerDecisionRequest request) {
        return examinerService.evaluerCandidat(request);
    }

    // Examiner : voir ses candidats
    @GetMapping("/commission/{commissionId}")
    public List<ProfesseurExaminer> mesCandidats(@PathVariable Long commissionId) {
        return examinerService.getEvaluationsParCommission(commissionId);
    }

    @GetMapping("/pv/{commissionId}")
    public byte[] genererPv(@PathVariable Long commissionId) {
        // ⚡ appeler la bonne méthode
        return pvService.genererPvProfesseur(commissionId)
                .toString()
                .getBytes();
    }

    // Envoyer PV au directeur de labo
    @PostMapping("/envoyer-pv/{commissionId}")
    public String envoyerPv(@PathVariable Long commissionId) {
        examinerService.envoyerPvAuDirecteur(commissionId);
        return "PV envoyé au directeur de labo";
    }

    // Directeur de labo : PV global
    @GetMapping("/pv-global")
    public List<ProfesseurExaminer> pvGlobal() {
        return examinerService.getPvGlobal();
    }
}
