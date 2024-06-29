import { Navigate, Outlet } from "react-router-dom";
import { useAuth } from "../hooks/auth-provider";

const ProtectedRoute = () => {
    const auth = useAuth();
    // console.log("auth ", auth)
    // Add <> </> to return JSX.Element
    return <>{ auth.isAuthenticated ?
        <Outlet/> : <Navigate to="/login" />}
        </>;
}

export default ProtectedRoute;