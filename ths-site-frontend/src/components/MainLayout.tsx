import { Link, Outlet } from "react-router-dom";
import { Navbar } from "./Navbar";
import { Footer } from "./Footer";
import { useToken } from "../stores/TokenStore";

export const MainLayout = () => {
  const { jwtToken, tokenObj } = useToken();

  return (
    <div className="min-h-screen bg-pink-100 flex flex-col">
      <header className="text-center text-4xl py-8 min-w-screen bg-blue-100 tracking-wide">
        <Link to="/">Thonys Hushållsservice</Link>
      </header>
        <span className="text-sm break-all truncate">{jwtToken}</span>
        <span className="text-sm break-all">{tokenObj?.email}</span>
        <span className="text-sm break-all">{tokenObj?.name}</span>
      <Navbar />
      <main className="bg-purple-600 flex grow size-full justify-center">
        <div className="page-wrapper bg-amber-500 w-full">
          <Outlet />
        </div>
      </main>
      <Footer />
    </div>
  );
};
