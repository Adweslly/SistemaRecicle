package com.aps.residuos.service;

import com.aps.residuos.domain.Coleta;
import com.aps.residuos.domain.StatusColeta;
import com.aps.residuos.repository.ColetaRepository;
import com.aps.residuos.web.dto.ColetaRequest;
import com.aps.residuos.web.dto.ColetaStatusRequest;
import com.aps.residuos.web.exception.ApiException;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class ColetaService {

    private final ColetaRepository repository;
    private final PontoColetaService pontoColetaService;
    private final RecicladorService recicladorService;

    public ColetaService(ColetaRepository repository,
                         PontoColetaService pontoColetaService,
                         RecicladorService recicladorService) {
        this.repository = repository;
        this.pontoColetaService = pontoColetaService;
        this.recicladorService = recicladorService;
    }

    public List<Coleta> listar(StatusColeta status, Long pontoColetaId, Integer mes, Integer ano) {
        Specification<Coleta> spec = Specification.where(porStatus(status))
                .and(porPontoColeta(pontoColetaId))
                .and(porPeriodo(mes, ano));
        return repository.findAll(spec);
    }

    private Specification<Coleta> porStatus(StatusColeta status) {
        return (root, query, cb) -> status == null
                ? cb.conjunction()
                : cb.equal(root.get("status"), status);
    }

    private Specification<Coleta> porPontoColeta(Long pontoColetaId) {
        return (root, query, cb) -> pontoColetaId == null
                ? cb.conjunction()
                : cb.equal(root.get("pontoColeta").get("id"), pontoColetaId);
    }

    private Specification<Coleta> porPeriodo(Integer mes, Integer ano) {
        return (root, query, cb) -> {
            if (ano == null) {
                return cb.conjunction();
            }
            java.time.YearMonth periodo = mes == null
                    ? java.time.YearMonth.of(ano, java.time.Month.JANUARY)
                    : java.time.YearMonth.of(ano, mes);
            return cb.between(root.get("dataSolicitacao"),
                    periodo.atDay(1).atStartOfDay(),
                    periodo.atEndOfMonth().atTime(java.time.LocalTime.MAX));
        };
    }

    public Coleta buscar(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND,
                        "Coleta nao encontrada com id " + id));
    }

    @Transactional
    public Coleta solicitar(ColetaRequest request) {
        var ponto = pontoColetaService.buscar(request.pontoColetaId());
        validarTipoAceito(ponto, request);
        validarCapacidade(ponto, request);

        Coleta coleta = new Coleta();
        coleta.setPontoColeta(ponto);
        coleta.setTipoResiduo(request.tipoResiduo());
        coleta.setQuantidadeKg(request.quantidadeKg());
        coleta.setStatus(StatusColeta.PENDENTE);
        if (request.recicladorId() != null) {
            coleta.setRecicladorDestino(recicladorService.buscar(request.recicladorId()));
        }
        return repository.save(coleta);
    }

    private void validarTipoAceito(com.aps.residuos.domain.PontoColeta ponto, ColetaRequest request) {
        if (ponto.getTipoResiduoAceito() != request.tipoResiduo()) {
            throw new ApiException(HttpStatus.UNPROCESSABLE_ENTITY,
                    "O ponto de coleta '" + ponto.getNome()
                            + "' aceita apenas residuos do tipo " + ponto.getTipoResiduoAceito());
        }
    }

    private void validarCapacidade(com.aps.residuos.domain.PontoColeta ponto, ColetaRequest request) {
        if (request.quantidadeKg().compareTo(ponto.getCapacidadeKg()) > 0) {
            throw new ApiException(HttpStatus.UNPROCESSABLE_ENTITY,
                    "A quantidade informada (" + request.quantidadeKg() + " kg) excede a capacidade do ponto de coleta ("
                            + ponto.getCapacidadeKg() + " kg)");
        }
    }

    @Transactional
    public Coleta mudarStatus(Long id, ColetaStatusRequest request) {
        Coleta coleta = buscar(id);
        transicionar(coleta, request);
        return repository.save(coleta);
    }

    private void transicionar(Coleta coleta, ColetaStatusRequest request) {
        StatusColeta atual = coleta.getStatus();
        StatusColeta novo = request.novoStatus();
        if (atual == novo) {
            throw new ApiException(HttpStatus.CONFLICT,
                    "A coleta ja possui o status " + novo);
        }

        Map<StatusColeta, StatusColeta[]> permitidas = Map.of(
                StatusColeta.PENDENTE, new StatusColeta[]{StatusColeta.AGENDADA, StatusColeta.CANCELADA},
                StatusColeta.AGENDADA, new StatusColeta[]{StatusColeta.REALIZADA, StatusColeta.CANCELADA});

        StatusColeta[] destinos = permitidas.get(atual);
        if (destinos == null || !List.of(destinos).contains(novo)) {
            throw new ApiException(HttpStatus.BAD_REQUEST,
                    "Transicao de " + atual + " para " + novo + " nao permitida para a coleta " + coleta.getId());
        }

        coleta.setStatus(novo);
        switch (novo) {
            case AGENDADA -> {
                if (request.dataPrevista() == null) {
                    throw new ApiException(HttpStatus.BAD_REQUEST,
                            "Para agendar a coleta, a data prevista e obrigatoria");
                }
                coleta.setDataPrevista(request.dataPrevista());
                if (request.recicladorId() != null) {
                    coleta.setRecicladorDestino(recicladorService.buscar(request.recicladorId()));
                }
            }
            case REALIZADA -> {
                if (coleta.getDataPrevista() == null) {
                    throw new ApiException(HttpStatus.BAD_REQUEST,
                            "A coleta precisa estar agendada antes de ser realizada");
                }
                coleta.setDataExecucao(LocalDateTime.now());
                if (request.recicladorId() != null) {
                    coleta.setRecicladorDestino(recicladorService.buscar(request.recicladorId()));
                }
            }
            case CANCELADA -> {
                // apenas transicao livre a partir de PENDENTE ou AGENDADA
            }
            default -> throw new ApiException(HttpStatus.BAD_REQUEST,
                    "Status de destino invalido");
        }
    }

}