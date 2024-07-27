import { jwtDecode } from "jwt-decode";
import { useAuth } from "../hooks/auth-provider";
import UserRoutes from "./user/user-routes";

const Homepage = () => {
    const auth = useAuth();
    const dedcoded: any = jwtDecode(auth.accessToken);
    const roles: any[] = [];
    roles.push(dedcoded?.roles);
    if (roles.includes("ROLE_USER")){
        return <UserRoutes/>
    }
    return (
        <>Not authorized</>
    )
}
export default Homepage;