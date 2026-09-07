package com.aps.residuos.service;

import com.aps.residuos.domain.Setor;
import com.aps.residuos.repository.SetorRepository;
import com.aps.residuos.web.exception.ApiException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SetorService {

    private final SetorRepository repository;

    public SetorService(SetorRepository repository) {
        this.repository = repository;
    }

    public List<Setor> listar() {
        return repository.findAll();
    }

    public Setor buscar(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND,
                        "Setor nao encontrado com id " + id));
    }

    @Transactional
    public Setor criar(Setor dados) {
        if (repository.existsByNomeIgnoringCase(dados.getNome())) {
            throw new ApiException(HttpStatus.CONFLICT,
                    "Ja existe um setor com o nome informado");
        }
        return repository.save(new Setor(dados.getNome(), dados.getDepartamento(),
                dados.getLocalizacao()));
    }

    @Transactional
    public Setor atualizar(Long id, Setor dados) {
        Setor setor = buscar(id);
        if (!setor.getNome().equalsIgnoreCase(dados.getNome())
                && repository.existsByNomeIgnoringCase(dados.getNome())) {
            throw new ApiException(HttpStatus.CONFLICT,
                    "Ja existe um setor com o nome informado");
        }
        setor.setNome(dados.getNome());
        setor.setDepartamento(dados.getDepartamento());
        setor.setLocalizacao(dados.getLocalizacao());
        return repository.save(setor);
    }

    @Transactional
    public void excluir(Long id) {
        Setor setor = buscar(id);
        if (!setor.getResiduos().isEmpty() || !setor.getPontosColeta().isEmpty()) {
            throw new ApiException(HttpStatus.CONFLICT,
                    "Nao e possivel excluir um setor que possui residuos ou pontos de coleta vinculados");
        }
        repository.delete(setor);
    }

}