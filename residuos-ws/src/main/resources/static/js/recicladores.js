(function () {
    let editandoId = null;

    function carregarTipos() {
        document.getElementById('tipo').innerHTML = '<option value="">Selecione...</option>' +
            TIPOS_RECICLADOR.map(t => `<option value="${t}">${t}</option>`).join('');
    }

    async function carregar() {
        try {
            const apenasAtivos = document.getElementById('apenasAtivos').checked;
            const dados = await API.get('/recicladores?apenasAtivos=' + apenasAtivos);
            const tbody = document.getElementById('linhas');
            tbody.innerHTML = dados.length
                ? dados.map(r => `
                    <tr>
                        <td>${r.id}</td>
                        <td>${escaparHtml(r.nome)}</td>
                        <td>${escaparHtml(r.tipo)}</td>
                        <td>${escaparHtml(r.cnpj)}</td>
                        <td>${escaparHtml(r.materiaisAceitos) || '-'}</td>
                        <td>${r.ativo ? '<span class="badge REALIZADA">Ativo</span>' : '<span class="badge CANCELADA">Inativo</span>'}</td>
                        <td>
                            <button class="btn pequeno" onclick="editar(${r.id})">Editar</button>
                            <button class="btn pequeno perigo" onclick="excluir(${r.id})">Inativar</button>
                        </td>
                    </tr>`).join('')
                : '<tr><td colspan="7" class="vazio">Nenhum reciclador encontrado</td></tr>';
        } catch (erro) {
            mostrarAlerta(erro.message);
        }
    }

    async function editar(id) {
        try {
            const r = await API.get('/recicladores/' + id);
            editandoId = id;
            document.getElementById('nome').value = r.nome;
            document.getElementById('tipo').value = r.tipo;
            document.getElementById('cnpj').value = r.cnpj;
            document.getElementById('materiaisAceitos').value = r.materiaisAceitos || '';
            document.getElementById('ativo').value = String(r.ativo);
            document.getElementById('form-titulo').textContent = 'Editar reciclador #' + id;
            abrirFormulario();
        } catch (erro) {
            mostrarAlerta(erro.message);
        }
    }

    async function salvar() {
        const dados = {
            nome: document.getElementById('nome').value.trim(),
            tipo: document.getElementById('tipo').value,
            cnpj: document.getElementById('cnpj').value.trim(),
            materiaisAceitos: document.getElementById('materiaisAceitos').value.trim(),
            ativo: document.getElementById('ativo').value === 'true'
        };
        try {
            if (editandoId) {
                await API.put('/recicladores/' + editandoId, dados);
                mostrarAlerta('Reciclador atualizado com sucesso!', 'sucesso');
            } else {
                await API.post('/recicladores', dados);
                mostrarAlerta('Reciclador cadastrado com sucesso!', 'sucesso');
            }
            fecharFormulario();
            carregar();
        } catch (erro) {
            mostrarAlerta(erro.message);
        }
    }

    async function excluir(id) {
        if (!confirm('Inativar este reciclador?')) return;
        try {
            await API.del('/recicladores/' + id);
            mostrarAlerta('Reciclador inativado.', 'sucesso');
            carregar();
        } catch (erro) {
            mostrarAlerta(erro.message);
        }
    }

    function abrirFormulario() {
        editandoId = null;
        document.getElementById('form-titulo').textContent = 'Novo reciclador';
        document.getElementById('nome').value = '';
        document.getElementById('tipo').value = '';
        document.getElementById('cnpj').value = '';
        document.getElementById('materiaisAceitos').value = '';
        document.getElementById('ativo').value = 'true';
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

    document.addEventListener('DOMContentLoaded', () => {
        carregarTipos();
        carregar().catch(erro => mostrarAlerta(erro.message));
    });
})();