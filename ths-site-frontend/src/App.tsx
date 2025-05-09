import "./App.css";
import { Router } from "./routes/Router";
import { useToken } from "./stores/TokenStore";

export const App = () => {
  const { jwtToken, tokenObj, setTokenObj } = useToken();
  if (!tokenObj && jwtToken) {
    setTokenObj(jwtToken);
  }
  return <Router />;
};
