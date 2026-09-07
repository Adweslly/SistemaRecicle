# Gestão de Resíduos Sólidos com Web Services

**Curso:** Ciência da Computação

**Disciplina:** Atividades Práticas Supervisionadas (APS) — 7º/8º semestre

**Tema:** Desenvolvimento de uma Aplicação Utilizando Web Services

**Grupo:**
- Nome do Aluno 1 — RA: __________
- Nome do Aluno 2 — RA: __________
- Nome do Aluno 3 — RA: __________

**Campus:** ______________ **Semestre:** ______ **Turno:** ____

---

# Índice

1. [Capa](#1-capa)
2. [Índice](#2-índice)
3. [Objetivo e motivação do trabalho](#3-objetivo-e-motivação-do-trabalho)
4. [Introdução](#4-introdução)
5. [Fundamentos e principais elementos dos Web Services](#5-fundamentos-e-principais-elementos-dos-web-services)
6. [Plano de desenvolvimento da aplicação](#6-plano-de-desenvolvimento-da-aplicação)
7. [Projeto (estrutura e módulos) do programa](#7-projeto-estrutura-e-módulos-do-programa)
8. [Relatório com as linhas de código do programa](#8-relatório-com-as-linhas-de-código-do-programa)
9. [Apresentação do programa em funcionamento](#9-apresentação-do-programa-em-funcionamento)
10. [Bibliografia](#10-bibliografia)
11. [Ficha de Atividades Práticas Supervisionadas](#11-ficha-de-atividades-práticas-supervisionadas)

---

# 3. Objetivo e motivação do trabalho

## 3.1 Objetivo geral

O presente trabalho, desenvolvido no âmbito das Atividades Práticas
Supervisionadas (APS), tem como objetivo geral desenvolver uma aplicação
baseada em Web Services, voltada à gestão ambiental de uma organização,
que minimize os impactos ambientais e aprimore os procedimentos entre os
stakeholders envolvidos, em consonância com a família de normas ISO
14000. Para isso, a aplicação implementa tanto os serviços — uma API REST
desenvolvida em Java com Spring Boot — quanto uma aplicação web
consumidora desses serviços, permitindo o registro, o controle e o
reporte do fluxo de resíduos sólidos da organização.

## 3.2 Objetivos específicos

-   Aplicar os fundamentos de Web Services, especialmente do estilo
    arquitetural REST, na construção de um sistema funcional;
-   Modelar e implementar um procedimento informatizado de gestão de
    resíduos que substitua o controle manual da organização;
-   Gerar indicadores ambientais — como geração por setor, taxa de
    reciclagem e desvio de aterro — a partir dos dados cadastrados;
-   Integrar conhecimentos de diferentes disciplinas do curso (banco de
    dados, redes de computadores, engenharia de software e gestão de
    projetos) em um único produto;
-   Documentar, por meio da dissertação, todos os elementos utilizados e
    o efeito do trabalho na formação acadêmica e profissional.

## 3.3 Motivação

A escolha do tema foi motivada por três frentes complementares. A
primeira é a sua aderência **técnica**: Web Services constituem um dos
pilares dos sistemas distribuídos modernos, sendo amplamente utilizados
na integração entre sistemas corporativos, aplicações móveis e sites. A
experiência de projetar, implementar e consumir uma API completa o ciclo
de aprendizado que as disciplinas teóricas sozinhas não proporcionam.

A segunda frente é a **relevância ambiental do contexto escolhido**. A
gestão adequada de resíduos sólidos é um dos aspectos ambientais mais
sensíveis da operação de qualquer empresa, e as normas ISO 14000 — em
especial a ISO 14001 — trazem esse controle para o centro do Sistema de
Gestão Ambiental. Ao aplicar a tecnologia a um problema real, o trabalho
deixa de ser apenas um exercício de programação e passa a entregar valor
para a sociedade e para as organizações.

A terceira frente é a **interdisciplinaridade**. O desenvolvimento da
aplicação exige a articulação de diferentes áreas do conhecimento —
redes de computadores (comunicação HTTP), bancos de dados (persistência e
consistência), engenharia de software (arquitetura em camadas, testes),
gestão ambiental (normas e indicadores) e comunicação escrita
(documentação técnica) —, o que espelha a realidade profissional, na qual
soluções tecnológicas raramente são construídas de forma isolada.

Dessa forma, a motivação central do trabalho é unir a formação técnica em
Ciência da Computação ao exercício da responsabilidade ambiental,
demonstrando que a tecnologia pode ser um instrumento concreto de
melhoria de processos, de redução de impactos e de aproximação entre as
partes interessadas de uma organização.

# 4. Introdução

## 4.1 Contextualização

A evolução da computação deixou para trás o isolamento dos sistemas
locais. Cada vez mais, os softwares precisam se comunicar entre si,
trocar dados e cooperar na execução de processos de negócio. Nesse
cenário, os Web Services — serviços disponíveis na web que podem ser
acessados por programas, e não apenas por navegadores — consolidaram-se
como a principal tecnologia de integração entre aplicações, por serem
baseados em padrões abertos e em protocolos amplamente suportados, como o
HTTP (RICHARDSON; RUBY, 2007).

Paralelamente, a sociedade passou a exigir das organizações uma postura
mais responsável diante dos impactos ambientais de suas atividades. A
família de normas ISO 14000 estabelece diretrizes para a implementação de
Sistemas de Gestão Ambiental (SGA), e a norma ISO 14001 especifica os
requisitos para que uma organização gerencie seus aspectos ambientais de
forma estruturada, contínua e mensurável (ABNT, 2015). Um dos aspectos
ambientais mais comuns e críticos nesse contexto é a geração e a
destinação de resíduos sólidos, cujo manejo adequado é também exigido
pela legislação brasileira (BRASIL, 2010).

## 4.2 O problema abordado

Dentro de uma organização, o controle de resíduos sólidos costuma
envolver diversos atores: setores que geram os resíduos, pontos de coleta
internos, a gestão ambiental e parceiros externos de destinação, como
cooperativas de reciclagem. Quando esse controle é feito de forma manual
— por planilhas, telefonemas e anotações —, perde-se confiabilidade nos
dados, dificulta-se o cálculo de indicadores e compromete-se a capacidade
de a organização demonstrar conformidade ambiental. O problema abordado
por este trabalho é, portanto, a ausência de um procedimento
informatizado, padronizado e rastreável de gestão de resíduos, que conecte
as partes interessadas e subsidie as decisões da gestão ambiental.

## 4.3 Solução proposta

Como solução, propõe-se uma aplicação baseada em Web Services, na qual
uma API REST desenvolve em Java com o framework Spring Boot expõe os
serviços responsáveis pelo cadastro de resíduos, setores, pontos de
coleta e recicladores, bem como pelo controle do fluxo de coletas e pela
geração de relatórios gerenciais. Uma aplicação web consumidora acessa
esses serviços via HTTP, oferecendo aos stakeholders uma interface para
registrar dados, acompanhar o andamento das coletas e visualizar os
indicadores ambientais. A arquitetura em serviços permite que o front-end
e o back-end evoluam de forma independente, reforçando os benefícios
didáticos e arquiteturais do uso de Web Services.

Além de atender aos requisitos funcionais da gestão de resíduos, o
sistema incorpora elementos valorizados pela avaliação do trabalho:
tratamento de erros padronizado, validação de regras de negócio,
relatórios adicionais com indicadores ambientais e uma interface de
dashboard com gráficos.

## 4.4 Fundamentação teórica e metodologia

Para o desenvolvimento, foram estudados os fundamentos dos Web Services,
destacando-se o estilo arquitetural REST, definido por Fielding (2000), e
sua implementação por meio do framework Spring Boot. No campo ambiental,
a fundamentação concentrou-se na norma ABNT NBR ISO 14001 e na Política
Nacional de Resíduos Sólidos (BRASIL, 2010). Do ponto de vista
metodológico, o trabalho seguiu as etapas de levantamento de requisitos,
modelagem do domínio, definição do contrato da API, implementação dos
serviços, construção da aplicação consumidora e testes funcionais,
conforme o plano de desenvolvimento apresentado na seção 6.

## 4.5 Organização do trabalho

O presente relatório está organizado da seguinte forma: a seção 3
apresenta o objetivo e a motivação do trabalho; a seção 4, esta
introdução; a seção 5 discorre sobre os fundamentos e principais
elementos dos Web Services; a seção 6 descreve o plano de desenvolvimento
da aplicação; a seção 7 apresenta o projeto (estrutura e módulos) do
programa; a seção 8 traz o relatório com as linhas de código; a seção 9
descreve a apresentação do programa em funcionamento; e a seção 10 reúne
a bibliografia utilizada. Ao final, discute-se ainda o efeito do trabalho
na formação acadêmica e a interdisciplinaridade envolvida no seu
desenvolvimento.

# 5. Fundamentos e principais elementos dos Web Services

## 5.1 Definição de Web Services

Um Web Service é um sistema de software projetado para suportar a
interoperabilidade entre máquinas em uma rede, expondo funcionalidades
que podem ser descobertas e consumidas por outras aplicações por meio de
padrões abertos de comunicação (W3C, 2004). Diferentemente de um site
convencional, cujo conteúdo é destinado à leitura humana em um navegador,
um Web Service é orientado a programas: a aplicação consumidora realiza
requisições e recebe respostas em formatos estruturados, processáveis por
código, como XML ou JSON.

Em termos práticos, um Web Service pode ser compreendido como uma
"função" disponibilizada na rede: a aplicação cliente envia os dados de
entrada, o serviço processa a requisição segundo as regras de negócio e
devolve os dados de saída. Essa abstração permite que sistemas
heterogêneos — desenvolvidos em linguagens diferentes, executados em
plataformas diferentes e mantidos por organizações diferentes — colaborem
entre si, o que explica a sua importância na integração de sistemas.

## 5.2 A evolução dos sistemas distribuídos e o papel dos Web Services

A primeira onda de integração entre sistemas baseou-se em tecnologias
proprietárias e fortemente acopladas, nas quais o cliente precisava
conhecer detalhes internos do servidor. Essa abordagem apresentava baixa
interoperabilidade e dificuldade de manutenção. Ao longo dos anos 1990,
surgiram alternativas como o RPC (Remote Procedure Call) e, depois,
tecnologias como CORBA e DCOM. Apesar de funcionais, dependiam de
infraestruturas específicas e de padrões pouco abertos.

A popularização da internet e a padronização do HTTP criaram as condições
para o surgimento dos Web Services, que se apoiaram em padrões abertos e
largamente difundidos, como URLs, HTTP, XML e, mais tarde, JSON. Esse
movimento insere-se no campo dos sistemas distribuídos, no qual processos
de diferentes máquinas cooperam para alcançar um objetivo comum,
comunicando-se por troca de mensagens (TANENBAUM; VAN STEEN, 2007). Essa
evolução culminou no conceito de Arquitetura Orientada a Serviços (SOA —
*Service-Oriented Architecture*), um estilo de arquitetura no qual as
funcionalidades de negócio são organizadas como serviços autônomos,
reutilizáveis e fracamente acoplados, podendo ser combinados para compor
processos de negócio mais amplos (ERL, 2005). Os Web Services são a
principal forma de materializá-los na prática.

## 5.3 Arquitetura de um Web Service

A arquitetura fundamental de um Web Service envolve três papéis:

1.  **Provedor de serviço:** o sistema que implementa a funcionalidade e
    disponibiliza o serviço na rede. No presente trabalho, o provedor é a
    API REST desenvolvida com Spring Boot.
2.  **Consumidor do serviço:** a aplicação que localiza e invoca o
    serviço para realizar uma determinada tarefa. No presente trabalho,
    é a aplicação web desenvolvida em HTML, CSS e JavaScript.
3.  **Registro (opcional):** um diretório onde os serviços são publicados
    e descobertos. Em serviços REST mais simples, essa etapa costuma ser
    dispensada, e o contrato é documentado diretamente.

A comunicação entre provedor e consumidor segue o modelo
requisição-resposta: o consumidor envia uma requisição HTTP com os dados
de entrada; o provedor processa, aplica as regras de negócio e devolve
uma resposta com os dados de saída e um código de status. Embora a
comunicação síncrona seja a mais comum, Web Services também podem operar
de forma assíncrona, quando a resposta é entregue posteriormente.

## 5.4 Protocolos e formatos de dados

### 5.4.1 O protocolo HTTP

O HTTP (*Hypertext Transfer Protocol*) é o protocolo base da web e o
meio de transporte dos Web Services. Define um conjunto de métodos —
também chamados verbos — que expressam a intenção da operação sobre um
recurso identificado por uma URL: GET (consulta), POST (criação), PUT
(atualização integral), PATCH (atualização parcial) e DELETE (remoção).
As respostas HTTP incluem códigos de status que indicam o resultado da
operação, como 200 (sucesso), 201 (criado), 400 (requisição inválida),
404 (não encontrado), 409 (conflito) e 500 (erro interno).

### 5.4.2 XML e JSON

O XML (*Extensible Markup Language*) foi o formato originalmente adotado
pelos Web Services tradicionais (SOAP), por sua flexibilidade na
definição de estruturas de dados e metadados. O JSON (*JavaScript Object
Notation*), por sua vez, é um formato leve, legível e diretamente
interpretável por JavaScript, o que o tornou dominante nos serviços REST
modernos. No presente trabalho, o JSON foi escolhido por sua simplicidade
e pelo suporte nativo das tecnologias empregadas.

## 5.5 As duas principais tecnologias de Web Services: SOAP e REST

### 5.5.1 SOAP

O SOAP (*Simple Object Access Protocol*) é um protocolo de mensagens
padronizado pela W3C, baseado em XML e frequentemente combinado com o
WSDL (*Web Services Description Language*), uma linguagem de descrição de
serviços, e com o UDDI, um registro para descoberta de serviços. A
principal força do SOAP é a padronização formal: mensagens estruturadas,
descrição de contratos e suporte a padrões de segurança e confiabilidade
de mensagem (WS-Security, WS-ReliableMessaging). A principal fraqueza é o
peso das mensagens XML e a complexidade de implementação, que o tornam
mais adequado a cenários corporativos que exigem rigoroso controle de
integridade e segurança.

### 5.5.2 REST

O REST (*Representational State Transfer*) não é um protocolo, mas um
estilo arquitetural proposto por Fielding (2000) para sistemas
distribuídos baseados em hipermídia. Fundamenta-se em seis restrições
arquiteturais:

1.  **Cliente-servidor:** a separação entre a interface (cliente) e o
    processamento (servidor) promove a evolução independente das partes;
2.  **Sem estado (stateless):** cada requisição contém toda a informação
    necessária para ser compreendida, sem depender de estado armazenado
    no servidor;
3.  **Cache:** respostas podem ser marcadas como cacheáveis, reduzindo a
    carga da rede;
4.  **Interface uniforme:** os recursos são manipulados por um conjunto
    fixo de operações (métodos HTTP), com representações e links
    padronizados;
5.  **Sistema em camadas:** a arquitetura pode ser organizada em camadas,
    como balanceadores e intermediários, sem que o cliente perceba;
6.  **Código sob demanda (opcional):** o cliente pode executar código
    enviado pelo servidor, como scripts.

Na prática, uma API REST trata os dados do domínio como *recursos*
identificados por URLs e operados pelos métodos HTTP: um item de resíduo,
por exemplo, é um recurso acessado em `/residuos/{id}`. Essa simplicidade
e aderência aos padrões da web conferem ao REST grande interoperabilidade
e curva de aprendizado reduzida.

### 5.5.3 Comparativo SOAP × REST

| Critério | SOAP | REST |
|---|---|---|
| Padronização | Formal (W3C) | Estilo arquitetural (Fielding) |
| Formato de mensagens | XML | JSON, XML, entre outros |
| Descoberta | UDDI + WSDL | Documentação/HATEOAS |
| Complexidade | Alta | Baixa |
| Segurança | WS-Security (robusto) | HTTPS + autenticação |
| Desempenho | Mensagens pesadas | Leve e cacheável |
| Casos de uso típicos | Integrações corporativas | APIs web, mobile, integrações ágeis |

No contexto deste trabalho, o REST foi selecionado por sua simplicidade,
facilidade de consumo por uma aplicação web e alinhamento com as
tecnologias utilizadas (Spring Boot e JavaScript), além de permitir
demonstrar com clareza os conceitos de recursos, métodos HTTP e resposta
JSON.

## 5.6 Componentes típicos de uma API REST

Uma API REST bem estruturada é composta por elementos recorrentes:

-   **Recursos e URLs:** representam as entidades do domínio por
    substantivos no plural e hierarquias claras (ex.: `/residuos`,
    `/coletas/{id}/status`);
-   **Métodos HTTP:** expressam a operação sobre os recursos;
-   **Status code:** comunicam o resultado da requisição de forma
    padronizada;
-   **Representações:** os corpos das mensagens em JSON, que descrevem o
    estado dos recursos;
-   **Validações:** a verificação da entrada antes da execução da regra
    de negócio;
-   **Tratamento de erros:** respostas de erro com estrutura consistente e
    mensagens compreensíveis;
-   **Paginação e filtros:** mecanismos para consultas volumosas;
-   **Autenticação e autorização (quando aplicável):** controle de acesso
    aos serviços.

## 5.7 Vantagens e limitações dos Web Services

As principais vantagens são: interoperabilidade entre plataformas e
linguagens; reutilização e modularidade; escalabilidade, pela separação
entre cliente e servidor; possibilidade de distribuição geográfica; e
facilidade de manutenção, quando os contratos são estáveis e bem
documentados. Como limitações, citam-se a dependência da disponibilidade
da rede e do serviço, a necessidade de planejamento cuidadoso dos
contratos para evitar quebras de compatibilidade e, em cenários com alto
volume, a latência da comunicação que acessa a rede.

## 5.8 Boas práticas adotadas no projeto

A partir dos fundamentos apresentados, o projeto adota um conjunto de
boas práticas: identificação de recursos por substantivos no plural;
uso adequado dos métodos HTTP; códigos de status semânticos; respostas de
erro padronizadas; validação sistemática das entradas; organização do
código em camadas (controller, service, repository); e documentação do
contrato da API. Essas práticas, detalhadas na seção 6, buscam traduzir
os conceitos teóricos em um sistema real, coeso e de fácil manutenção.

## Referências utilizadas nesta seção

ERL, Thomas. **Service-Oriented Architecture**: Concepts, Technology, and
Design. Upper Saddle River: Prentice Hall, 2005.

FIELDING, Roy Thomas. **Architectural Styles and the Design of Network-
based Software Architectures**. 2000. Tese (Doutorado em Ciência da
Computação) — University of California, Irvine.

RICHARDSON, Leonard; RUBY, Sam. **RESTful Web Services**. Sebastopol:
O'Reilly Media, 2007.

TANENBAUM, Andrew S.; VAN STEEN, Maarten. **Distributed Systems**:
Principles and Paradigms. 2. ed. Upper Saddle River: Pearson Prentice
Hall, 2007.

W3C. **Web Services Glossary**. W3C Working Group Note, 2004.
Disponível em: https://www.w3.org/TR/ws-gloss/. Acesso em: 2026.

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

# 8. Relatório com as linhas de código do programa

## 8.1 Visão geral do código

O programa foi desenvolvido em **Java 17** com **Spring Boot 3.5**, e a
aplicação consumidora em **HTML, CSS e JavaScript**. A tabela abaixo resume
a quantidade de linhas de código (LOC) de cada módulo. O total aproximado
é de **3.160 linhas** (1.581 em Java e 1.585 no front-end).

| Módulo (Java) | Arquivos | Linhas |
|---|---:|---:|
| Aplicação (`ResiduosApplication`) | 1 | 9 |
| Configuração (`config`) | 2 | 147 |
| Domínio (`domain`) | 8 | 466 |
| Persistência (`repository`) | 7 | 130 |
| Negócio (`service`) | 6 | 480 |
| API (`web` + `dto` + `exception`) | 12 | 356 |
| **Total Java** | **36** | **1.581** |

| Módulo (front-end) | Arquivos | Linhas |
|---|---:|---:|
| Páginas HTML (`static`) | 7 | 564 |
| Estilos (`css/style.css`) | 1 | 265 |
| JavaScript (`js`) | 7 | 756 |
| **Total front-end** | **15** | **1.585** |

A seguir, são apresentados os trechos de código mais representativos de
cada camada, acompanhados de comentários sobre o funcionamento.

## 8.2 Ponto de entrada da aplicação

O método `main` inicializa o contexto do Spring Boot, que realiza a
configuração automática dos Web Services, da persistência e da aplicação
consumidora (recursos estáticos):

```java
package com.aps.residuos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ResiduosApplication {

    public static void main(String[] args) {
        SpringApplication.run(ResiduosApplication.class, args);
    }

}
```

## 8.3 Camada de domínio (entidade)

As entidades mapeiam as tabelas do banco por meio da JPA. O exemplo a
seguir mostra a entidade `Residuo`, com as validações básicas dos campos e
o relacionamento com o setor de origem:

```java
@Entity
@Table(name = "residuo")
public class Residuo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private TipoResiduo tipo;

    @Column(nullable = false, length = 255)
    private String descricao;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal quantidadeKg;

    @Column(nullable = false)
    private LocalDateTime dataGeracao;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "setor_id")
    private Setor setor;

    // construtores, getters e setters omitidos
}
```

As enumerações (`TipoResiduo`, `TipoReciclador`, `StatusColeta`)
restringem os valores possíveis no banco, garantindo a consistência dos
dados cadastrados.

## 8.4 Camada de persistência (repositórios)

Os repositórios abstraem o acesso ao banco e incluem a consulta agregada
utilizada pelo relatório de geração por setor, escrita em JPQL:

```java
@Repository
public interface ResiduoRepository extends JpaRepository<Residuo, Long>,
        JpaSpecificationExecutor<Residuo> {

    @Query("""
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
    List<SetorGeracaoProjection> agregarPorSetor(Integer mes, Integer ano);
}
```

A consulta faz `GROUP BY` por setor e soma as quantidades em quilogramas,
com filtros opcionais de mês e ano, o que alimenta o indicador de geração
por setor do dashboard.

## 8.5 Camada de negócio (regras de transição de status)

A regra mais relevante do domínio é a transição de status da coleta. O
trecho abaixo mostra como o procedimento (PENDENTE → AGENDADA → REALIZADA,
com cancelamento) é validado:

```java
    private void transicionar(Coleta coleta, ColetaStatusRequest request) {
        StatusColeta atual = coleta.getStatus();
        StatusColeta novo = request.novoStatus();
        if (atual == novo) {
            throw new ApiException(HttpStatus.CONFLICT,
                    "A coleta ja possui o status " + novo);
        }

        Map<StatusColeta, StatusColeta[]> permitidas = Map.of(
                StatusColeta.PENDENTE, new StatusColeta[]{StatusColeta.AGENDADA, StatusColeta.CANCELADA},
                StatusColeta.AGENDADA, new StatusColeta[]{StatusColeta.REALIZADA, StatusColeta.CANCELADA});

        StatusColeta[] destinos = permitidas.get(atual);
        if (destinos == null || !List.of(destinos).contains(novo)) {
            throw new ApiException(HttpStatus.BAD_REQUEST,
                    "Transicao de " + atual + " para " + novo + " nao permitida");
        }

        coleta.setStatus(novo);
        switch (novo) {
            case AGENDADA -> {
                if (request.dataPrevista() == null) {
                    throw new ApiException(HttpStatus.BAD_REQUEST,
                            "Para agendar a coleta, a data prevista e obrigatoria");
                }
                coleta.setDataPrevista(request.dataPrevista());
            }
            case REALIZADA -> {
                if (coleta.getDataPrevista() == null) {
                    throw new ApiException(HttpStatus.BAD_REQUEST,
                            "A coleta precisa estar agendada antes de ser realizada");
                }
                coleta.setDataExecucao(LocalDateTime.now());
            }
            case CANCELADA -> {
                // transicao livre a partir de PENDENTE ou AGENDADA
            }
        }
    }
```

As transições inválidas (por exemplo, saltar de PENDENTE para REALIZADA)
são rejeitadas com erro padronizado.

## 8.6 Camada de API (controller REST)

Os controllers expõem os Web Services. O exemplo abaixo, do
`ResiduoController`, mostra a consulta com filtros e a criação de um novo
resíduo:

```java
@RestController
@RequestMapping("/residuos")
public class ResiduoController {

    private final ResiduoService service;

    @GetMapping
    public List<Residuo> listar(@RequestParam(required = false) Long setorId,
                                @RequestParam(required = false) String tipo,
                                @RequestParam(required = false) Integer mes,
                                @RequestParam(required = false) Integer ano) {
        return service.listar(setorId, tipo, mes, ano);
    }

    @PostMapping
    public ResponseEntity<Residuo> criar(@Valid @RequestBody ResiduoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.criar(request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
```

O uso de `@Valid` aciona as validações do contrato (como "quantidade deve
ser maior que zero"), automaticamente convertidas em HTTP 400 pelo
tratamento global de erros.

## 8.7 Tratamento de erros

Todas as exceções são convertidas em respostas padronizadas
`{ status, mensagem, detalhes, dataHora }`:

```java
@RestControllerAdvice
public class GlobalExceptionHandler {

    private ResponseEntity<Map<String, Object>> resposta(HttpStatus status, String mensagem,
                                                         Object detalhes) {
        Map<String, Object> corpo = new LinkedHashMap<>();
        corpo.put("status", status.value());
        corpo.put("mensagem", mensagem);
        corpo.put("detalhes", detalhes == null ? "[]" : detalhes);
        corpo.put("dataHora", LocalDateTime.now().toString());
        return ResponseEntity.status(status).body(corpo);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidation(MethodArgumentNotValidException ex) {
        String detalhes = ex.getBindingResult().getFieldErrors().stream()
                .map((FieldError f) -> f.getField() + ": " + f.getDefaultMessage())
                .collect(Collectors.joining("; "));
        return resposta(HttpStatus.BAD_REQUEST, "Dados invalidos na requisicao", detalhes);
    }

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<Map<String, Object>> handleApiException(ApiException ex) {
        return resposta(ex.getStatus(), ex.getMessage(), null);
    }
}
```

## 8.8 Aplicação consumidora (front-end)

A aplicação web consome os Web Services por meio da API `fetch` do
navegador. O módulo compartilhado `api.js` centraliza as chamadas e o
tratamento de erros:

```javascript
const API = {
    async request(url, options = {}) {
        const config = {
            headers: { 'Content-Type': 'application/json' },
            ...options
        };
        const resp = await fetch(this.base + url, config);
        if (!resp.ok) {
            let detalhe = 'Falha na requisicao';
            try {
                const corpo = await resp.json();
                detalhe = corpo.mensagem || corpo.detalhes || detalhe;
            } catch (e) { }
            throw new Error(detalhe + ' (HTTP ' + resp.status + ')');
        }
        if (resp.status === 204) return null;
        return resp.json();
    },
    get(url)  { return this.request(url); },
    post(url, dados) { return this.request(url, { method: 'POST', body: JSON.stringify(dados) }); },
    patch(url, dados) { return this.request(url, { method: 'PATCH', body: JSON.stringify(dados) }); }
};
```

Como exemplo de integração, a página de coletas chama o serviço de
transição de status (`PATCH /coletas/{id}/status`), traduzindo as opções
do usuário em requisições HTTP:

```javascript
async function confirmarStatus() {
    const body = {
        novoStatus: statusAlvo,
        dataPrevista: document.getElementById('dataPrevista').value || null,
        recicladorId: document.getElementById('recicladorIdStatus').value
            ? Number(document.getElementById('recicladorIdStatus').value) : null
    };
    try {
        await API.patch('/coletas/' + coletaAlvo + '/status', body);
        mostrarAlerta('Status da coleta atualizado para ' + statusAlvo + '!', 'sucesso');
        fecharFormularioStatus();
        carregar();
    } catch (erro) {
        mostrarAlerta(erro.message);
    }
}
```

As demais páginas (dashboard, setores, resíduos, pontos, recicladores e
relatórios) seguem a mesma estrutura, alternando listagens, formulários e
gráficos com a biblioteca Chart.js.

## 8.9 Considerações sobre o código

O código completo está disponível no repositório do projeto, organizado
nas pastas descritas na seção 7. Todos os endpoints foram testados por
meio de requisições HTTP, cobrindo os casos de sucesso e de erro,
conforme descrito na seção 6.15 do plano de desenvolvimento.

# 9. Apresentação do programa em funcionamento

## 9.1 Requisitos para execução

Para executar a aplicação, é necessário apenas o **Java 17 ou superior**
(já instalado na máquina de demonstração, versão 25 LTS) e o pacote `.jar`
gerado pelo Maven Wrapper. Não é necessária a instalação de banco de dados
nem de servidor web externos, pois o sistema utiliza o H2 embutido e
serve a aplicação consumidora a partir do próprio Spring Boot.

## 9.2 Passos para executar

1. Abrir o terminal na pasta `residuos-ws`;
2. Executar `.\mvnw.cmd spring-boot:run` (na primeira execução, o Maven
   Wrapper baixa as dependências automaticamente);
3. Aguardar a mensagem de inicialização ("Started ResiduosApplication");
4. Abrir no navegador o endereço `http://localhost:8080`;
5. O banco H2 é populado automaticamente com dados de demonstração.

## 9.3 Roteiro de demonstração

A apresentação do programa em funcionamento seguirá o roteiro abaixo,
percorrendo todas as funcionalidades pedidas e as extras:

1. **Dashboard** (`index.html`): indicadores consolidados — total de
   resíduos registrados, setores, coletas realizadas/pendentes, desvio de
   aterro e gráficos de geração por setor, destinação e tipos de resíduo;
2. **Setores**: cadastro, edição e exclusão de setores, demonstrando o
   bloqueio de duplicidade e de exclusão de setores vinculados;
3. **Resíduos**: registro com validação de quantidade e uso de filtros
   por setor e tipo;
4. **Pontos de Coleta**: cadastro com tipo de resíduo aceito e
   capacidade;
5. **Coletas**: solicitação (iniciando como PENDENTE), agendamento
   (AGENDADA), realização (REALIZADA) e cancelamento (CANCELADA),
   demonstrando a validação das transições e a rejeição de operações
   ilegais;
6. **Recicladores**: cadastro de cooperativas/empresas e inativação
   lógica de parceiros;
7. **Relatórios**: geração por setor, destinação (reciclado × aterro) e
   desvio de aterro, com filtro de período.

**Observação:** o resultado da apresentação em aula (data, participantes e
validação do professor) deverá ser registrado abaixo para compor a
dissertação.

- Data da apresentação: __________
- Validação / observações do avaliador: __________

---

# 10. Bibliografia

## Referências utilizadas no trabalho

ABNT. **ABNT NBR ISO 14001**: Sistemas de gestão ambiental — Requisitos
com orientações para uso. Rio de Janeiro: ABNT, 2015.

BRASIL. **Lei nº 12.305, de 2 de agosto de 2010**: Institui a Política
Nacional de Resíduos Sólidos. Diário Oficial da União, Brasília, 2010.

ERL, Thomas. **Service-Oriented Architecture**: Concepts, Technology, and
Design. Upper Saddle River: Prentice Hall, 2005.

FIELDING, Roy Thomas. **Architectural Styles and the Design of Network-
based Software Architectures**. 2000. Tese (Doutorado em Ciência da
Computação) — University of California, Irvine.

OLIVEIRA, Cesário de. **ISO 14000 e a gestão ambiental**: benefícios da
adoção das normas. São Paulo: FSP/USP, 2010.

PIVOTAL SOFTWARE. **Spring Boot Reference Documentation**. Disponível em:
https://docs.spring.io/spring-boot/. Acesso em: 2026.

PRESSMAN, Roger S. **Engenharia de Software**: uma abordagem
profissional. 8. ed. Porto Alegre: AMGH, 2016.

RICHARDSON, Leonard; RUBY, Sam. **RESTful Web Services**. Sebastopol:
O'Reilly Media, 2007.

TANENBAUM, Andrew S.; VAN STEEN, Maarten. **Distributed Systems**:
Principles and Paradigms. 2. ed. Upper Saddle River: Pearson Prentice
Hall, 2007.

W3C. **Web Services Glossary**. W3C Working Group Note, 2004. Disponível
em: https://www.w3.org/TR/ws-gloss/. Acesso em: 2026.

# 11. Ficha de Atividades Práticas Supervisionadas - APS

**Atividades Práticas Supervisionadas** (laboratórios, atividades em
biblioteca, iniciação científica, trabalhos individuais e em grupo,
práticas de ensino e outras)

**NOME:** ______________________________________________________

**RA:** ______________ **CURSO:** _______________________________

**CAMPUS:** ____________________ **SEMESTRE:** ______ **TURNO:** ____

## Registro de atividades

| DATA | ATIVIDADE | TOTAL DE HORAS | ASSINATURA — ALUNO | ASSINATURA — PROFESSOR |
|---|---|---|---|---|
|      |           |                |                    |                        |
|      |           |                |                    |                        |
|      |           |                |                    |                        |
|      |           |                |                    |                        |
|      |           |                |                    |                        |
|      |           |                |                    |                        |
|      |           |                |                    |                        |
|      |           |                |                    |                        |

**TOTAL DE HORAS:** ______________

