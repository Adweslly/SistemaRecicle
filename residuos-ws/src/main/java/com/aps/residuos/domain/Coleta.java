package com.aps.residuos.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Coleta de residuos: o procedimento central que conecta o ponto de
 * coleta, o tipo de residuo e a destinacao ao reciclador. O status
 * percorre o fluxo PENDENTE -> AGENDADA -> REALIZADA ou CANCELADA.
 */
@Entity
@Table(name = "coleta")
public class Coleta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "ponto_coleta_id")
    private PontoColeta pontoColeta;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private TipoResiduo tipoResiduo;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal quantidadeKg;

    @Column(nullable = false)
    private LocalDateTime dataSolicitacao;

    private LocalDateTime dataPrevista;

    private LocalDateTime dataExecucao;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private StatusColeta status = StatusColeta.PENDENTE;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "reciclador_id")
    private Reciclador recicladorDestino;

    @PrePersist
    protected void onCreate() {
        if (dataSolicitacao == null) {
            dataSolicitacao = LocalDateTime.now();
        }
    }

    public Coleta() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PontoColeta getPontoColeta() {
        return pontoColeta;
    }

    public void setPontoColeta(PontoColeta pontoColeta) {
        this.pontoColeta = pontoColeta;
    }

    public TipoResiduo getTipoResiduo() {
        return tipoResiduo;
    }

    public void setTipoResiduo(TipoResiduo tipoResiduo) {
        this.tipoResiduo = tipoResiduo;
    }

    public BigDecimal getQuantidadeKg() {
        return quantidadeKg;
    }

    public void setQuantidadeKg(BigDecimal quantidadeKg) {
        this.quantidadeKg = quantidadeKg;
    }

    public LocalDateTime getDataSolicitacao() {
        return dataSolicitacao;
    }

    public void setDataSolicitacao(LocalDateTime dataSolicitacao) {
        this.dataSolicitacao = dataSolicitacao;
    }

    public LocalDateTime getDataPrevista() {
        return dataPrevista;
    }

    public void setDataPrevista(LocalDateTime dataPrevista) {
        this.dataPrevista = dataPrevista;
    }

    public LocalDateTime getDataExecucao() {
        return dataExecucao;
    }

    public void setDataExecucao(LocalDateTime dataExecucao) {
        this.dataExecucao = dataExecucao;
    }

    public StatusColeta getStatus() {
        return status;
    }

    public void setStatus(StatusColeta status) {
        this.status = status;
    }

    public Reciclador getRecicladorDestino() {
        return recicladorDestino;
    }

    public void setRecicladorDestino(Reciclador recicladorDestino) {
        this.recicladorDestino = recicladorDestino;
    }

}