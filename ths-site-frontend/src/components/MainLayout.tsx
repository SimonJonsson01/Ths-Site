import { Outlet } from "react-router-dom"
import { Navbar } from "./Navbar"
import { Footer } from "./Footer"



export const MainLayout = () => {
    return (
        <>
            <header className="text-center text-4xl py-8 min-w-screen bg-blue-100 tracking-wide">Thonys Hushållsservice</header>
            <Navbar />
        <Outlet />
        <Footer />
        </>
    )
}