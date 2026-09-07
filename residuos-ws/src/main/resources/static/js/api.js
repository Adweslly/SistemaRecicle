// Helpers compartilhados da aplicacao consumidora.
const API = {
    base: '',

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
            } catch (e) { /* resposta vazia */ }
            throw new Error(detalhe + ' (HTTP ' + resp.status + ')');
        }
        if (resp.status === 204) return null;
        return resp.json();
    },

    get(url)  { return this.request(url); },
    post(url, dados) { return this.request(url, { method: 'POST', body: JSON.stringify(dados) }); },
    put(url, dados)  { return this.request(url, { method: 'PUT', body: JSON.stringify(dados) }); },
    patch(url, dados) { return this.request(url, { method: 'PATCH', body: JSON.stringify(dados) }); },
    del(url)  { return this.request(url, { method: 'DELETE' }); }
};

// Formata datas no padrao brasileiro.
function formatarData(iso) {
    if (!iso) return '-';
    const d = new Date(iso);
    return d.toLocaleDateString('pt-BR') + ' ' + d.toLocaleTimeString('pt-BR', { hour: '2-digit', minute: '2-digit' });
}

function formatarDataCurta(iso) {
    if (!iso) return '-';
    return new Date(iso).toLocaleDateString('pt-BR');
}

function formatarKg(valor) {
    return Number(valor).toLocaleString('pt-BR', { minimumFractionDigits: 2, maximumFractionDigits: 2 }) + ' kg';
}

function mostrarAlerta(mensagem, tipo = 'erro') {
    const el = document.getElementById('alerta');
    if (!el) return;
    el.textContent = mensagem;
    el.className = 'alerta visivel ' + tipo;
    el.scrollIntoView({ behavior: 'smooth', block: 'center' });
    setTimeout(() => { el.className = 'alerta'; }, 6000);
}

function escaparHtml(texto) {
    return String(texto ?? '').replace(/[&<>"']/g,
        c => ({ '&': '&amp;', '<': '&lt;', '>': '&gt;', '"': '&quot;', "'": '&#39;' }[c]));
}

// Preenche um <select> a partir de uma lista de opcoes.
function preencherSelect(selectId, opcoes, rotuloFn, valorFn, placeholder = 'Selecione...') {
    const sel = document.getElementById(selectId);
    if (!sel) return;
    sel.innerHTML = '<option value="">' + placeholder + '</option>' +
        opcoes.map(o => '<option value="' + valorFn(o) + '">' + rotuloFn(o) + '</option>').join('');
}

const TIPOS_RESIDUO = ['ORGANICO', 'RECICLAVEL', 'PERIGOSO', 'REJEITO'];
const TIPOS_RECICLADOR = ['COOPERATIVA', 'EMPRESA'];
const STATUS_COLETA = ['PENDENTE', 'AGENDADA', 'REALIZADA', 'CANCELADA'];