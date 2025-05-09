import { Link, useNavigate } from "react-router-dom";
import { useToken } from "../stores/TokenStore";

export const Navbar = () => {
  const navigate = useNavigate();
  const { jwtToken, tokenObj, deleteJwtToken } = useToken();

  const logOut = () => {
    deleteJwtToken();
    navigate("/");
  };

  return (
    <nav className="sticky top-0 w-screen h-fit bg-blue-300 flex justify-center py-2 mx-auto *:bg-amber-300 *:text-center *:px-3 gap-2 *:cursor-pointer *:hover:bg-amber-500">
      <Link to="/">Start</Link>
      <Link to="/info">Information</Link>
      <Link to="/review">Recensioner</Link>
      {jwtToken ? (
        tokenObj?.userType == "customer" ? (
          /* Inloggad som Customer */
          <>
            <Link to="/form">Ansök service</Link>
            <Link to="/writereview">Skriv recension</Link>
            <Link to="/customer">Mina sidor</Link>
            <button onClick={logOut}>Logga ut</button>
          </>
        ) : (
          /* Inloggad som admin */
          <>
            <Link to="/job">Alla jobb</Link>
            <Link to="/admin">Mina sidor</Link>
            <button onClick={logOut}>Logga ut</button>
          </>
        )
      ) : (
        /* Inte inloggad */
        <>
          <Link to="/signup">Skapa konto</Link>
          <Link to="/login">Logga in</Link>
        </>
      )}
    </nav>
  );
};
