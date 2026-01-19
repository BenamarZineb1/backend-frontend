package com.projet.Edoctorat.Professeur.Controllers;

import org.springframework.web.bind.annotation.*;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

@RestController
@RequestMapping("/prof")
public class ProfesseurController {

    // Ici tu vas ajouter tes méthodes, par exemple :
    @GetMapping("/me")
    public String getMonProfil(@AuthenticationPrincipal OAuth2User principal) {
        String email = principal.getAttribute("email");
        return "Email connecté : " + email;
    }
}
