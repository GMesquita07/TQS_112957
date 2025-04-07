import { Link } from "react-router-dom";

function Navbar() {
  return (
    <nav style={{ padding: "1rem", backgroundColor: "#eee" }}>
      <Link to="/" style={{ marginRight: "1rem" }}>Início</Link>
      <Link to="/reservations" style={{ marginRight: "1rem" }}>As Minhas Reservas</Link>
      <Link to="/staff" style={{ marginRight: "1rem" }}>Área Staff</Link>
      <Link to="/cache" style={{ marginRight: "1rem" }}>Estatísticas</Link> {/* Adicionado aqui */}
    </nav>
  );
}

export default Navbar;
