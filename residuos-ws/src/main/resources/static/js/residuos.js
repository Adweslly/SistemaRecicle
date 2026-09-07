(function () {
    let editandoId = null;

    async function carregarSetores() {
        const setores = await API.get('/setores');
        preencherSelect('setorId', setores, s => s.nome, s => s.id);
        preencherSelect('filtro-setor', setores, s => s.nome, s => s.id, 'Todos os setores');
    }

    function carregarTipos() {
        const selectTipo = document.getElementById('tipo');
        selectTipo.innerHTML = '<option value="">Selecione...</option>' +
            TIPOS_RESIDUO.map(t => `<option value="${t}">${t}</option>`).join('');
        document.getElementById('filtro-tipo').innerHTML = '<option value="">Todos os tipos</option>' +
            TIPOS_RESIDUO.map(t => `<option value="${t}">${t}</option>`).join('');
    }

    async function carregar() {
        try {
            const params = new URLSearchParams();
            const setorId = document.getElementById('filtro-setor').value;
            const tipo = document.getElementById('filtro-tipo').value;
            if (setorId) params.set('setorId', setorId);
            if (tipo) params.set('tipo', tipo);

            const dados = await API.get('/residuos?' + params.toString());
            const tbody = document.getElementById('linhas');
            tbody.innerHTML = dados.length
                ? dados.map(r => `
                    <tr>
                        <td>${r.id}</td>
                        <td><span class="badge ${r.tipo}">${r.tipo}</span></td>
                        <td>${escaparHtml(r.descricao)}</td>
                        <td>${formatarKg(r.quantidadeKg)}</td>
                        <td>${escaparHtml(r.setor?.nome) || '-'}</td>
                        <td>${formatarData(r.dataGeracao)}</td>
                        <td>
                            <button class="btn pequeno" onclick="editar(${r.id})">Editar</button>
                            <button class="btn pequeno perigo" onclick="excluir(${r.id})">Excluir</button>
                        </td>
                    </tr>`).join('')
                : '<tr><td colspan="7" class="vazio">Nenhum resíduo encontrado</td></tr>';
        } catch (erro) {
            mostrarAlerta(erro.message);
        }
    }

    async function editar(id) {
        try {
            const r = await API.get('/residuos/' + id);
            editandoId = id;
            document.getElementById('tipo').value = r.tipo;
            document.getElementById('descricao').value = r.descricao;
            document.getElementById('quantidadeKg').value = r.quantidadeKg;
            document.getElementById('setorId').value = r.setor.id;
            document.getElementById('form-titulo').textContent = 'Editar resíduo #' + id;
            abrirFormulario();
        } catch (erro) {
            mostrarAlerta(erro.message);
        }
    }

    async function salvar() {
        const dados = {
            tipo: document.getElementById('tipo').value,
            descricao: document.getElementById('descricao').value.trim(),
            quantidadeKg: document.getElementById('quantidadeKg').value,
            setorId: document.getElementById('setorId').value ? Number(document.getElementById('setorId').value) : null
        };
        try {
            if (editandoId) {
                await API.put('/residuos/' + editandoId, dados);
                mostrarAlerta('Resíduo atualizado com sucesso!', 'sucesso');
            } else {
                await API.post('/residuos', dados);
                mostrarAlerta('Resíduo registrado com sucesso!', 'sucesso');
            }
            fecharFormulario();
            carregar();
        } catch (erro) {
            mostrarAlerta(erro.message);
        }
    }

    async function excluir(id) {
        if (!confirm('Excluir este registro?')) return;
        try {
            await API.del('/residuos/' + id);
            mostrarAlerta('Resíduo excluído.', 'sucesso');
            carregar();
        } catch (erro) {
            mostrarAlerta(erro.message);
        }
    }

    function abrirFormulario() {
        editandoId = null;
        document.getElementById('form-titulo').textContent = 'Novo resíduo';
        document.getElementById('tipo').value = '';
        document.getElementById('descricao').value = '';
        document.getElementById('quantidadeKg').value = '';
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
    window.carregar = carregar;

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