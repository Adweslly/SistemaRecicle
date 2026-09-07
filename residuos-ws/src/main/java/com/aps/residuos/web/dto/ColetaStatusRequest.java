package com.aps.residuos.web.dto;

import com.aps.residuos.domain.StatusColeta;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

/**
 * Contrato de entrada para a mudanca de status de uma coleta.
 * A data prevista so se aplica no agendamento (transicao para AGENDADA).
 */
public record ColetaStatusRequest(

        @NotNull(message = "O novo status e obrigatorio")
        StatusColeta novoStatus,

        LocalDateTime dataPrevista,
        Long recicladorId
) {
}