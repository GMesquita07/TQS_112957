import { useState, useEffect } from "react";

function HomePage() {
  const [selectedRestaurant, setSelectedRestaurant] = useState("");
  const [selectedDate, setSelectedDate] = useState("");
  const [meals, setMeals] = useState([]);
  const [reservationMessage, setReservationMessage] = useState("");

  useEffect(() => {
    if (selectedRestaurant && selectedDate) {
      fetchMealsWithWeather();
    }
  }, [selectedRestaurant, selectedDate]);

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
    const restaurantId = selectedRestaurant;
    const date = selectedDate;

    fetch("http://localhost:8080/api/reservations", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        restaurantId,
        mealId,
        date,
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
        setReservationMessage(`Reserva efetuada com sucesso! Token: ${data.token}`);
      })
      .catch((error) => {
        setReservationMessage(`Erro ao fazer a reserva: ${error.message}`);
      });
  };

  return (
    <div>
      <h2>Página de Cantinas</h2>

      <label>Escolha uma cantina:</label>
      <select onChange={(e) => setSelectedRestaurant(e.target.value)} value={selectedRestaurant}>
        <option value="">Selecione uma cantina</option>
        <option value="1">Cantina Central</option>
        <option value="2">Cantina Santiago</option>
      </select>

      <label>Data:</label>
      <input type="date" onChange={(e) => setSelectedDate(e.target.value)} value={selectedDate} />

      <h3>Refeições disponíveis</h3>
      <table>
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
              <tr key={meal.id}>
                <td>{meal.type}</td>
                <td>{meal.description}</td>
                <td>{meal.date}</td>
                <td>
                  {meal.weatherForecast
                    ? `${meal.weatherForecast.summary}, ${meal.weatherForecast.temperature}ºC`
                    : "Sem dados"}
                </td>
                <td>
                  <button onClick={() => handleReservation(meal.id, meal.type)}>Reservar</button>
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
