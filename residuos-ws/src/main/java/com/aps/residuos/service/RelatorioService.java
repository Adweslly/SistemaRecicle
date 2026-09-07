package com.aps.residuos.service;

import com.aps.residuos.domain.Coleta;
import com.aps.residuos.domain.StatusColeta;
import com.aps.residuos.repository.ColetaRepository;
import com.aps.residuos.repository.ResiduoRepository;
import com.aps.residuos.repository.SetorGeracaoProjection;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Servico responsavel pelos relatorios gerenciais e indicadores
 * ambientais da organizacao.
 */
@Service
public class RelatorioService {

    private final ResiduoRepository residuoRepository;
    private final ColetaRepository coletaRepository;

    public RelatorioService(ResiduoRepository residuoRepository,
                            ColetaRepository coletaRepository) {
        this.residuoRepository = residuoRepository;
        this.coletaRepository = coletaRepository;
    }

    public List<SetorGeracaoProjection> geracaoPorSetor(Integer mes, Integer ano) {
        return residuoRepository.agregarPorSetor(mes, ano);
    }

    /**
     * Dados consolidados da destinacao (reciclado x aterro) a partir das
     * coletas realizadas.
     */
    public Destinacao destinacao(Integer mes, Integer ano) {
        List<Coleta> realizadas = coletasRealizadasNoPeriodo(mes, ano);

        BigDecimal recicladoKg = realizadas.stream()
                .filter(c -> c.getRecicladorDestino() != null)
                .map(Coleta::getQuantidadeKg)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal aterroKg = realizadas.stream()
                .filter(c -> c.getRecicladorDestino() == null)
                .map(Coleta::getQuantidadeKg)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalKg = recicladoKg.add(aterroKg);
        BigDecimal percentualReciclado = percentual(recicladoKg, totalKg);
        BigDecimal percentualAterro = percentual(aterroKg, totalKg);

        return new Destinacao(totalKg, recicladoKg, aterroKg,
                percentualReciclado, percentualAterro, realizadas.size());
    }

    /**
     * Indicador de desvio de aterro: percentual dos residuos coletados
     * que foram destinados a reciclagem em vez do aterro.
     */
    public DesvioAterro desvioAterro(Integer mes, Integer ano) {
        Destinacao d = destinacao(mes, ano);
        return new DesvioAterro(d.percentualReciclado(), d.recicladoKg(), d.aterroKg(), d.totalKg());
    }

    private List<Coleta> coletasRealizadasNoPeriodo(Integer mes, Integer ano) {
        LocalDateTime inicio;
        LocalDateTime fim;
        if (ano == null) {
            inicio = LocalDateTime.MIN;
            fim = LocalDateTime.MAX;
        } else if (mes == null) {
            inicio = java.time.Year.of(ano).atDay(1).atStartOfDay();
            fim = java.time.Year.of(ano).atMonth(java.time.Month.DECEMBER).atEndOfMonth().atTime(23, 59, 59);
        } else {
            inicio = java.time.YearMonth.of(ano, mes).atDay(1).atStartOfDay();
            fim = java.time.YearMonth.of(ano, mes).atEndOfMonth().atTime(23, 59, 59);
        }

        return coletaRepository.findAll().stream()
                .filter(c -> c.getStatus() == StatusColeta.REALIZADA)
                .filter(c -> {
                    LocalDateTime execucao = c.getDataExecucao();
                    return execucao != null && !execucao.isBefore(inicio) && !execucao.isAfter(fim);
                })
                .toList();
    }

    private BigDecimal percentual(BigDecimal parte, BigDecimal total) {
        if (total.signum() == 0) {
            return BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
        }
        return parte.multiply(BigDecimal.valueOf(100))
                .divide(total, 2, RoundingMode.HALF_UP);
    }

    public record Destinacao(BigDecimal totalKg,
                             BigDecimal recicladoKg,
                             BigDecimal aterroKg,
                             BigDecimal percentualReciclado,
                             BigDecimal percentualAterro,
                             long totalColetas) {
    }

    public record DesvioAterro(BigDecimal desvioAterroPercentual,
                               BigDecimal recicladoKg,
                               BigDecimal aterroKg,
                               BigDecimal totalKg) {
    }

}