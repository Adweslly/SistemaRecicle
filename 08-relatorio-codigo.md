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