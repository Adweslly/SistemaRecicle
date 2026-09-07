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
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

/**
 * Ponto de coleta interno de uma organizacao, vinculado a um setor e
 * especializado no tipo de residuo que aceita.
 */
@Entity
@Table(name = "ponto_coleta")
public class PontoColeta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome do ponto de coleta e obrigatorio")
    @Size(max = 100, message = "Nome deve ter no maximo 100 caracteres")
    @Column(nullable = false, length = 100)
    private String nome;

    @NotNull(message = "O tipo de residuo aceito e obrigatorio")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private TipoResiduo tipoResiduoAceito;

    @NotNull(message = "A capacidade e obrigatoria")
    @DecimalMin(value = "0.01", message = "A capacidade deve ser maior que zero")
    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal capacidadeKg;

    @NotNull(message = "O setor vinculado e obrigatorio")
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "setor_id")
    private Setor setor;

    public PontoColeta() {
    }

    public PontoColeta(String nome, TipoResiduo tipoResiduoAceito, BigDecimal capacidadeKg, Setor setor) {
        this.nome = nome;
        this.tipoResiduoAceito = tipoResiduoAceito;
        this.capacidadeKg = capacidadeKg;
        this.setor = setor;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public TipoResiduo getTipoResiduoAceito() {
        return tipoResiduoAceito;
    }

    public void setTipoResiduoAceito(TipoResiduo tipoResiduoAceito) {
        this.tipoResiduoAceito = tipoResiduoAceito;
    }

    public BigDecimal getCapacidadeKg() {
        return capacidadeKg;
    }

    public void setCapacidadeKg(BigDecimal capacidadeKg) {
        this.capacidadeKg = capacidadeKg;
    }

    public Setor getSetor() {
        return setor;
    }

    public void setSetor(Setor setor) {
        this.setor = setor;
    }

}