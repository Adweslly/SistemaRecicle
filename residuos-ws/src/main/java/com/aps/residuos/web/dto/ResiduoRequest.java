package com.aps.residuos.web.dto;

import com.aps.residuos.domain.TipoResiduo;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

/**
 * Contrato de entrada do servico de residuos.
 */
public record ResiduoRequest(

        @NotNull(message = "O tipo de residuo e obrigatorio")
        TipoResiduo tipo,

        @NotBlank(message = "A descricao e obrigatoria")
        @Size(max = 255, message = "Descricao deve ter no maximo 255 caracteres")
        String descricao,

        @NotNull(message = "A quantidade e obrigatoria")
        @DecimalMin(value = "0.01", message = "A quantidade deve ser maior que zero")
        BigDecimal quantidadeKg,

        @NotNull(message = "O setor de origem e obrigatorio")
        Long setorId
) {
}