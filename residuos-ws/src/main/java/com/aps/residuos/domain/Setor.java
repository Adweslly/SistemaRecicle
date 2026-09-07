package com.aps.residuos.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

/**
 * Setor da organizacao que gera residuos e mantem pontos de coleta.
 */
@Entity
@Table(name = "setor")
public class Setor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome do setor e obrigatorio")
    @Column(nullable = false, length = 100)
    private String nome;

    @Size(max = 100, message = "Departamento deve ter no maximo 100 caracteres")
    @Column(length = 100)
    private String departamento;

    @Size(max = 120, message = "Localizacao deve ter no maximo 120 caracteres")
    @Column(length = 120)
    private String localizacao;

    @JsonIgnore
    @OneToMany(mappedBy = "setor")
    private List<Residuo> residuos = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "setor")
    private List<PontoColeta> pontosColeta = new ArrayList<>();

    public Setor() {
    }

    public Setor(String nome, String departamento, String localizacao) {
        this.nome = nome;
        this.departamento = departamento;
        this.localizacao = localizacao;
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

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }

    public List<Residuo> getResiduos() {
        return residuos;
    }

    public List<PontoColeta> getPontosColeta() {
        return pontosColeta;
    }

}