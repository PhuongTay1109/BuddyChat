import { Outlet } from "react-router-dom";
import Header from "../header";
import Footer from "../footer";
const UserLayout = () => {
    return (
        <div className="d-flex flex-column min-vh-100">
            <Header/>
            <main className="container flex-grow-1 py-4">
                <Outlet /> {/* This is where nested routes will render */}
            </main>
            <Footer/>
        </div>
    );
}
export default UserLayout;