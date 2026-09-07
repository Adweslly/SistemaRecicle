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