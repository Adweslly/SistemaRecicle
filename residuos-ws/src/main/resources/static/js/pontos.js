(function () {
    let editandoId = null;

    async function carregarSetores() {
        const setores = await API.get('/setores');
        preencherSelect('setorId', setores, s => s.nome, s => s.id);
    }

    function carregarTipos() {
        document.getElementById('tipoResiduoAceito').innerHTML = '<option value="">Selecione...</option>' +
            TIPOS_RESIDUO.map(t => `<option value="${t}">${t}</option>`).join('');
    }

    async function carregar() {
        try {
            const dados = await API.get('/pontos');
            const tbody = document.getElementById('linhas');
            tbody.innerHTML = dados.length
                ? dados.map(p => `
                    <tr>
                        <td>${p.id}</td>
                        <td>${escaparHtml(p.nome)}</td>
                        <td><span class="badge ${p.tipoResiduoAceito}">${p.tipoResiduoAceito}</span></td>
                        <td>${formatarKg(p.capacidadeKg)}</td>
                        <td>${escaparHtml(p.setor?.nome) || '-'}</td>
                        <td>
                            <button class="btn pequeno" onclick="editar(${p.id})">Editar</button>
                            <button class="btn pequeno perigo" onclick="excluir(${p.id})">Excluir</button>
                        </td>
                    </tr>`).join('')
                : '<tr><td colspan="6" class="vazio">Nenhum ponto de coleta cadastrado</td></tr>';
        } catch (erro) {
            mostrarAlerta(erro.message);
        }
    }

    async function editar(id) {
        try {
            const p = await API.get('/pontos/' + id);
            editandoId = id;
            document.getElementById('nome').value = p.nome;
            document.getElementById('tipoResiduoAceito').value = p.tipoResiduoAceito;
            document.getElementById('capacidadeKg').value = p.capacidadeKg;
            document.getElementById('setorId').value = p.setor.id;
            document.getElementById('form-titulo').textContent = 'Editar ponto de coleta #' + id;
            abrirFormulario();
        } catch (erro) {
            mostrarAlerta(erro.message);
        }
    }

    async function salvar() {
        const dados = {
            nome: document.getElementById('nome').value.trim(),
            tipoResiduoAceito: document.getElementById('tipoResiduoAceito').value,
            capacidadeKg: document.getElementById('capacidadeKg').value,
            setor: { id: document.getElementById('setorId').value ? Number(document.getElementById('setorId').value) : null }
        };
        try {
            if (editandoId) {
                await API.put('/pontos/' + editandoId, dados);
                mostrarAlerta('Ponto de coleta atualizado com sucesso!', 'sucesso');
            } else {
                await API.post('/pontos', dados);
                mostrarAlerta('Ponto de coleta cadastrado com sucesso!', 'sucesso');
            }
            fecharFormulario();
            carregar();
        } catch (erro) {
            mostrarAlerta(erro.message);
        }
    }

    async function excluir(id) {
        if (!confirm('Excluir este ponto de coleta?')) return;
        try {
            await API.del('/pontos/' + id);
            mostrarAlerta('Ponto de coleta excluído.', 'sucesso');
            carregar();
        } catch (erro) {
            mostrarAlerta(erro.message);
        }
    }

    function abrirFormulario() {
        editandoId = null;
        document.getElementById('form-titulo').textContent = 'Novo ponto de coleta';
        document.getElementById('nome').value = '';
        document.getElementById('tipoResiduoAceito').value = '';
        document.getElementById('capacidadeKg').value = '';
        document.getElementById('setorId').value = '';
        document.getElementById('formulario').classList.add('visivel');
    }

    function fecharFormulario() {
        document.getElementById('formulario').classList.remove('visivel');
    }

    window.editar = editar;
    window.excluir = excluir;
    window.abrirFormulario = abrirFormulario;
    window.fecharFormulario = fecharFormulario;
    window.salvar = salvar;

    document.addEventListener('DOMContentLoaded', async () => {
        carregarTipos();
        try {
            await carregarSetores();
            await carregar();
        } catch (erro) {
            mostrarAlerta(erro.message);
        }
    });
})();