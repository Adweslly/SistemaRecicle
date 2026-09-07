(function () {
    async function carregar() {
        try {
            const [residuos, setores, coletas, desvio, destinacao] = await Promise.all([
                API.get('/residuos'),
                API.get('/setores'),
                API.get('/coletas'),
                API.get('/relatorios/desvio-aterro'),
                API.get('/relatorios/destinacao')
            ]);

            const realizadas = coletas.filter(c => c.status === 'REALIZADA').length;
            const pendentes = coletas.filter(c => c.status === 'PENDENTE' || c.status === 'AGENDADA').length;

            document.getElementById('card-residuos').textContent = residuos.length;
            document.getElementById('card-setores').textContent = setores.length;
            document.getElementById('card-coletas-realizadas').textContent = realizadas;
            document.getElementById('card-coletas-pendentes').textContent = pendentes;
            document.getElementById('card-desvio').textContent =
                Number(desvio.desvioAterroPercentual).toLocaleString('pt-BR') + '%';

            // Geração por setor
            const porSetor = await API.get('/relatorios/geracao-por-setor');
            novoGrafico('grafico-setores', 'bar', {
                labels: porSetor.map(s => s.setorNome),
                valores: porSetor.map(s => s.totalKg)
            });

            // Destinação (reciclado x aterro)
            novoGrafico('grafico-destinacao', 'doughnut', {
                labels: ['Reciclado', 'Aterro'],
                valores: [destinacao.recicladoKg, destinacao.aterroKg],
                cores: ['#16a34a', '#9ca3af']
            });

            // Resíduos por tipo
            const tipos = {};
            residuos.forEach(r => { tipos[r.tipo] = (tipos[r.tipo] || 0) + Number(r.quantidadeKg); });
            novoGrafico('grafico-tipos', 'pie', {
                labels: Object.keys(tipos),
                valores: Object.values(tipos)
            });
        } catch (erro) {
            mostrarAlerta(erro.message);
        }
    }

    function novoGrafico(id, tipo, dados) {
        const canvas = document.getElementById(id);
        if (!canvas || typeof Chart === 'undefined') return;
        new Chart(canvas.getContext('2d'), {
            type: tipo,
            data: {
                labels: dados.labels,
                datasets: [{
                    label: 'kg',
                    data: dados.valores,
                    backgroundColor: dados.cores || ['#16a34a', '#065f46', '#86efac', '#eab308', '#93c5fd', '#f9a8d4']
                }]
            },
            options: {
                responsive: true,
                plugins: {
                    legend: { position: 'bottom' },
                    title: { display: false }
                }
            }
        });
    }

    carregar();
})();