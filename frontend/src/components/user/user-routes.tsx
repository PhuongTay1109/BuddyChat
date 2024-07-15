import { Route, Routes } from "react-router-dom"
import UserLayout from "./user-layout"
import UserHomepage from "./user-homepage"

const UserRoutes = () => {
    return (
        <Routes>
            <Route path="/" element={<UserLayout/>}>
                <Route index element={<UserHomepage/>}/>
            </Route>

        </Routes>
    )
}

export default UserRoutes