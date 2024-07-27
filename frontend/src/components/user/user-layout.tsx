import { Outlet } from "react-router-dom";
import Header from "../header";
import Footer from "../footer";
const UserLayout = () => {
    return (
        <>
            <Header />
            <main id='main' className="container py-5 flex-grow-1 ">
                <Outlet /> {/* This is where nested routes will render */}
            </main>
            <Footer />
        </>



    );
}
export default UserLayout;