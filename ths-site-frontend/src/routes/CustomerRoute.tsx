import { Navigate, Outlet } from "react-router-dom";
import { useToken } from "../stores/TokenStore";

export const CustomerRoute = () => {
  const { tokenObj } = useToken();

  const isCustomer: any = tokenObj?.authorities.length === 1;
  return isCustomer ? <Outlet /> : <Navigate to={"/"} />;
};
