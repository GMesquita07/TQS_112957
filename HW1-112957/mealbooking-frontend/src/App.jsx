import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Navbar from './components/Navbar';
import HomePage from './pages/HomePage';
import ReservationsPage from './pages/ReservationsPage';
import StaffPage from './pages/StaffPage';
import WeatherCachePage from './pages/WeatherCachePage';

function App() {
  return (
    <Router>
      <Navbar />
      <div className="p-4">
        <Routes>
          <Route path="/" element={<HomePage />} />
          <Route path="/reservations" element={<ReservationsPage />} />
          <Route path="/staff" element={<StaffPage />} />
          <Route path="/cache-stats" element={<WeatherCachePage />} />
        </Routes>
      </div>
    </Router>
  );
}

export default App;
