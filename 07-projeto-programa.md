# 7. Projeto (estrutura e módulos) do programa

## 7.1 Visão geral da estrutura

O programa foi organizado em um projeto único (Maven), denominado
`residuos-ws`, seguindo a separação em camadas proposta no plano de
desenvolvimento (seção 6). O back-end da aplicação — responsável pelos
Web Services — está no diretório `src/main/java`, enquanto a aplicação
consumidora (front-end web) está no diretório `src/main/resources/static`.

A estrutura de pacotes do código Java é a seguinte:

```
com.aps.residuos
├── ResiduosApplication        → ponto de entrada (Spring Boot)
├── config/                    → configurações (CORS e carga inicial de dados)
├── domain/                    → entidades e enumerações do domínio
├── repository/                → acesso a dados (Spring Data JPA)
├── service/                   → regras de negócio
├── web/                       → controllers REST e DTOs
│   ├── dto/                   → contratos de entrada da API
│   └── exception/             → tratamento padronizado de erros
```

Essa organização reflete o padrão de camadas do Spring (Controller →
Service → Repository), que separa a interface HTTP, as regras de negócio e
o acesso ao banco, favorecendo a manutenibilidade e a testabilidade do
código.

## 7.2 Módulos do programa

O programa é composto pelos seguintes módulos, cada um com responsabilidade
bem definida:

| Módulo | Classe principal | Responsabilidade |
|---|---|---|
| Aplicação | `ResiduosApplication` | Inicialização do Spring Boot |
| Domínio | Classes do pacote `domain` | Entidades e enums (Setor, Residuo, PontoColeta, Reciclador, Coleta) |
| Persistência | Interfaces do pacote `repository` | Acesso a dados via Spring Data JPA |
| Negócio | Classes do pacote `service` | Regras de negócio e orquestração |
| API REST | Controllers do pacote `web` | Exposição dos Web Services |
| Tratamento de erros | `GlobalExceptionHandler` | Respostas de erro padronizadas |
| Configuração | `CorsConfig` e `DataSeeder` | CORS e dados de demonstração |
| Consumidor | Arquivos em `static/` | Aplicação web que consome a API |

### 7.2.1 Módulo de domínio

O módulo de domínio modela os conceitos do problema com entidades JPA e
enumerações:

-   **Setor**: setor da organização que gera resíduos e possui pontos de
    coleta;
-   **Residuo**: registro de resíduo gerado, com tipo, descrição,
    quantidade (kg), data de geração e setor de origem;
-   **PontoColeta**: local interno que recebe resíduos de um tipo
    específico, com capacidade máxima;
-   **Reciclador**: stakeholder externo (cooperativa ou empresa) que
    recebe a destinação dos materiais;
-   **Coleta**: procedimento de coleta, com ponto de coleta, tipo,
    quantidade, datas e status;
-   **Enumerações**: `TipoResiduo` (ORGANICO, RECICLAVEL, PERIGOSO,
    REJEITO), `TipoReciclador` (COOPERATIVA, EMPRESA) e `StatusColeta`
    (PENDENTE, AGENDADA, REALIZADA, CANCELADA).

As enumerações garantem a consistência dos dados, pois restringem os
valores aceitos e simplificam os relatórios.

### 7.2.2 Módulo de persistência

As interfaces de repositório estendem `JpaRepository` e
`JpaSpecificationExecutor`, o que fornece operações prontas de banco
(CRUD, paginação e consultas por especificação). O módulo também contém a
consulta agregada que alimenta o relatório de geração por setor:

```
ResiduoRepository.agregarPorSetor(mes, ano)
```

Essa consulta utiliza agrupamento (GROUP BY) na linguagem JPQL para somar
as quantidades por setor, com filtros opcionais de mês e ano, e retorna o
resultado por meio de uma projeção (`SetorGeracaoProjection`).

### 7.2.3 Módulo de negócio

As camadas de serviço concentram as regras de negócio. Destacam-se:

-   **ColetaService**: valida se o tipo de resíduo é aceito pelo ponto de
    coleta, se a quantidade não excede a capacidade e se a transição de
    status é legal. O fluxo de transições é explicitado no código:
    `PENDENTE → AGENDADA/CANCELADA` e `AGENDADA → REALIZADA/CANCELADA`;
-   **SetorService**: impede a criação de setores duplicados e a exclusão
    de setores com vínculos;
-   **RecicladorService**: impede CNPJ duplicado e implementa a inativação
    lógica de parceiros;
-   **RelatorioService**: calcula destinação (reciclado × aterro) e o
    indicador de desvio de aterro a partir das coletas realizadas.

### 7.2.4 Módulo de API (controllers)

Cada recurso do domínio é exposto por um controller REST. A tabela abaixo
resume os Web Services implementados:

| Controller | Endpoints |
|---|---|
| SetorController | GET/POST/PUT/DELETE `/setores` |
| ResiduoController | GET/POST/PUT/DELETE `/residuos` + filtros por setor, tipo e período |
| PontoColetaController | GET/POST/PUT/DELETE `/pontos` |
| RecicladorController | GET/POST/PUT/DELETE `/recicladores` |
| ColetaController | GET/POST `/coletas` e PATCH `/coletas/{id}/status` |
| RelatorioController | GET `/relatorios/geracao-por-setor`, `/relatorios/destinacao`, `/relatorios/desvio-aterro` |

Os controllers recebem os corpos de requisição em DTOs (`record` do Java)
até o módulo de negócio, evitando o acoplamento direto da entrada HTTP com
as entidades do banco.

### 7.2.5 Módulo de tratamento de erros

O `GlobalExceptionHandler` centraliza a conversão de exceções em respostas
HTTP com o formato padronizado planejado na seção 6.10:

-   Exceções de negócio (`ApiException`) → código e mensagem definidos pelo
    serviço (400, 404, 409, 422);
-   Erros de validação das anotações (`@Valid`) → 400 com detalhes dos
    campos;
-   Corpo de requisição mal formado → 400;
-   Exceções inesperadas → 500 com mensagem genérica.

### 7.2.6 Módulo de configuração

-   **CorsConfig**: libera chamadas entre origens diferentes, permitindo
    que a aplicação consumidora seja executada separadamente da API;
-   **DataSeeder**: carrega dados de exemplo (setores, resíduos, pontos,
    recicladores e coletas em diferentes status) quando o banco está
    vazio, viabilizando a demonstração do sistema.

## 7.3 Modelo de dados

O banco relacional (H2) é gerado a partir das entidades por meio do
Hibernate. As principais tabelas e seus relacionamentos são:

| Tabela | Colunas principais | Relacionamentos |
|---|---|---|
| setor | id, nome, departamento, localizacao | 1:N para residuo e ponto_coleta |
| residuo | id, tipo, descricao, quantidade_kg, data_geracao, setor_id | N:1 para setor |
| ponto_coleta | id, nome, tipo_residuo_aceito, capacidade_kg, setor_id | N:1 para setor |
| reciclador | id, nome, tipo, cnpj, materiais_aceitos, ativo | 1:N para coleta (destino) |
| coleta | id, ponto_coleta_id, tipo_residuo, quantidade_kg, data_solicitacao, data_prevista, data_execucao, status, reciclador_id | N:1 para ponto_coleta e reciclador |

## 7.4 Estrutura da aplicação consumidora

A aplicação web, armazenada em `src/main/resources/static`, consome os Web
Services por meio de `fetch` (JavaScript), no formato JSON. Suas páginas
são:

| Página | Funcionalidade |
|---|---|
| index.html | Dashboard com indicadores e gráficos (Chart.js) |
| setores.html | CRUD de setores |
| residuos.html | Registro e listagem de resíduos com filtros |
| pontos.html | CRUD de pontos de coleta |
| coletas.html | Solicitação, agendamento, realização e cancelamento |
| recicladores.html | CRUD de parceiros de destinação |
| relatorios.html | Relatórios e indicadores com filtro de período |

Todos os arquivos JavaScript compartilham o módulo `api.js`, que centraliza
as chamadas HTTP e o tratamento de erros no cliente, e o arquivo
`style.css`, que fornece o estilo visual comum.

## 7.5 Resumo dos padrões de projeto

Na construção do programa, três padrões de projeto/arquitetura se destacam
relacionados à formação em Engenharia de Software: o **padrão camadas**
(Controller/Service/Repository), que separa responsabilidades; o **padrão
DTO** (Data Transfer Object), que protege a modelagem interna da API; e o
**padrão DAO/Repository**, que abstrai a persistência. Esses padrões
organizam o código em torno de responsabilidades bem definidas, o que
facilita a manutenção e a evolução do software (PRESSMAN, 2016). Além
deles, o programa adota o princípio da **interface uniforme** do REST
(FIELDING, 2000), expresso no uso consistente de métodos HTTP sobre os
recursos do domínio.

## Referências utilizadas nesta seção

FIELDING, Roy Thomas. **Architectural Styles and the Design of Network-
based Software Architectures**. 2000. Tese (Doutorado em Ciência da
Computação) — University of California, Irvine.

PIVOTAL SOFTWARE. **Spring Boot Reference Documentation**. Disponível em:
https://docs.spring.io/spring-boot/. Acesso em: 2026.

PRESSMAN, Roger S. **Engenharia de Software**: uma abordagem
profissional. 8. ed. Porto Alegre: AMGH, 2016.