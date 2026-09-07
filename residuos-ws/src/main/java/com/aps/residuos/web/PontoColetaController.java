package com.aps.residuos.web;

import com.aps.residuos.domain.PontoColeta;
import com.aps.residuos.service.PontoColetaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/pontos")
public class PontoColetaController {

    private final PontoColetaService service;

    public PontoColetaController(PontoColetaService service) {
        this.service = service;
    }

    @GetMapping
    public List<PontoColeta> listar(@RequestParam(required = false) Long setorId) {
        return service.listar(setorId);
    }

    @GetMapping("/{id}")
    public PontoColeta buscar(@PathVariable Long id) {
        return service.buscar(id);
    }

    @PostMapping
    public ResponseEntity<PontoColeta> criar(@Valid @RequestBody PontoColeta dados) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.criar(dados));
    }

    @PutMapping("/{id}")
    public PontoColeta atualizar(@PathVariable Long id, @Valid @RequestBody PontoColeta dados) {
        return service.atualizar(id, dados);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }

}