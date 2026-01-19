package com.projet.Edoctorat.DirecteurLabo.Controllers;

import com.projet.Edoctorat.DirecteurLabo.Services.PvGlobalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/directeur-labo/pv")
@RequiredArgsConstructor
@CrossOrigin("*")
public class PvGlobalController {

    private final PvGlobalService service;

    @GetMapping("/global")
    public ResponseEntity<byte[]> pvGlobal() {
        return service.genererPvGlobal();
    }
}
