package com.aps.residuos.service;

import com.aps.residuos.domain.Residuo;
import com.aps.residuos.repository.ResiduoRepository;
import com.aps.residuos.web.dto.ResiduoRequest;
import com.aps.residuos.web.exception.ApiException;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ResiduoService {

    private final ResiduoRepository repository;
    private final SetorService setorService;

    public ResiduoService(ResiduoRepository repository, SetorService setorService) {
        this.repository = repository;
        this.setorService = setorService;
    }

    public List<Residuo> listar(Long setorId, String tipo, Integer mes, Integer ano) {
        Specification<Residuo> spec = Specification.where(porSetorId(setorId))
                .and(porTipo(tipo))
                .and(porPeriodo(mes, ano));
        return repository.findAll(spec);
    }

    private Specification<Residuo> porSetorId(Long setorId) {
        return (root, query, cb) -> setorId == null
                ? cb.conjunction()
                : cb.equal(root.get("setor").get("id"), setorId);
    }

    private Specification<Residuo> porTipo(String tipo) {
        return (root, query, cb) -> {
            if (tipo == null) {
                return cb.conjunction();
            }
            try {
                return cb.equal(root.get("tipo"),
                        com.aps.residuos.domain.TipoResiduo.valueOf(tipo.toUpperCase()));
            } catch (IllegalArgumentException ex) {
                throw new ApiException(HttpStatus.BAD_REQUEST,
                        "Tipo de residuo invalido: " + tipo);
            }
        };
    }

    private Specification<Residuo> porPeriodo(Integer mes, Integer ano) {
        return (root, query, cb) -> {
            if (ano == null) {
                return cb.conjunction();
            }
            java.time.YearMonth periodo = mes == null
                    ? java.time.YearMonth.of(ano, java.time.Month.JANUARY)
                    : java.time.YearMonth.of(ano, mes);
            return cb.between(root.get("dataGeracao"),
                    periodo.atDay(1).atStartOfDay(),
                    periodo.atEndOfMonth().atTime(java.time.LocalTime.MAX));
        };
    }

    public Residuo buscar(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND,
                        "Residuo nao encontrado com id " + id));
    }

    @Transactional
    public Residuo criar(ResiduoRequest request) {
        Residuo residuo = new Residuo(
                request.tipo(),
                request.descricao().trim(),
                request.quantidadeKg(),
                setorService.buscar(request.setorId()));
        return repository.save(residuo);
    }

    @Transactional
    public Residuo atualizar(Long id, ResiduoRequest request) {
        Residuo residuo = buscar(id);
        residuo.setTipo(request.tipo());
        residuo.setDescricao(request.descricao().trim());
        residuo.setQuantidadeKg(request.quantidadeKg());
        residuo.setSetor(setorService.buscar(request.setorId()));
        return repository.save(residuo);
    }

    @Transactional
    public void excluir(Long id) {
        Residuo residuo = buscar(id);
        repository.delete(residuo);
    }

}