package com.aps.residuos.repository;

import java.math.BigDecimal;

/**
 * Projecao do relatorio de geracao de residuos por setor.
 */
public interface SetorGeracaoProjection {

    Long getSetorId();

    String getSetorNome();

    BigDecimal getTotalKg();

    Long getTotalItens();

}