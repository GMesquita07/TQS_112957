// src/pages/WeatherCachePage.jsx
import { useEffect, useState } from "react";

function WeatherCachePage() {
  const [stats, setStats] = useState(null);

  useEffect(() => {
    fetch("http://localhost:8080/api/weather/cache-stats")
      .then((res) => res.json())
      .then((data) => setStats(data))
      .catch((err) => console.error("Erro ao buscar estatísticas da cache:", err));
  }, []);

  return (
    <div>
      <h2>Estatísticas da Cache Meteorológica</h2>
      {stats ? (
        <ul>
          <li><strong>Pedidos totais:</strong> {stats.totalRequests}</li>
          <li><strong>Cache hits:</strong> {stats.hits}</li>
          <li><strong>Cache misses:</strong> {stats.misses}</li>
        </ul>
      ) : (
        <p>A carregar...</p>
      )}
    </div>
  );
}

export default WeatherCachePage;
