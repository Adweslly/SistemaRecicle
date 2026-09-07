(function () {
    let editandoId = null;

    async function carregar() {
        try {
            const dados = await API.get('/setores');
            const tbody = document.getElementById('linhas');
            tbody.innerHTML = dados.length
                ? dados.map(s => `
                    <tr>
                        <td>${s.id}</td>
                        <td>${escaparHtml(s.nome)}</td>
                        <td>${escaparHtml(s.departamento) || '-'}</td>
                        <td>${escaparHtml(s.localizacao) || '-'}</td>
                        <td>
                            <button class="btn pequeno" onclick="editar(${s.id})">Editar</button>
                            <button class="btn pequeno perigo" onclick="excluir(${s.id})">Excluir</button>
                        </td>
                    </tr>`).join('')
                : '<tr><td colspan="5" class="vazio">Nenhum setor cadastrado</td></tr>';
        } catch (erro) {
            mostrarAlerta(erro.message);
        }
    }

    async function editar(id) {
        try {
            const s = await API.get('/setores/' + id);
            editandoId = id;
            document.getElementById('nome').value = s.nome;
            document.getElementById('departamento').value = s.departamento || '';
            document.getElementById('localizacao').value = s.localizacao || '';
            document.getElementById('form-titulo').textContent = 'Editar setor #' + id;
            abrirFormulario();
        } catch (erro) {
            mostrarAlerta(erro.message);
        }
    }

    async function salvar() {
        const dados = {
            nome: document.getElementById('nome').value.trim(),
            departamento: document.getElementById('departamento').value.trim(),
            localizacao: document.getElementById('localizacao').value.trim()
        };
        try {
            if (editandoId) {
                await API.put('/setores/' + editandoId, dados);
                mostrarAlerta('Setor atualizado com sucesso!', 'sucesso');
            } else {
                await API.post('/setores', dados);
                mostrarAlerta('Setor cadastrado com sucesso!', 'sucesso');
            }
            fecharFormulario();
            carregar();
        } catch (erro) {
            mostrarAlerta(erro.message);
        }
    }

    async function excluir(id) {
        if (!confirm('Excluir este setor?')) return;
        try {
            await API.del('/setores/' + id);
            mostrarAlerta('Setor excluído.', 'sucesso');
            carregar();
        } catch (erro) {
            mostrarAlerta(erro.message);
        }
    }

    function abrirFormulario() {
        editandoId = null;
        document.getElementById('form-titulo').textContent = 'Novo setor';
        document.getElementById('nome').value = '';
        document.getElementById('departamento').value = '';
        document.getElementById('localizacao').value = '';
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

    carregar();
})();