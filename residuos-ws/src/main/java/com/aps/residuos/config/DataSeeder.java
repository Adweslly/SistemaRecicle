package com.aps.residuos.config;

import com.aps.residuos.domain.Coleta;
import com.aps.residuos.domain.PontoColeta;
import com.aps.residuos.domain.Reciclador;
import com.aps.residuos.domain.Residuo;
import com.aps.residuos.domain.Setor;
import com.aps.residuos.domain.StatusColeta;
import com.aps.residuos.domain.TipoReciclador;
import com.aps.residuos.domain.TipoResiduo;
import com.aps.residuos.repository.ColetaRepository;
import com.aps.residuos.repository.PontoColetaRepository;
import com.aps.residuos.repository.RecicladorRepository;
import com.aps.residuos.repository.ResiduoRepository;
import com.aps.residuos.repository.SetorRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Popula o banco em memoria com dados de exemplo para demonstracao e
 * testes, somente quando o banco estiver vazio.
 */
@Configuration
@Profile("!test")
public class DataSeeder {

    @Bean
    CommandLineRunner seed(SetorRepository setores,
                           ResiduoRepository residuos,
                           PontoColetaRepository pontos,
                           RecicladorRepository recicladores,
                           ColetaRepository coletas) {
        return args -> {
            if (setores.count() > 0) {
                return;
            }

            Setor administrativo = new Setor("Administrativo", "Gerencia", "Bloco A - 1o andar");
            Setor producao = new Setor("Producao", "Operacoes", "Galpao 2");
            Setor restaurante = new Setor("Restaurante", "Facilities", "Bloco B - Terreo");
            setores.save(administrativo);
            setores.save(producao);
            setores.save(restaurante);

            Reciclador papelZero = new Reciclador("Coop. Papel Zero", TipoReciclador.COOPERATIVA,
                    "12345678000190", "Papel, plastico, metal");
            Reciclador organicaVida = new Reciclador("Organica Vida Ltda", TipoReciclador.EMPRESA,
                    "98765432000112", "Residuos organicos");
            recicladores.save(papelZero);
            recicladores.save(organicaVida);

            PontoColeta pontoAdm = new PontoColeta("Ponto Administrativo",
                    TipoResiduo.RECICLAVEL, new BigDecimal("500"), administrativo);
            PontoColeta pontoProd = new PontoColeta("Ponto da Producao",
                    TipoResiduo.RECICLAVEL, new BigDecimal("1000"), producao);
            PontoColeta pontoRest = new PontoColeta("Ponto do Restaurante",
                    TipoResiduo.ORGANICO, new BigDecimal("300"), restaurante);
            pontos.save(pontoAdm);
            pontos.save(pontoProd);
            pontos.save(pontoRest);

            LocalDateTime base = LocalDateTime.now().withDayOfMonth(1);
            residuos.save(new Residuo(TipoResiduo.RECICLAVEL, "Papel branco de escritorio",
                    new BigDecimal("45.5"), administrativo));
            residuos.save(new Residuo(TipoResiduo.RECICLAVEL, "Plastico de embalagens",
                    new BigDecimal("120"), producao));
            residuos.save(new Residuo(TipoResiduo.ORGANICO, "Sobras de alimentos",
                    new BigDecimal("80"), restaurante));
            residuos.save(new Residuo(TipoResiduo.RECICLAVEL, "Metal de sucata",
                    new BigDecimal("230"), producao));
            residuos.save(new Residuo(TipoResiduo.PERIGOSO, "Cartuchos e toners",
                    new BigDecimal("9.8"), administrativo));
            residuos.save(new Residuo(TipoResiduo.ORGANICO, "Cascas e restos de cozinha",
                    new BigDecimal("60.4"), restaurante));

            Coleta c1 = new Coleta();
            c1.setPontoColeta(pontoAdm);
            c1.setTipoResiduo(TipoResiduo.RECICLAVEL);
            c1.setQuantidadeKg(new BigDecimal("30"));
            c1.setStatus(StatusColeta.REALIZADA);
            c1.setDataSolicitacao(base.minusDays(12));
            c1.setDataPrevista(base.minusDays(10));
            c1.setDataExecucao(base.minusDays(9));
            c1.setRecicladorDestino(papelZero);
            coletas.save(c1);

            Coleta c2 = new Coleta();
            c2.setPontoColeta(pontoProd);
            c2.setTipoResiduo(TipoResiduo.RECICLAVEL);
            c2.setQuantidadeKg(new BigDecimal("150"));
            c2.setStatus(StatusColeta.REALIZADA);
            c2.setDataSolicitacao(base.minusDays(20));
            c2.setDataPrevista(base.minusDays(18));
            c2.setDataExecucao(base.minusDays(17));
            c2.setRecicladorDestino(papelZero);
            coletas.save(c2);

            Coleta c3 = new Coleta();
            c3.setPontoColeta(pontoRest);
            c3.setTipoResiduo(TipoResiduo.ORGANICO);
            c3.setQuantidadeKg(new BigDecimal("70"));
            c3.setStatus(StatusColeta.REALIZADA);
            c3.setDataSolicitacao(base.minusDays(6));
            c3.setDataPrevista(base.minusDays(5));
            c3.setDataExecucao(base.minusDays(4));
            c3.setRecicladorDestino(organicaVida);
            coletas.save(c3);

            Coleta c4 = new Coleta();
            c4.setPontoColeta(pontoProd);
            c4.setTipoResiduo(TipoResiduo.RECICLAVEL);
            c4.setQuantidadeKg(new BigDecimal("90"));
            c4.setStatus(StatusColeta.REALIZADA);
            c4.setDataSolicitacao(base.minusDays(3));
            c4.setDataPrevista(base.minusDays(1));
            c4.setDataExecucao(base);
            coletas.save(c4);

            Coleta c5 = new Coleta();
            c5.setPontoColeta(pontoAdm);
            c5.setTipoResiduo(TipoResiduo.RECICLAVEL);
            c5.setQuantidadeKg(new BigDecimal("18"));
            c5.setStatus(StatusColeta.AGENDADA);
            c5.setDataSolicitacao(base);
            c5.setDataPrevista(base.plusDays(2));
            c5.setRecicladorDestino(papelZero);
            coletas.save(c5);

            Coleta c6 = new Coleta();
            c6.setPontoColeta(pontoRest);
            c6.setTipoResiduo(TipoResiduo.ORGANICO);
            c6.setQuantidadeKg(new BigDecimal("40"));
            c6.setStatus(StatusColeta.PENDENTE);
            c6.setDataSolicitacao(base);
            coletas.save(c6);
        };
    }

}