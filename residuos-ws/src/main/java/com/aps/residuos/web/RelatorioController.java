package com.aps.residuos.web;

import com.aps.residuos.repository.SetorGeracaoProjection;
import com.aps.residuos.service.RelatorioService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/relatorios")
public class RelatorioController {

    private final RelatorioService service;

    public RelatorioController(RelatorioService service) {
        this.service = service;
    }

    @GetMapping("/geracao-por-setor")
    public List<SetorGeracaoProjection> geracaoPorSetor(@RequestParam(required = false) Integer mes,
                                                        @RequestParam(required = false) Integer ano) {
        return service.geracaoPorSetor(mes, ano);
    }

    @GetMapping("/destinacao")
    public RelatorioService.Destinacao destinacao(@RequestParam(required = false) Integer mes,
                                                  @RequestParam(required = false) Integer ano) {
        return service.destinacao(mes, ano);
    }

    @GetMapping("/desvio-aterro")
    public RelatorioService.DesvioAterro desvioAterro(@RequestParam(required = false) Integer mes,
                                                      @RequestParam(required = false) Integer ano) {
        return service.desvioAterro(mes, ano);
    }

}