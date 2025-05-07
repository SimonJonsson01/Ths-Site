import { useEffect, useState } from "react";
import { Link, useNavigate } from "react-router-dom";

export const Navbar = () => {
  const navigate = useNavigate();
  const [loggedIn, setLoggedIn] = useState(
    sessionStorage.getItem("token") != null
  );

  useEffect(() => {

  }, []);

  const logOut = () => {
    sessionStorage.removeItem("token");
    setLoggedIn(false);
    navigate("/");
  };

  return (
    <nav className="sticky top-0 w-screen h-fit bg-blue-300 flex justify-center py-2 mx-auto *:bg-amber-300 *:text-center *:px-3 gap-2 *:cursor-pointer *:hover:bg-amber-500">
      <Link to="/">Start</Link>
      <Link to="/info">Information</Link>
      <Link to="/review">Recensioner</Link>
      {loggedIn ? (
        <>
          <Link to="/form">Ansök service</Link>
          <button onClick={logOut}>Logga ut</button>
        </>
      ) : (
        <>
          <Link to="/signup">Skapa konto</Link>
          <Link to="/login">Logga in</Link>
        </>
      )}
    </nav>
  );
};
