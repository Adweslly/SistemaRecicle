package com.aps.residuos.web;

import com.aps.residuos.domain.Residuo;
import com.aps.residuos.service.ResiduoService;
import com.aps.residuos.web.dto.ResiduoRequest;
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
@RequestMapping("/residuos")
public class ResiduoController {

    private final ResiduoService service;

    public ResiduoController(ResiduoService service) {
        this.service = service;
    }

    @GetMapping
    public List<Residuo> listar(@RequestParam(required = false) Long setorId,
                                @RequestParam(required = false) String tipo,
                                @RequestParam(required = false) Integer mes,
                                @RequestParam(required = false) Integer ano) {
        return service.listar(setorId, tipo, mes, ano);
    }

    @GetMapping("/{id}")
    public Residuo buscar(@PathVariable Long id) {
        return service.buscar(id);
    }

    @PostMapping
    public ResponseEntity<Residuo> criar(@Valid @RequestBody ResiduoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.criar(request));
    }

    @PutMapping("/{id}")
    public Residuo atualizar(@PathVariable Long id, @Valid @RequestBody ResiduoRequest request) {
        return service.atualizar(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }

}