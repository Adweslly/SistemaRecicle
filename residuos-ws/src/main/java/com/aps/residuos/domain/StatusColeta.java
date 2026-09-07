package com.aps.residuos.domain;

/**
 * Fluxo de estados do procedimento de coleta de residuos.
 *
 * PENDENTE -> AGENDADA -> REALIZADA
 * PENDENTE/AGENDADA -> CANCELADA
 */
public enum StatusColeta {

    PENDENTE,
    AGENDADA,
    REALIZADA,
    CANCELADA

}