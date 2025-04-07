import { useEffect, useState } from 'react';

export default function StaffPage() {
  const [reservas, setReservas] = useState([]);
  const [mensagem, setMensagem] = useState('');
  const [erro, setErro] = useState('');

  useEffect(() => {
    fetch('http://localhost:8080/api/reservations/all')
      .then(res => {
        if (!res.ok) throw new Error("Erro ao buscar reservas");
        return res.json();
      })
      .then(data => setReservas(data))
      .catch(err => setErro(err.message));
  }, []);

  const handleCheckIn = (token) => {
    fetch(`http://localhost:8080/api/reservations/checkin?token=${token}`, {
      method: 'POST'
    })
      .then(res => {
        if (!res.ok) throw new Error("Erro ao fazer check-in");
        return res.text();
      })
      .then(() => {
        setMensagem("Check-in realizado com sucesso!");
        setErro('');
        setReservas(prev =>
          prev.map(r =>
            r.token === token ? { ...r, checkedIn: true } : r
          )
        );
      })
      .catch(err => {
        setMensagem('');
        setErro(err.message);
      });
  };

  return (
    <div>
      <h2>Área de Staff - Lista de Reservas</h2>
      {mensagem && <p style={{ color: 'green' }}>{mensagem}</p>}
      {erro && <p style={{ color: 'red' }}>{erro}</p>}

      {reservas.length === 0 ? (
        <p>Sem reservas registadas.</p>
      ) : (
        <table border="1" cellPadding="8">
          <thead>
            <tr>
              <th>Restaurante</th>
              <th>Data</th>
              <th>Tipo</th>
              <th>Token</th>
              <th>Check-in</th>
              <th>Ação</th>
            </tr>
          </thead>
          <tbody>
            {reservas.map(reserva => (
              <tr key={reserva.token}>
                <td>{reserva.restaurantName}</td>
                <td>{reserva.date}</td>
                <td>{reserva.type}</td>
                <td>{reserva.token}</td>
                <td>{reserva.checkedIn ? '✔️ Sim' : '❌ Não'}</td>
                <td>
                  {!reserva.checkedIn && !reserva.cancelled ? (
                    <button onClick={() => handleCheckIn(reserva.token)}>Fazer Check-in</button>
                  ) : reserva.cancelled ? (
                    'Cancelada'
                  ) : (
                    'Check-in feito'
                  )}
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      )}
    </div>
  );
}
