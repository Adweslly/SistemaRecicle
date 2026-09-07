package com.aps.residuos.service;

import com.aps.residuos.domain.PontoColeta;
import com.aps.residuos.repository.PontoColetaRepository;
import com.aps.residuos.web.exception.ApiException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PontoColetaService {

    private final PontoColetaRepository repository;
    private final SetorService setorService;

    public PontoColetaService(PontoColetaRepository repository, SetorService setorService) {
        this.repository = repository;
        this.setorService = setorService;
    }

    public List<PontoColeta> listar(Long setorId) {
        if (setorId != null) {
            return repository.findBySetorId(setorId);
        }
        return repository.findAll();
    }

    public PontoColeta buscar(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND,
                        "Ponto de coleta nao encontrado com id " + id));
    }

    @Transactional
    public PontoColeta criar(PontoColeta dados) {
        if (dados.getCapacidadeKg().signum() <= 0) {
            throw new ApiException(HttpStatus.BAD_REQUEST,
                    "A capacidade deve ser maior que zero");
        }
        return repository.save(new PontoColeta(
                dados.getNome(), dados.getTipoResiduoAceito(),
                dados.getCapacidadeKg(), setorService.buscar(dados.getSetor().getId())));
    }

    @Transactional
    public PontoColeta atualizar(Long id, PontoColeta dados) {
        PontoColeta ponto = buscar(id);
        ponto.setNome(dados.getNome());
        ponto.setTipoResiduoAceito(dados.getTipoResiduoAceito());
        ponto.setCapacidadeKg(dados.getCapacidadeKg());
        ponto.setSetor(setorService.buscar(dados.getSetor().getId()));
        return repository.save(ponto);
    }

    @Transactional
    public void excluir(Long id) {
        PontoColeta ponto = buscar(id);
        repository.delete(ponto);
    }

}