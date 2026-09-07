(function () {
    let graficoTipo = {};

    function params() {
        const p = new URLSearchParams();
        const mes = document.getElementById('filtro-mes').value;
        const ano = document.getElementById('filtro-ano').value;
        if (mes) p.set('mes', mes);
        if (ano) p.set('ano', ano);
        return p.toString();
    }

    async function carregar() {
        try {
            const q = params();
            const [porSetor, destinacao, desvio] = await Promise.all([
                API.get('/relatorios/geracao-por-setor?' + q),
                API.get('/relatorios/destinacao?' + q),
                API.get('/relatorios/desvio-aterro?' + q)
            ]);

            document.getElementById('total-coletado').textContent = formatarKg(destinacao.totalKg);
            document.getElementById('total-reciclado').textContent = formatarKg(destinacao.recicladoKg);
            document.getElementById('total-aterro').textContent = formatarKg(destinacao.aterroKg);
            document.getElementById('desvio-aterro').textContent =
                Number(desvio.desvioAterroPercentual).toLocaleString('pt-BR') + '%';

            desenharGrafico('grafico-setores', 'bar',
                porSetor.map(s => s.setorNome), porSetor.map(s => s.totalKg),
                'Setores', '#16a34a');

            desenharGrafico('grafico-destinacao', 'doughnut',
                ['Reciclado', 'Aterro'],
                [destinacao.recicladoKg, destinacao.aterroKg],
                'Destino', ['#16a34a', '#9ca3af']);

            const tbody = document.getElementById('linhas-setores');
            tbody.innerHTML = porSetor.length
                ? porSetor.map(s => `
                    <tr>
                        <td>${escaparHtml(s.setorNome)}</td>
                        <td>${formatarKg(s.totalKg)}</td>
                        <td>${s.totalItens}</td>
                    </tr>`).join('')
                : '<tr><td colspan="3" class="vazio">Sem dados para o período</td></tr>';
        } catch (erro) {
            mostrarAlerta(erro.message);
        }
    }

    function desenharGrafico(id, tipo, labels, valores, rotulo, cor) {
        if (typeof Chart === 'undefined') return;
        const canvas = document.getElementById(id);
        if (!canvas) return;
        if (graficoTipo[id]) graficoTipo[id].destroy();
        graficoTipo[id] = new Chart(canvas.getContext('2d'), {
            type: tipo,
            data: {
                labels,
                datasets: [{
                    label: rotulo,
                    data: valores,
                    backgroundColor: cor
                }]
            },
            options: {
                responsive: true,
                plugins: { legend: { position: 'bottom' } }
            }
        });
    }

    document.addEventListener('DOMContentLoaded', () => {
        carregar().catch(erro => mostrarAlerta(erro.message));
    });
    window.carregar = carregar;
})();