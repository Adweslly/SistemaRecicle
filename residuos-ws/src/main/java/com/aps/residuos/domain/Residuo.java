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
 * Registro de residuo gerado por um setor da organizacao.
 */
@Entity
@Table(name = "residuo")
public class Residuo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private TipoResiduo tipo;

    @Column(nullable = false, length = 255)
    private String descricao;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal quantidadeKg;

    @Column(nullable = false)
    private LocalDateTime dataGeracao;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "setor_id")
    private Setor setor;

    @PrePersist
    protected void onCreate() {
        if (dataGeracao == null) {
            dataGeracao = LocalDateTime.now();
        }
    }

    public Residuo() {
    }

    public Residuo(TipoResiduo tipo, String descricao, BigDecimal quantidadeKg, Setor setor) {
        this.tipo = tipo;
        this.descricao = descricao;
        this.quantidadeKg = quantidadeKg;
        this.setor = setor;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TipoResiduo getTipo() {
        return tipo;
    }

    public void setTipo(TipoResiduo tipo) {
        this.tipo = tipo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public BigDecimal getQuantidadeKg() {
        return quantidadeKg;
    }

    public void setQuantidadeKg(BigDecimal quantidadeKg) {
        this.quantidadeKg = quantidadeKg;
    }

    public LocalDateTime getDataGeracao() {
        return dataGeracao;
    }

    public void setDataGeracao(LocalDateTime dataGeracao) {
        this.dataGeracao = dataGeracao;
    }

    public Setor getSetor() {
        return setor;
    }

    public void setSetor(Setor setor) {
        this.setor = setor;
    }

}