package com.aps.residuos.repository;

import com.aps.residuos.domain.Coleta;
import com.aps.residuos.domain.StatusColeta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ColetaRepository extends JpaRepository<Coleta, Long>,
        JpaSpecificationExecutor<Coleta> {

    List<Coleta> findByStatus(StatusColeta status);

    List<Coleta> findByPontoColetaId(Long pontoColetaId);

    long countByStatus(StatusColeta status);

}