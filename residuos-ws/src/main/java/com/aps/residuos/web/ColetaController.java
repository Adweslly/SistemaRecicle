package com.aps.residuos.web;

import com.aps.residuos.domain.Coleta;
import com.aps.residuos.domain.StatusColeta;
import com.aps.residuos.service.ColetaService;
import com.aps.residuos.web.dto.ColetaRequest;
import com.aps.residuos.web.dto.ColetaStatusRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/coletas")
public class ColetaController {

    private final ColetaService service;

    public ColetaController(ColetaService service) {
        this.service = service;
    }

    @GetMapping
    public List<Coleta> listar(@RequestParam(required = false) StatusColeta status,
                               @RequestParam(required = false) Long pontoColetaId,
                               @RequestParam(required = false) Integer mes,
                               @RequestParam(required = false) Integer ano) {
        return service.listar(status, pontoColetaId, mes, ano);
    }

    @GetMapping("/{id}")
    public Coleta buscar(@PathVariable Long id) {
        return service.buscar(id);
    }

    @PostMapping
    public ResponseEntity<Coleta> solicitar(@Valid @RequestBody ColetaRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.solicitar(request));
    }

    @PatchMapping("/{id}/status")
    public Coleta mudarStatus(@PathVariable Long id,
                              @Valid @RequestBody ColetaStatusRequest request) {
        return service.mudarStatus(id, request);
    }

}