(function () {
    let statusAlvo = null;   // status de destino da transicao em andamento
    let coletaAlvo = null;   // id da coleta em transicao

    async function carregarOpcoes() {
        const [pontos, recicladores] = await Promise.all([
            API.get('/pontos'),
            API.get('/recicladores?apenasAtivos=true')
        ]);
        preencherSelect('pontoColetaId', pontos, p => p.nome + ' (' + p.tipoResiduoAceito + ')', p => p.id);
        preencherSelect('recicladorId', recicladores, r => r.nome, r => r.id);
        preencherSelect('recicladorIdStatus', recicladores, r => r.nome, r => r.id);
        preencherSelect('filtro-status', STATUS_COLETA.map(s => ({ s })), x => x.s, x => x.s, 'Todos os status');
    }

    function carregarTipos() {
        document.getElementById('tipoResiduo').innerHTML = '<option value="">Selecione...</option>' +
            TIPOS_RESIDUO.map(t => `<option value="${t}">${t}</option>`).join('');
    }

    async function atualizarTipoAceito() {
        const select = document.getElementById('pontoColetaId');
        const pontos = JSON.parse(select.dataset.pontos || '[]');
        const ponto = pontos.find(p => String(p.id) === select.value);
        document.getElementById('tipoAceitoExibicao').value = ponto ? ponto.tipoResiduoAceito : '';
        document.getElementById('tipoResiduo').value = ponto ? ponto.tipoResiduoAceito : '';
    }

    async function carregar() {
        try {
            const status = document.getElementById('filtro-status').value;
            const mes = document.getElementById('filtro-mes').value;
            const ano = document.getElementById('filtro-ano').value;
            const params = new URLSearchParams();
            if (status) params.set('status', status);
            if (mes) params.set('mes', mes);
            if (ano) params.set('ano', ano);

            const dados = await API.get('/coletas?' + params.toString());
            const tbody = document.getElementById('linhas');
            tbody.innerHTML = dados.length
                ? dados.map(c => `
                    <tr>
                        <td>${c.id}</td>
                        <td>${escaparHtml(c.pontoColeta?.nome) || '-'}</td>
                        <td>${escaparHtml(c.pontoColeta?.setor?.nome) || '-'}</td>
                        <td><span class="badge ${c.tipoResiduo}">${c.tipoResiduo}</span></td>
                        <td>${formatarKg(c.quantidadeKg)}</td>
                        <td>${formatarData(c.dataSolicitacao)}</td>
                        <td>${formatarData(c.dataPrevista)}</td>
                        <td>${escaparHtml(c.recicladorDestino?.nome) || '-'}</td>
                        <td><span class="badge ${c.status}">${c.status}</span></td>
                        <td>${botoesAcao(c)}</td>
                    </tr>`).join('')
                : '<tr><td colspan="10" class="vazio">Nenhuma coleta encontrada</td></tr>';
        } catch (erro) {
            mostrarAlerta(erro.message);
        }
    }

    function botoesAcao(c) {
        let botoes = '';
        if (c.status === 'PENDENTE') {
            botoes += `<button class="btn pequeno" onclick="abrirStatus(${c.id},'AGENDADA')">Agendar</button>`;
            botoes += `<button class="btn pequeno perigo" onclick="confirmarDireto(${c.id},'CANCELADA')">Cancelar</button>`;
        } else if (c.status === 'AGENDADA') {
            botoes += `<button class="btn pequeno" onclick="abrirStatus(${c.id},'REALIZADA')">Realizar</button>`;
            botoes += `<button class="btn pequeno perigo" onclick="confirmarDireto(${c.id},'CANCELADA')">Cancelar</button>`;
        }
        return botoes || '<span style="color:#9ca3af">—</span>';
    }

    function abrirStatus(id, novoStatus) {
        coletaAlvo = id;
        statusAlvo = novoStatus;
        document.getElementById('status-titulo').textContent =
            (novoStatus === 'AGENDADA' ? 'Agendar coleta #' : 'Realizar coleta #') + id;
        document.getElementById('btn-status').textContent =
            novoStatus === 'AGENDADA' ? 'Confirmar agendamento' : 'Confirmar realização';
        const dataPrevista = document.getElementById('dataPrevista');
        dataPrevista.value = novoStatus === 'AGENDADA'
            ? new Date(Date.now() + 86400000).toISOString().slice(0, 16)
            : '';
        dataPrevista.disabled = novoStatus !== 'AGENDADA';
        document.getElementById('formulario-status').classList.add('visivel');
    }

    function fecharFormularioStatus() {
        document.getElementById('formulario-status').classList.remove('visivel');
        coletaAlvo = null;
        statusAlvo = null;
    }

    async function confirmarStatus() {
        if (!coletaAlvo) return;
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

    async function confirmarDireto(id, novoStatus) {
        if (!confirm('Cancelar a coleta #' + id + '?')) return;
        try {
            await API.patch('/coletas/' + id + '/status', { novoStatus });
            mostrarAlerta('Coleta cancelada.', 'sucesso');
            carregar();
        } catch (erro) {
            mostrarAlerta(erro.message);
        }
    }

    async function salvarNova() {
        const dados = {
            pontoColetaId: document.getElementById('pontoColetaId').value
                ? Number(document.getElementById('pontoColetaId').value) : null,
            tipoResiduo: document.getElementById('tipoResiduo').value,
            quantidadeKg: document.getElementById('quantidadeKg').value,
            recicladorId: document.getElementById('recicladorId').value
                ? Number(document.getElementById('recicladorId').value) : null
        };
        try {
            await API.post('/coletas', dados);
            mostrarAlerta('Coleta solicitada com sucesso!', 'sucesso');
            fecharFormulario();
            carregar();
        } catch (erro) {
            mostrarAlerta(erro.message);
        }
    }

    function abrirFormulario() {
        document.getElementById('formulario').classList.add('visivel');
    }

    function fecharFormulario() {
        document.getElementById('formulario').classList.remove('visivel');
    }

    window.carregar = carregar;
    window.salvarNova = salvarNova;
    window.abrirFormulario = abrirFormulario;
    window.fecharFormulario = fecharFormulario;
    window.abrirStatus = abrirStatus;
    window.fecharFormularioStatus = fecharFormularioStatus;
    window.confirmarStatus = confirmarStatus;
    window.confirmarDireto = confirmarDireto;
    window.atualizarTipoAceito = atualizarTipoAceito;

    document.addEventListener('DOMContentLoaded', async () => {
        carregarTipos();
        try {
            await carregarOpcoes();
            const pontos = await API.get('/pontos');
            const select = document.getElementById('pontoColetaId');
            select.dataset.pontos = JSON.stringify(pontos);
            await carregar();
        } catch (erro) {
            mostrarAlerta(erro.message);
        }
    });
})();