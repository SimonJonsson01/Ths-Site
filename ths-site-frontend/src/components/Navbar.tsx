import { Link } from "react-router-dom";

export const Navbar = () => {
  return (
    <nav className="sticky top-0 w-screen h-fit bg-blue-300 flex justify-center py-2 mx-auto *:bg-amber-300 *:text-center *:px-3 gap-2">
      <Link to="/">Start</Link>
      <Link to="/info">Information</Link>
      <Link to="/form">Ansök service</Link>
      <Link to="/review">Recensioner</Link>
      <Link to="/login">Logga in</Link>
    </nav>
  );
};
