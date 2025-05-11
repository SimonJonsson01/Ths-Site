import { BrowserRouter, Navigate, Route, Routes } from "react-router-dom";
import { MainLayout } from "../components/MainLayout";
import { Homepage } from "../pages/Homepage";
import { Formpage } from "../pages/Formpage";
import { Reviewpage } from "../pages/Reviewpage";
import { Adminpage } from "../pages/Adminpage";
import { Loginpage } from "../pages/Loginpage";
import { SignupPage } from "../pages/Signuppage";
import { Customerpage } from "../pages/Customerpage";
import { CustomerRoute } from "./CustomerRoute";
import { AdminRoute } from "./AdminRoute";
import { Jobpage } from "../pages/Jobpage";
import { ReviewWritepage } from "../pages/ReviewWritepage";

export const Router = () => {

  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<MainLayout />}>
          <Route path="/" element={<Homepage />} />
          <Route path="/review" element={<Reviewpage />} />
          {/* <Route path="/info" element={<Informationpage />} /> */}
          <Route element={<CustomerRoute />}>
            <Route path="/customer" element={<Customerpage />} />
            <Route path="/form" element={<Formpage />} />
            <Route path="/writereview" element={<ReviewWritepage />} />
          </Route>
          <Route element={<AdminRoute />}>
           <Route path="/job" element={<Jobpage />} />
            <Route path="/admin" element={<Adminpage />} />
          </Route>
          <Route path="/login" element={<Loginpage />} />
          <Route path="/signup" element={<SignupPage />} />
          <Route path="*" element={<Navigate to="/" />} />
        </Route>
      </Routes>
    </BrowserRouter>
  );
};
