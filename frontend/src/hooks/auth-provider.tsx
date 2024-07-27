import { createContext, useState, useEffect, ReactNode, useContext } from 'react';
import { useNavigate } from 'react-router-dom';
import Cookies from 'js-cookie';
import axios from 'axios';
import {SERVER_BASE_URL } from '../constants/backend-server';

// Initial state with authentication check
const getAccessToken = () => {
    const accessToken = Cookies.get('accessToken') || null;
    return accessToken;
};

const getRefreshToken = () => {
    const refreshToken = Cookies.get('refreshToken') || null;
    return refreshToken;
};


const authStateInit = {
    isAuthenticated: !!getAccessToken(),
    accessToken: getAccessToken(),
    refreshToken: getRefreshToken(),
    login: async (input: object) => Promise<any>,
    logout: () => { },
    setAuthState: (state: any) => { }
};

const AuthContext = createContext(authStateInit);

interface AuthProviderProps {
    children: ReactNode;
}

export const AuthProvider = ({ children }: AuthProviderProps) => {
    const [authState, setAuthState] = useState(authStateInit);
    const navigate = useNavigate();

    // Define login function
    const login = async (input: object): Promise<any> => {
        try {
            const response = await axios(`${SERVER_BASE_URL}/api/v1/auth/login`, {
                method: 'POST',
                withCredentials: true, // This is critical for cookies to be sent and received
                headers: {
                    'Content-Type': 'application/json'
                },
                data: input
            });

            if (response.status === 200) { // OK
                console.log(response)
                const accessToken = response.data.data.accessToken;
                const refreshToken = response.data.data.refreshToken;
                const roles = response.data.data.roles;
                Cookies.set('accessToken', accessToken, { path: '/', secure: true});
                Cookies.set('refreshToken', refreshToken, { path: '/', secure: true});
                localStorage.setItem("roles", JSON.stringify(roles));

                setAuthState({
                    isAuthenticated: true,
                    accessToken,
                    refreshToken,
                    login,
                    logout,
                    setAuthState
                });
                navigate("/");
            } else {
                return response.data.message;
            }
        } catch (err: any) {
            console.error("Login Error: ", err);
            return err.response.data.message;
        }
    };

    // Define logout function
    const logout = async () => {
        console.log("logout")
        const { refreshToken } = authState
        try {
            const response = await axios.delete(`${SERVER_BASE_URL}/api/v1/auth/logout`, {
                withCredentials: true, // This is critical for cookies to be sent and received
                headers: {
                    'Content-Type': 'application/json'
                },
                data: { refreshToken}
            });
            if (response.status == 204) {// NO CONTENT 
                Cookies.remove("accessToken");
                Cookies.remove("refreshToken");
                localStorage.removeItem("roles");
                setAuthState({
                    isAuthenticated: false,
                    accessToken: null,
                    refreshToken: null,
                    login,
                    logout,
                    setAuthState
                });
                navigate("/login");
            }

        } catch (err: any) {
            console.error("Login Error: ", err);
            return err.response.data.message;
        }

    };

    useEffect(() => {
        setAuthState(prev => ({
            ...prev,
            login,
            logout,
            setAuthState
        }));
    }, []);

    return (
        <AuthContext.Provider value={authState}>
            {children}
        </AuthContext.Provider>
    );
};

export const useAuth = () => {
    return useContext(AuthContext);
};

export default AuthProvider;
