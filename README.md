# SistemaRecicle — Gestão de Resíduos Sólidos com Web Services

Aplicação de **Atividades Práticas Supervisionadas (APS)** do curso de
Ciência da Computação, desenvolvida como um **Web Service REST** para
apoio à gestão ambiental (alinhada à ISO 14001 e à Política Nacional de
Resíduos Sólidos — Lei nº 12.305/2010).

O sistema permite gerenciar setores, resíduos, pontos de coleta,
recicladores e coletas, com o ciclo de vida de cada coleta seguindo o
fluxo **PENDENTE → AGENDADA → REALIZADA / CANCELADA**, demonstrando a
melhoria de procedimentos entre stakeholders.

## Stack

| Camada        | Tecnologia                                  |
|---------------|---------------------------------------------|
| Backend       | Java 17+, Spring Boot 3.5.16 (REST API)     |
| Banco de dados| H2 em memória (`jdbc:h2:mem:residuosdb`)    |
| Frontend      | HTML, CSS, JavaScript (vanilla + Chart.js)  |
| Build         | Maven Wrapper (Maven 3.9.9)                 |

## Como executar

Pré-requisito: **Java 17 ou superior** já instalado.

```bash
cd residuos-ws
.\mvnw.cmd spring-boot:run   # Linux/macOS: ./mvnw spring-boot:run
```

A aplicação sobe em `http://localhost:8080` e o banco H2 é populado
automaticamente com dados de demonstração.

## Endereços úteis

- **Aplicação/web:** http://localhost:8080
- **Console H2:** http://localhost:8080/h2-console
- **API (exemplos):**
  - `GET  /setores`
  - `GET  /residuos?setorId=&tipo=`
  - `GET  /pontos`
  - `GET  /recicladores`
  - `GET  /coletas`
  - `PATCH /coletas/{id}/status`  (transição de status da coleta)
  - `GET  /relatorios/geracao-por-setor`
  - `GET  /relatorios/destinacao`
  - `GET  /relatorios/desvio-aterro`

## Estrutura do projeto

```
residuos-ws/
└── src/main/
    ├── java/com/aps/residuos/
    │   ├── config/        # CorsConfig, DataSeeder
    │   ├── domain/        # Entidades e enums
    │   ├── repository/    # Acesso a dados (Spring Data JPA)
    │   ├── service/       # Regras de negócio
    │   └── web/           # Controllers, DTOs, tratamento de erros
    └── resources/
        ├── static/        # Frontend (HTML/CSS/JS)
        └── application.properties
```

## Modelagem de negócio

Entidades: `Setor`, `Residuo`, `PontoColeta`, `Reciclador`, `Coleta`.

O fluxo de uma coleta é controlado por regras de transição de status no
`ColetaService`, que rejeitam operações ilegais (ex.: realizar uma coleta
ainda pendente) com respostas de erro padronizadas.