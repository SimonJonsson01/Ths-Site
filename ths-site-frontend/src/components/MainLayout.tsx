import { Link, Outlet } from "react-router-dom";
import { Navbar } from "./Navbar";
import { Footer } from "./Footer";

export const MainLayout = () => {
  return (
    <div className="min-h-screen flex flex-col">
      <header className="min-w-screen bg-blue-50">
        <div className="page-wrapper my-8 size-fit">
          <Link className="flex items-center" to="/">
            <img
              src="../company_logo.svg"
              alt="company_logo"
              className="size-32"
            />
            <span className="text-4xl italic font-semibold tracking-wide w-min h-fit">
              Thonys Hushållsservice AB
            </span>
          </Link>
        </div>
      </header>
      <Navbar />
      <main className="flex size-full justify-center bg-center bg-[url(../company_logo.svg)] bg-repeat-space">
        <div className="size-full bg-white/70">
          <div className="page-wrapper bg-gray-100 size-full min-h-fit h-screen border-x-2 opacity-100">
            <Outlet />
          </div>
        </div>
      </main>
      <Footer />
    </div>
  );
};
