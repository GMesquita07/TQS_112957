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
        .then(data => ({ ...data, token }))
        .catch(() => {
          const stored = JSON.parse(localStorage.getItem('reservationTokens')) || [];
          const updated = stored.filter(t => t !== token);
          localStorage.setItem('reservationTokens', JSON.stringify(updated));
          return null;
        })
    )).then(results => {
      const validReservations = results.filter(r => r !== null);
      setReservations(validReservations);
    });
  }, []);

  const cancelarReserva = (token) => {
    fetch(`http://localhost:8080/api/reservations/${token}/cancel`, {
      method: 'POST'
    })
      .then(res => {
        if (!res.ok) throw new Error("Erro ao cancelar reserva");
        return res.text();
      })
      .then(() => {
        setReservations(prev =>
          prev.map(r => r.token === token ? { ...r, cancelled: true } : r)
        );
        const tokens = JSON.parse(localStorage.getItem('reservationTokens')) || [];
        localStorage.setItem('reservationTokens', JSON.stringify(tokens.filter(t => t !== token)));
      })
      .catch(err => setError(err.message));
  };

  return (
    <div>
      <h2>As Minhas Reservas</h2>
      {error && <p style={{ color: 'red' }} id="error-message">{error}</p>}

      {reservations.length === 0 ? (
        <p id="no-reservations">Sem reservas ativas.</p>
      ) : (
        reservations.map((reservation, index) => (
          <div
            key={reservation.token}
            className="reservation-row"
            id={`reserva-${index}`}
            style={{ border: '1px solid #ccc', marginBottom: '10px', padding: '10px' }}
          >
            <p><strong>Restaurante:</strong> {reservation.restaurantName}</p>
            <p><strong>Data:</strong> {reservation.date}</p>
            <p><strong>Tipo:</strong> {reservation.type}</p>
            <p><strong>Cancelada:</strong> {reservation.cancelled ? 'Sim' : 'Não'}</p>
            <p className="reservation-token"><strong>Token:</strong> {reservation.token}</p>

            {!reservation.cancelled && (
              <button
                id={`cancel-btn-${index}`}
                className="cancel-btn"
                onClick={() => cancelarReserva(reservation.token)}
              >
                Cancelar Reserva
              </button>
            )}
          </div>
        ))
      )}
    </div>
  );
}
