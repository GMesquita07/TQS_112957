import { useEffect, useState } from 'react';

export default function ReservationsPage() {
  const [reservations, setReservations] = useState([]);
  const [error, setError] = useState('');

  useEffect(() => {
    const tokens = JSON.parse(localStorage.getItem('reservationTokens')) || [];

    if (tokens.length === 0) return;

    Promise.all(tokens.map(token =>
      fetch(`http://localhost:8080/api/reservations/${token}`)
        .then(res => {
          if (!res.ok) throw new Error('Erro ao carregar reserva');
          return res.json();
        })
        .then(data => ({ token, data }))
        .catch(err => {
          console.error('Erro ao carregar token', token, err);
          // Remover token inválido
          const stored = JSON.parse(localStorage.getItem('reservationTokens')) || [];
          const updated = stored.filter(t => t !== token);
          localStorage.setItem('reservationTokens', JSON.stringify(updated));

          return null;
        })
    ))
      .then(results => {
        const validReservations = results.filter(r => r !== null).map(r => r.data);
        setReservations(validReservations);
      });
  }, []);

  const cancelarReserva = (token) => {
    fetch(`http://localhost:8080/api/reservations/${token}/cancel`, {
      method: 'POST'
    })
      .then(res => {
        if (!res.ok) {
          throw new Error("Erro ao cancelar reserva");
        }
        return res.text();
      })
      .then(() => {
        setReservations(prev =>
          prev.map(r => r.token === token ? { ...r, cancelled: true } : r)
        );

        const tokens = JSON.parse(localStorage.getItem('reservationTokens')) || [];
        const updated = tokens.filter(t => t !== token);
        localStorage.setItem('reservationTokens', JSON.stringify(updated));
      })
      .catch(err => {
        setError(err.message);
      });
  };

  if (reservations.length === 0) {
    return <p>Sem reservas ativas.</p>;
  }

  return (
    <div>
      <h2>As Minhas Reservas</h2>
      {error && <p style={{ color: 'red' }}>{error}</p>}

      {reservations.map(reservation => (
        <div key={reservation.token} style={{ border: '1px solid #ccc', marginBottom: '10px', padding: '10px' }}>
          <p><strong>Restaurante:</strong> {reservation.restaurantName}</p>
          <p><strong>Data:</strong> {reservation.date}</p>
          <p><strong>Tipo:</strong> {reservation.type}</p>
          <p><strong>Cancelada:</strong> {reservation.cancelled ? 'Sim' : 'Não'}</p>

          {!reservation.cancelled && (
            <button onClick={() => cancelarReserva(reservation.token)}>Cancelar Reserva</button>
          )}
        </div>
      ))}
    </div>
  );
}
