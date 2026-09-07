# 6. Plano de Desenvolvimento da Aplicação

## 6.1 Contexto e problema

O crescimento das organizações e o aumento da complexidade dos seus
processos produtivos trazem, como consequência direta, a geração
crescente de resíduos sólidos. Quando esses resíduos deixam de ser
tratados de forma organizada, observam-se impactos ambientais relevantes:
contaminação do solo e da água, emissão de gases de efeito estufa em
aterros, desperdício de materiais potencialmente recicláveis e passivos
ambientais decorrentes do descarte inadequado.

Em paralelo, cresce a pressão legal e social por práticas compatíveis com
a sustentabilidade. A família de normas ISO 14000, em especial a ISO
14001, estabelece os requisitos de um Sistema de Gestão Ambiental (SGA),
incluindo a necessidade de a organização identificar, controlar e
melhorar continuamente os aspectos ambientais das suas atividades,
produtos e serviços. Dentro desse contexto, a gestão de resíduos sólidos
aparece como um dos aspectos ambientais mais tangíveis do dia a dia
operacional, envolvendo diferentes partes interessadas — *stakeholders* —
como colaboradores, setores produtivos, empresas de coleta, cooperativas
de reciclagem e a própria auditoria da gestão ambiental.

Na prática, porém, muitas organizações ainda executam esse controle de
forma manual, com planilhas isoladas, anotações em papel e comunicação
informal entre setores e parceiros externos. Esse cenário dificulta o
acompanhamento de indicadores, compromete a confiabilidade dos dados e
cria brechas para não conformidades frente à legislação (BRASIL, 2010) e
à norma ISO 14001.

O problema que esta aplicação se propõe a resolver é, portanto, o
seguinte: **substituir o controle manual de resíduos sólidos por um
sistema informatizado, baseado em Web Services, que padronize o
procedimento entre os stakeholders, dê visibilidade aos indicadores
ambientais e reduza os impactos da atividade da organização.**

## 6.2 Objetivos da aplicação

### 6.2.1 Objetivo geral

Desenvolver uma aplicação baseada em Web Services, integrada às
iniciativas de gestão ambiental de uma organização, capaz de registrar,
controlar e reportar o fluxo de resíduos sólidos — da geração no setor à
destinação final — promovendo melhorias de procedimento entre os
stakeholders e apoiando a conformidade com a família de normas ISO 14000.

### 6.2.2 Objetivos específicos

-   Especificar e implementar uma API REST que exponha os serviços de
    gestão de resíduos, com contratos padronizados e tratamento de erros;
-   Desenvolver uma aplicação web consumidora dos Web Services, acessível
    aos stakeholders internos da organização;
-   Modelar o fluxo de coleta como um procedimento formal (solicitação,
    agendamento, execução e destinação), eliminando a comunicação
    informal;
-   Gerar relatórios gerenciais com indicadores de desempenho ambiental,
    tais como geração por setor, taxa de reciclagem e desvio de resíduos
    do aterro;
-   Apresentar, na dissertação, uma discussão sobre os elementos
    utilizados, a interdisciplinaridade envolvida e o efeito do trabalho
    na formação acadêmica.

## 6.3 Justificativa e embasamento normativo

A gestão adequada de resíduos sólidos é um requisito recorrente dos
Sistemas de Gestão Ambiental. A norma ABNT NBR ISO 14001 define o ciclo
PDCA (Planejar, Executar, Verificar e Agir) como base do SGA e exige que
a organização mantenha informações documentadas sobre seus aspectos
ambientais e sobre o desempenho relacionado a eles (ABNT, 2015). No
Brasil, a Política Nacional de Resíduos Sólidos (Lei nº 12.305/2010)
estabelece a ordem de prioridade na gestão dos resíduos: não geração,
redução, reutilização, reciclagem, tratamento dos resíduos sólidos e
disposição final ambientalmente adequada dos rejeitos (BRASIL, 2010).

O registro adequado da origem, da quantidade e da destinação dos
resíduos é, portanto, mais do que uma boa prática operacional: é um
instrumento de evidência para auditorias internas e externas do SGA. Ao
automatizar esse controle por meio de Web Services, a organização ganha
rastreabilidade, reduz erros de registro e consegue demonstrar melhorias
no desempenho ambiental — o que se conecta diretamente ao espírito da
ISO 14001, que extrapola os benefícios puramente ecológicos e alcança
ganhos de produtividade, relacionamento com fornecedores e comunidades e
redução de custos operacionais (OLIVEIRA, 2010).

## 6.4 Stakeholders e o procedimento a ser melhorado

A aplicação serve a diferentes partes interessadas. Para o cumprimento do
requisito do edital, que pede "ações que minimizem os impactos ambientais
e implementem melhorias de procedimentos entre os stakeholders", o fluxo
de coleta foi desenhado como o procedimento central a ser informatizado.

| Stakeholder | Papel na organização | Interação com o sistema |
|---|---|---|
| Setor produtivo | Gera resíduos no dia a dia | Registra o resíduo gerado e solicita coleta |
| Gestor ambiental | Responsável pelo SGA | Agenda coletas, acompanha indicadores e gera relatórios |
| Ponto de coleta | Concentra resíduos até a destinação | Recebe os resíduos e confirma a coleta |
| Reciclador/Cooperativa | Destinação dos recicláveis | Recebe a destinação conforme os materiais aceitos |
| Auditoria | Verifica a conformidade | Consulta relatórios de rastreabilidade |

Como situação problema, imaginemos o cenário anterior ao sistema: um setor
detecta acúmulo de resíduos recicláveis e comunica a necessidade de coleta
por telefone ou e-mail informal; não há registro confiável da quantidade
nem da data; não se sabe se o resíduo foi reciclado ou enviado ao aterro.
No cenário informatizado, o setor registra o resíduo, o gestor agenda a
coleta pela aplicação, o ponto de coleta e o reciclador recebem a
solicitação de forma padronizada e o status percorre um fluxo definido.
Ao final, os dados alimentam relatórios que demonstram, por exemplo, o
percentual de material desviado do aterro — um indicador direto do
objetivo ambiental da organização.

O fluxo de estados do procedimento é:

```
PENDENTE → AGENDADA → REALIZADA
    └─────────→ CANCELADA
```

## 6.5 Requisitos funcionais

Os requisitos funcionais descrevem as funcionalidades que o sistema deve
oferecer, sob a perspectiva dos stakeholders.

| Código | Requisito funcional |
|---|---|
| RF01 | Manter cadastro de resíduos (tipo, descrição, quantidade em kg, setor de origem e data de geração) |
| RF02 | Manter cadastro de setores da organização |
| RF03 | Manter cadastro de pontos de coleta |
| RF04 | Manter cadastro de recicladores/cooperativas parceiros |
| RF05 | Registrar solicitação de coleta de resíduos |
| RF06 | Permitir o agendamento da coleta com data prevista |
| RF07 | Registrar a execução e o cancelamento da coleta, com controle de transição de status |
| RF08 | Consultar coletas por período, setor, ponto de coleta e status |
| RF09 | Gerar relatório de geração de resíduos por setor |
| RF10 | Gerar relatório de destinação (reciclado vs. disposto em aterro) |
| RF11 | Gerar relatório de desvio de aterro (indicador ambiental) |
| RF12 | Apresentar painel (dashboard) com indicadores consolidados |

## 6.6 Requisitos não funcionais

| Código | Requisito não funcional | Descrição |
|---|---|---|
| RNF01 | Disponibilidade dos serviços | API acessível via HTTP padrão, consumível por qualquer cliente compatível |
| RNF02 | Usabilidade | Interface web simples e de fácil navegação |
| RNF03 | Desempenho | Respostas da API em tempo aceitável para operações rotineiras |
| RNF04 | Portabilidade | Execução em ambiente Java padrão (multi-plataforma), sem dependência de infraestrutura específica |
| RNF05 | Manutenibilidade | Código organizado em camadas (controller, service, repository) |
| RNF06 | Confiabilidade de dados | Persistência dos registros em banco de dados com integridade referencial |
| RNF07 | Tratamento de erros | Respostas de erro padronizadas, informativas e com códigos HTTP adequados |

## 6.7 Arquitetura da solução

A aplicação segue o estilo arquitetural REST, no qual os recursos do
domínio são identificados por URLs e manipulados por métodos HTTP. O
sistema é dividido em três camadas lógicas:

1.  **Apresentação (aplicação consumidora):** aplicação web executada no
    navegador, responsável pela interface com o usuário e pela chamada aos
    Web Services via JavaScript.
2.  **Serviços (back-end):** API REST desenvolvida em Java com Spring
    Boot, responsável pelas regras de negócio, validações e persistência.
3.  **Dados:** banco de dados relacional que mantém as entidades do
    domínio.

A comunicação entre a aplicação consumidora e os serviços ocorre por meio
de troca de mensagens em formato JSON sobre HTTP. A separação em camadas
garante que cada parte evolua de forma independente, o que é um dos
benefícios didáticos e práticos do uso de Web Services.

```
┌────────────────────────────┐
│   Aplicação consumidora    │  (navegador: HTML/CSS/JavaScript)
│   (interface web)          │
└─────────────┬──────────────┘
              │  HTTP + JSON (REST)
┌─────────────▼──────────────┐
│   Web Services (Spring)    │  (controllers → services → repositories)
│   API REST                 │
└─────────────┬──────────────┘
              │  JDBC/JPA
┌─────────────▼──────────────┐
│   Banco de dados relacional│  (entidades do domínio)
└────────────────────────────┘
```

### 6.7.1 Padrão arquitetural: REST

O estilo REST (Representational State Transfer) foi definido por Fielding
(2000) como um estilo arquitetural para sistemas distribuídos
hipermídia. A escolha do REST em detrimento de outras tecnologias de Web
Services, como o SOAP, justifica-se por: simplicidade de uso com recursos
amplamente disponíveis, interoperabilidade com qualquer cliente HTTP,
formato JSON leve e legível, mecanismos de cache e escalabilidade, além
de uma comunidade ativa de desenvolvimento. Para o contexto acadêmico, o
REST também facilita a demonstração prática e o consumo pelos clientes
desenvolvidos no mesmo projeto.

### 6.7.2 Padrão de camadas no back-end

Internamente, a API organiza-se no padrão de camadas típico do Spring:

-   **Controller:** recebe as requisições HTTP, valida a entrada e
    devolve as respostas com os códigos de status adequados.
-   **Service:** concentra as regras de negócio, como a transição de
    status da coleta e o cálculo de indicadores.
-   **Repository:** abstrai o acesso ao banco de dados.
-   **Entity:** representa as tabelas do banco e os recursos do domínio.

### 6.7.3 Contrato de API (contrato de serviço)

Um contrato de API documenta os recursos, os métodos e os formatos de
dados aceitos e retornados. A definição explícita do contrato é um dos
elementos centrais de um sistema baseado em Web Services, pois permite
que consumidores e provedores evoluam com autonomia, respeitando as
mesmas regras.

## 6.8 Modelagem do domínio

A modelagem segue o paradigma de classes do Java, mapeado para tabelas
relacionais por meio da JPA.

| Entidade | Atributos principais | Observações |
|---|---|---|
| Setor | id, nome, departamento, localizacao | Representa a origem dos resíduos |
| Residuo | id, tipo, descricao, quantidadeKg, dataGeracao | Tipo: ORGANICO, RECICLAVEL, PERIGOSO, REJEITO |
| PontoColeta | id, nome, setor (origem), tipoResiduoAceito, capacidadeKg | Concentra resíduos até a destinação |
| Coleta | id, pontoColeta, dataSolicitacao, dataPrevista, quantidadeKg, status, recicladorDestino | Fluxo PENDENTE → AGENDADA → REALIZADA / CANCELADA |
| Reciclador | id, nome, tipo, cnpj, materiaisAceitos, ativo | Stakeholder externo de destinação |

### 6.8.1 Relacionamentos

-   Um `Setor` pode gerar muitos `Residuo` e possuir muitos `PontoColeta`;
-   Um `PontoColeta` pode originar muitas `Coleta` e ter um `Reciclador`
    como destino preferencial;
-   Uma `Coleta` referencia, opcionalmente, o `Reciclador` que recebeu o
    material.

### 6.8.2 Vocabulário de enumeração

As enumerações garantem consistência de dados: os tipos de resíduo e os
status de coleta são limitados a conjuntos definidos, evitando entradas
divergentes e simplificando os relatórios.

## 6.9 Web Services da aplicação

Cada recurso do domínio é exposto por meio de um conjunto de endpoints
REST, seguindo as boas práticas de nomenclatura (substantivos no plural,
sem verbos nas URLs).

### 6.9.1 Serviço de resíduos

| Método | Endpoint | Descrição |
|---|---|---|
| GET | /residuos | Lista resíduos (com filtros e paginação) |
| POST | /residuos | Registra um novo resíduo |
| GET | /residuos/{id} | Consulta um resíduo por identificador |
| PUT | /residuos/{id} | Atualiza os dados de um resíduo |
| DELETE | /residuos/{id} | Remove um registro de resíduo |

### 6.9.2 Serviço de setores

Padrão equivalente ao anterior no endpoint `/setores`.

### 6.9.3 Serviço de pontos de coleta

Padrão equivalente em `/pontos`.

### 6.9.4 Serviço de recicladores

Padrão equivalente em `/recicladores`.

### 6.9.5 Serviço de coletas

| Método | Endpoint | Descrição |
|---|---|---|
| GET | /coletas | Lista coletas com filtros por status, período e setor |
| POST | /coletas | Solicita uma nova coleta |
| GET | /coletas/{id} | Consulta uma coleta |
| PATCH | /coletas/{id}/status | Altera o status seguindo o fluxo do procedimento |

### 6.9.6 Serviço de relatórios

| Método | Endpoint | Descrição |
|---|---|---|
| GET | /relatorios/geracao-por-setor | Kg gerado por setor (com opção de mês) |
| GET | /relatorios/destinacao | Proporção reciclado vs. aterro com evolução temporal |
| GET | /relatorios/desvio-aterro | Indicador de desvio de aterro |

### 6.9.7 Exemplo de contrato (JSON)

Requisição de registro de resíduo:

```json
{
  "tipo": "RECICLAVEL",
  "descricao": "Papel branco de escritório",
  "quantidadeKg": 45.5,
  "setor": { "id": 3 }
}
```

Resposta de sucesso da criação (HTTP 201):

```json
{
  "id": 12,
  "tipo": "RECICLAVEL",
  "descricao": "Papel branco de escritório",
  "quantidadeKg": 45.5,
  "setor": { "id": 3, "nome": "Administrativo" },
  "dataGeracao": "2026-09-07T10:00:00"
}
```

Resposta de erro padronizada (HTTP 400):

```json
{
  "status": 400,
  "mensagem": "A quantidade informada deve ser maior que zero",
  "detalhes": ["quantidadeKg: deve ser maior que zero"],
  "dataHora": "2026-09-07T10:05:00"
}
```

## 6.10 Tratamento de erros e validações

O tratamento adequado de erros é um dos critérios de avaliação do
trabalho (item III.3 do edital). A API adota as seguintes práticas:

-   **Códigos HTTP semânticos:** 200/201 para sucesso, 400 para entrada
    inválida, 404 para recurso inexistente, 409 para conflito de regra de
    negócio e 500 para falhas inesperadas;
-   **Corpo de erro padronizado:** todos os erros retornam o formato
    `{ status, mensagem, detalhes, dataHora }`, o que facilita o
    tratamento no cliente;
-   **Validações de entrada:** uso de anotações de validação (tais como
    obrigatoriedade e faixa aceitável de valores) no contrato de dados;
-   **Consistência de regras de negócio:** validação da transição de
    status da coleta e do tipo de resíduo aceito pelo ponto de coleta.

## 6.11 Relatórios extras e indicadores ambientais

Os relatórios adicionais têm impacto direto na nota final, conforme o
edital. Três relatórios foram previstos:

1.  **Geração por setor:** consolida os quilogramas gerados por setor e
    período, apoiando a identificação das maiores fontes de resíduos e a
    definição de metas.
2.  **Destinação:** apresenta a proporção entre material reciclado e
    material disposto em aterro, com evolução temporal.
3.  **Desvio de aterro:** calcula o percentual de resíduos que deixaram
    de ser enviados ao aterro, um indicador amplamente utilizado em
    programas de sustentabilidade e em relatórios do SGA.

No frontend, esses dados serão apresentados em um dashboard com gráficos,
facilitando a leitura dos indicadores pelo gestor ambiental.

## 6.12 Ferramentas e tecnologias

| Componente | Tecnologia | Papel |
|---|---|---|
| Linguagem | Java | Implementação do back-end |
| Framework Web Services | Spring Boot | Criação da API REST |
| Persistência | Spring Data JPA (Hibernate) | Mapeamento objeto-relacional |
| Banco de dados | H2 (embutido) | Persistência sem instalação de servidor |
| Build | Maven | Gerenciamento de dependências e build |
| Aplicação consumidora | HTML, CSS e JavaScript | Interface web que consome a API |
| Formato de dados | JSON | Troca de informações entre cliente e servidor |
| Controle de versão | Git | Versionamento e trabalho em grupo |

A escolha do Java e do Spring Boot reflete a grande difusão da plataforma
no mercado e na academia, a facilidade de manutenção e a ampla oferta de
documentação, o que favorece a continuidade e o entendimento do projeto.
O H2 embutido elimina barreiras de instalação, permitindo que a
apresentação do programa em funcionamento seja feita de forma simples em
qualquer máquina (requisito do edital).

## 6.13 Interface da aplicação consumidora (telas previstas)

A aplicação web disponibilizará as seguintes telas:

1.  **Dashboard:** visão geral com indicadores (total de resíduos,
    desvio de aterro, coletas pendentes) e gráficos.
2.  **Resíduos:** listagem, cadastro, edição e exclusão de resíduos.
3.  **Setores:** cadastro e listagem de setores.
4.  **Pontos de coleta:** cadastro e listagem de pontos.
5.  **Recicladores:** cadastro e listagem de parceiros.
6.  **Coletas:** listagem, solicitação, agendamento e mudança de status.
7.  **Relatórios:** consulta aos relatórios gerenciais.

## 6.14 Cronograma de desenvolvimento

| Etapa | Atividades | Período estimado |
|---|---|---|
| 1. Concepção | Levantamento de requisitos, definição da proposta e aprovação do coordenador | Semana 1 |
| 2. Planejamento | Elaboração do presente plano, modelagem do domínio e definição do contrato da API | Semana 2 |
| 3. Estruturação | Criação do esqueleto do projeto, configuração do banco e mapeamento das entidades | Semana 2–3 |
| 4. Implementação | Controllers, services, validações e regras de negócio de todos os módulos | Semana 3–6 |
| 5. Relatórios | Implementação dos serviços de relatório e dos indicadores | Semana 6–7 |
| 6. Consumidor | Desenvolvimento da aplicação web e integração com a API | Semana 7–8 |
| 7. Testes | Testes funcionais, tratamento de erros e correções | Semana 8–9 |
| 8. Documentação | Dissertação, relatório de código e revisão final | Semana 9–10 |
| 9. Entrega | Impressão, encadernação e ficha de APS | Semana 10 |

## 6.15 Estratégia de testes

Os testes serão conduzidos em três níveis:

1.  **Testes da API:** verificação de cada endpoint com requisições
    válidas e inválidas, confirmando os códigos HTTP e o corpo de resposta.
2.  **Testes de regras de negócio:** validação das transições de status e
    das restrições de domínio (ex.: tipo de resíduo aceito).
3.  **Testes da aplicação consumidora:** execução das telas e conferência
    da integração ponta a ponta com a API.

Os cenários de teste serão registrados na forma de tabela (entrada, ação
esperada, resultado) e alguns deles poderão ser automatizados nos níveis
de service e controller.

## 6.16 Riscos e mitigação

| Risco | Mitigação |
|---|---|
| Divergência de dados entre setores cadastrados e a realidade | Validações e checklist de implantação por setor |
| Erro humano no registro de quantidades | Campos com validação, unidade padronizada (kg) e mensagens claras |
| Dependência de parceiros externos para a coleta | Cadastro de múltiplos recicladores e status do procedimento visível |
| Inconsistência entre o plano e a implementação | Desenvolvimento iterativo com revisão semanal |

## 6.17 Considerações finais do plano

Este plano define o escopo, a arquitetura, o contrato de serviços e o
cronograma da aplicação. Ele servirá como referência para as etapas
seguintes do trabalho: o projeto detalhado do programa (seção 7), o
relatório das linhas de código (seção 8) e a dissertação sobre os
elementos utilizados. O atendimento aos requisitos do edital — Web
Services implementados, aplicação consumidora, tratamento de erros,
relatórios adicionais e dissertação — é o norte de todo o
desenvolvimento, buscando unir o conhecimento técnico de
desenvolvimento de software à responsabilidade ambiental prevista na ISO
14000.

## Referências utilizadas neste plano

ABNT. **ABNT NBR ISO 14001**: Sistemas de gestão ambiental — Requisitos
com orientações para uso. Rio de Janeiro: ABNT, 2015.

BRASIL. **Lei nº 12.305, de 2 de agosto de 2010**: Institui a Política
Nacional de Resíduos Sólidos. Diário Oficial da União, Brasília, 2010.

FIELDING, Roy Thomas. **Architectural Styles and the Design of Network-
based Software Architectures**. 2000. Tese (Doutorado em Ciência da
Computação) — University of California, Irvine.

OLIVEIRA, Cesário de. **ISO 14000 e a gestão ambiental**: benefícios da
adoção das normas. São Paulo: FSP/USP, 2010.

RICHARDSON, Leonard; RUBY, Sam. **RESTful Web Services**. Sebastopol:
O'Reilly Media, 2007.