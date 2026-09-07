package com.aps.residuos.repository;

import com.aps.residuos.domain.Setor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SetorRepository extends JpaRepository<Setor, Long> {

    boolean existsByNomeIgnoringCase(String nome);

}