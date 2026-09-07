package com.aps.residuos.service;

import com.aps.residuos.domain.Reciclador;
import com.aps.residuos.repository.RecicladorRepository;
import com.aps.residuos.web.exception.ApiException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RecicladorService {

    private final RecicladorRepository repository;

    public RecicladorService(RecicladorRepository repository) {
        this.repository = repository;
    }

    public List<Reciclador> listar(boolean apenasAtivos) {
        return apenasAtivos ? repository.findByAtivoTrue() : repository.findAll();
    }

    public Reciclador buscar(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND,
                        "Reciclador nao encontrado com id " + id));
    }

    @Transactional
    public Reciclador criar(Reciclador dados) {
        if (repository.existsByCnpj(dados.getCnpj())) {
            throw new ApiException(HttpStatus.CONFLICT,
                    "Ja existe um reciclador cadastrado com esse CNPJ");
        }
        return repository.save(new Reciclador(
                dados.getNome(), dados.getTipo(), dados.getCnpj(), dados.getMateriaisAceitos()));
    }

    @Transactional
    public Reciclador atualizar(Long id, Reciclador dados) {
        Reciclador reciclador = buscar(id);
        reciclador.setNome(dados.getNome());
        reciclador.setTipo(dados.getTipo());
        reciclador.setCnpj(dados.getCnpj());
        reciclador.setMateriaisAceitos(dados.getMateriaisAceitos());
        reciclador.setAtivo(dados.isAtivo());
        return repository.save(reciclador);
    }

    @Transactional
    public void excluir(Long id) {
        Reciclador reciclador = buscar(id);
        if (!reciclador.isAtivo()) {
            throw new ApiException(HttpStatus.CONFLICT,
                    "O reciclador ja esta inativo");
        }
        reciclador.setAtivo(false);
        repository.save(reciclador);
    }

}