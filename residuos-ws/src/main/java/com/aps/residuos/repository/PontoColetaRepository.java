package com.aps.residuos.repository;

import com.aps.residuos.domain.PontoColeta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PontoColetaRepository extends JpaRepository<PontoColeta, Long> {

    List<PontoColeta> findBySetorId(Long setorId);

    List<PontoColeta> findByTipoResiduoAceito(com.aps.residuos.domain.TipoResiduo tipo);

}