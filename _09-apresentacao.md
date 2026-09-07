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