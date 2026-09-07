package com.aps.residuos.repository;

import com.aps.residuos.domain.Residuo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResiduoRepository extends JpaRepository<Residuo, Long>,
        JpaSpecificationExecutor<Residuo> {

    List<Residuo> findBySetorId(Long setorId);

    long countBySetorId(Long setorId);

    /**
     * Agrega a geracao de residuos consolidada por setor, com filtro
     * opcional de mes e ano a partir da data de geracao.
     */
    @org.springframework.data.jpa.repository.Query("""
            select r.setor.id as setorId,
                   r.setor.nome as setorNome,
                   sum(r.quantidadeKg) as totalKg,
                   count(r.id) as totalItens
            from Residuo r
            where (:ano is null or year(r.dataGeracao) = :ano)
              and (:mes is null or month(r.dataGeracao) = :mes)
            group by r.setor.id, r.setor.nome
            order by totalKg desc
            """)
    List<SetorGeracaoProjection> agregarPorSetor(
            @org.springframework.data.repository.query.Param("mes") Integer mes,
            @org.springframework.data.repository.query.Param("ano") Integer ano);

}