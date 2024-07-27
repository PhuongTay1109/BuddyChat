import { Route, Routes } from "react-router-dom"
import UserLayout from "./user-layout"
import UserHomepage from "./user-homepage"
import UserProfile from "./user-profile"
import UserMessage from "./user-message"

const UserRoutes = () => {
    return (
        <Routes>
            <Route path="/*" element={<UserLayout/>}>
                <Route index element={<UserHomepage/>}/>
                <Route path="profile" element={<UserProfile/>}/>
                <Route path="message" element={<UserMessage/>}/>
            </Route>

        </Routes>
    )
}

export default UserRoutes