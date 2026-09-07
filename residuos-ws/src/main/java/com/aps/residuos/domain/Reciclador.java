package com.aps.residuos.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * Stakeholder externo (cooperativa ou empresa) que recebe a destinacao
 * dos residuos reciclaveis.
 */
@Entity
@Table(name = "reciclador")
public class Reciclador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome do reciclador e obrigatorio")
    @Size(max = 150, message = "Nome deve ter no maximo 150 caracteres")
    @Column(nullable = false, length = 150)
    private String nome;

    @NotNull(message = "O tipo de reciclador e obrigatorio")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private TipoReciclador tipo;

    @NotBlank(message = "O CNPJ e obrigatorio")
    @Size(min = 14, max = 18, message = "CNPJ deve ter entre 14 e 18 caracteres")
    @Column(nullable = false, length = 18)
    private String cnpj;

    @Column(length = 255)
    private String materiaisAceitos;

    @Column(nullable = false)
    private boolean ativo = true;

    public Reciclador() {
    }

    public Reciclador(String nome, TipoReciclador tipo, String cnpj, String materiaisAceitos) {
        this.nome = nome;
        this.tipo = tipo;
        this.cnpj = cnpj;
        this.materiaisAceitos = materiaisAceitos;
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

    public TipoReciclador getTipo() {
        return tipo;
    }

    public void setTipo(TipoReciclador tipo) {
        this.tipo = tipo;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getMateriaisAceitos() {
        return materiaisAceitos;
    }

    public void setMateriaisAceitos(String materiaisAceitos) {
        this.materiaisAceitos = materiaisAceitos;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

}