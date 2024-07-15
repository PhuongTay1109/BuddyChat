import { useAuth } from "../hooks/auth-provider";
import UserRoutes from "./user/user-routes";

const Homepage = () => {
    const auth = useAuth();
    const roles: string[] = auth.roles.map((role: any) => role.authority);
    console.log(roles)
    if (roles.includes("ROLE_USER")){
        return <UserRoutes/>
    }
    return (
        <>Not authorized</>
    )
}
export default Homepage;