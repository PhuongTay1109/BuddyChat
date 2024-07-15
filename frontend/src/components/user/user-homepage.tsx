import { useNavigate } from "react-router-dom";
import { useAuth } from "../../hooks/auth-provider";
import useApi from "../../hooks/api";
import { useEffect, useState } from "react";

const UserHomepage = () => {
    const api = useApi();
    const auth = useAuth();
    const navigate = useNavigate();
    const [username, setUsername] = useState<string>("");

    useEffect(() => {
        const fetchData = async () => {
            try {
                const response = await api.get(`/api/v1/users/truonganhtuan3092@gmail.com`)
                if (response.status == 200) {
                    const data = response.data.data;
                    setUsername(data.username)
                }
            } catch (err) {
                console.log(err)
            }

        }
        fetchData();
    }, [])

    return (
        <>
        Welcome to homepage {username}
        <button onClick={() => auth.logout()}>Log out</button>
        </>

    )
}
export default UserHomepage;