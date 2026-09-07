package com.aps.residuos.web.dto;

import com.aps.residuos.domain.TipoResiduo;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

/**
 * Contrato de entrada do servico de coletas.
 */
public record ColetaRequest(

        @NotNull(message = "O ponto de coleta e obrigatorio")
        Long pontoColetaId,

        @NotNull(message = "O tipo de residuo e obrigatorio")
        TipoResiduo tipoResiduo,

        @NotNull(message = "A quantidade e obrigatoria")
        @DecimalMin(value = "0.01", message = "A quantidade deve ser maior que zero")
        BigDecimal quantidadeKg,

        Long recicladorId
) {
}