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