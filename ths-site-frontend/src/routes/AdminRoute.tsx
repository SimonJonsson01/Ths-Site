import { Navigate, Outlet } from "react-router-dom";
import { useToken } from "../stores/TokenStore";

export const AdminRoute = () => {
  const { tokenObj } = useToken();

  const isAdmin: any = tokenObj?.userType == "admin";
  return isAdmin ? <Outlet /> : <Navigate to={"/"} />;
};
