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
    <nav className="sticky -top-1 w-screen h-fit bg-blue-300 flex justify-center py-3 mx-auto border-y-1">
      <div className="flex gap-2 *:bg-gray-50 *:text-center *:py-1 *:px-4 *:cursor-pointer *:hover:bg-gray-200 *:rounded-md *:hover:font-semibold">
        <Link to="/">Start</Link>
        {/* <Link to="/info">Information</Link> */}
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
      </div>
    </nav>
  );
};
