import { useEffect, useState } from 'react';

export default function WeatherCachePage() {
  const [stats, setStats] = useState(null);

  useEffect(() => {
    fetch('http://localhost:8080/api/weather/cache/stats')
      .then(res => res.json())
      .then(data => setStats(data))
      .catch(err => console.error("Erro ao buscar estatísticas da cache:", err));
  }, []);

  return (
    <div>
      <h2>Estatísticas da Cache Meteorológica</h2>
      {stats ? (
        <ul>
          <li><strong>Total de Pedidos:</strong> {stats.totalRequests}</li>
          <li><strong>Acertos (Hits):</strong> {stats.hits}</li>
          <li><strong>Falhas (Misses):</strong> {stats.misses}</li>
        </ul>
      ) : (
        <p>A carregar estatísticas...</p>
      )}
    </div>
  );
}
