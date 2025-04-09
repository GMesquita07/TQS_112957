import { useState } from "react";

function HomePage() {
  const [selectedRestaurant, setSelectedRestaurant] = useState("1");
  const [selectedDate, setSelectedDate] = useState(() => {
    const today = new Date().toISOString().split("T")[0];
    return today;
  });
  const [meals, setMeals] = useState([]);
  const [reservationMessage, setReservationMessage] = useState("");

  const fetchMealsWithWeather = () => {
    fetch(`http://localhost:8080/api/meals/weather?restaurantId=${selectedRestaurant}&date=${selectedDate}`)
      .then((response) => {
        if (!response.ok) {
          return response.text().then((text) => {
            throw new Error(text);
          });
        }
        return response.json();
      })
      .then((data) => {
        setMeals(data);
      })
      .catch((error) => {
        setReservationMessage(`Erro ao carregar refeições: ${error.message}`);
      });
  };

  const handleReservation = (mealId, type) => {
    fetch("http://localhost:8080/api/reservations", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({
        restaurantId: selectedRestaurant,
        mealId,
        date: selectedDate,
        type,
      }),
    })
      .then((response) => {
        if (!response.ok) {
          return response.text().then((text) => {
            throw new Error(text);
          });
        }
        return response.json();
      })
      .then((data) => {
        const existingTokens = JSON.parse(localStorage.getItem("reservationTokens")) || [];
        localStorage.setItem("reservationTokens", JSON.stringify([...existingTokens, data.token]));
        alert("Token: " + data.token); // ⚠️ Necessário para o teste funcional!
      })
      .catch((error) => {
        setReservationMessage(`Erro ao fazer a reserva: ${error.message}`);
      });
  };

  return (
    <div>
      <h2>Refeições Disponíveis</h2>

      <label htmlFor="restaurant-select">Escolha a cantina:</label>
      <select
        id="restaurant-select"
        onChange={(e) => setSelectedRestaurant(e.target.value)}
        value={selectedRestaurant}
      >
        <option value="1">Cantina Central</option>
        <option value="2">Cantina de Santiago</option>
      </select>

      <label style={{ marginLeft: "20px" }}>Data:</label>
      <input
        type="date"
        value={selectedDate}
        onChange={(e) => setSelectedDate(e.target.value)}
      />

      <button onClick={fetchMealsWithWeather} style={{ marginLeft: "10px" }}>
        Procurar
      </button>

      <table border="1" cellPadding="8" style={{ marginTop: "20px" }}>
        <thead>
          <tr>
            <th>Tipo</th>
            <th>Descrição</th>
            <th>Data</th>
            <th>Previsão</th>
            <th>Ação</th>
          </tr>
        </thead>
        <tbody>
          {meals.length > 0 ? (
            meals.map((meal) => (
              <tr key={meal.id} className="meal-row">
                <td>{meal.type}</td>
                <td>{meal.description}</td>
                <td>{meal.date}</td>
                <td>
                  {meal.weatherForecast
                    ? `${meal.weatherForecast.summary}, ${meal.weatherForecast.temperature}ºC`
                    : "Sem dados"}
                </td>
                <td>
                  <button
                    className={
                      meal.type === "ALMOCO"
                        ? "reservation-btn-almoco"
                        : "reservation-btn-jantar"
                    }
                    onClick={() => handleReservation(meal.id, meal.type)}
                  >
                    Reservar
                  </button>
                </td>
              </tr>
            ))
          ) : (
            <tr>
              <td colSpan="5">Sem refeições disponíveis.</td>
            </tr>
          )}
        </tbody>
      </table>

      {reservationMessage && <p>{reservationMessage}</p>}
    </div>
  );
}

export default HomePage;
