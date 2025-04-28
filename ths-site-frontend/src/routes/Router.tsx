import { BrowserRouter, Route, Routes } from "react-router-dom";
import { MainLayout } from "../components/MainLayout";
import { Homepage } from "../pages/Homepage";
import { Informationpage } from "../pages/Informationpage";
import { Formpage } from "../pages/Formpage";
import { Reviewpage } from "../pages/Reviewpage";
import { Adminpage } from "../pages/Adminpage";

export const Router = () => {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<MainLayout />}>
          <Route path="/" element={<Homepage />} />
          <Route path="/form" element={<Formpage />} />
          <Route path="/review" element={<Reviewpage />} />
          <Route path="/info" element={<Informationpage />} />
          <Route path="/admin" element={<Adminpage />} />
        </Route>
      </Routes>
    </BrowserRouter>
  );
};
