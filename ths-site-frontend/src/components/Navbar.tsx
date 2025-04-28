import { Link } from "react-router-dom"

export const Navbar = () => {
    return (
        <nav className="w-screen h-fit bg-blue-300 flex justify-around py-2">
            <Link to="/">Home</Link>
            <Link to="/form">Forms</Link>
            <Link to="/info">Info</Link>
            <Link to="/review">Reviews</Link>
        </nav>
    )
}