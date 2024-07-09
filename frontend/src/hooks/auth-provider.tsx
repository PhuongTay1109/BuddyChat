import { createContext, useState, useEffect, ReactNode, useContext } from 'react';
import { useNavigate } from 'react-router-dom';
import Cookies from 'js-cookie';
import axios from 'axios';
import { LOGIN_POST_ENDPOINT } from '../constants/api';

// Initial state with authentication check
const getAccessToken = () => {
    const accessToken = Cookies.get('accessToken') || null;
    return accessToken;
};

const getRefreshToken = () => {
    const refreshToken = Cookies.get('refreshToken') || null;
    return refreshToken;
};

const getUser = () => {
    const userJson = Cookies.get('user') || null;
    try {
        return userJson ? JSON.parse(userJson) : null;
    } catch (e) {
        console.error('Failed to parse user data:', e);
        return null;
    }
};

const authStateInit = {
    isAuthenticated: !!getUser(),
    user: getUser(),
    accessToken: getAccessToken(),
    refreshToken: getRefreshToken(),
    login: async (input: object) => Promise<any>,
    logout: () => {},
    setAuthState: (state: any) => {}
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
            const response = await axios(LOGIN_POST_ENDPOINT, {
                method: 'POST',
                withCredentials: true, // This is critical for cookies to be sent and received
                headers: {
                    'Content-Type': 'application/json'
                },
                data: input
            });

            if (response.status === 200) { // OK
                console.log(response)
                const user = response.data.data.user;
                const accessToken = response.data.data.accessToken;
                const refreshToken = response.data.data.refreshToken;
                Cookies.set('user', JSON.stringify(user), { path: '/' });
                Cookies.set('accessToken', accessToken, { path: '/' });
                Cookies.set('refreshToken', refreshToken, { path: '/' });

                setAuthState({
                    isAuthenticated: true,
                    user,
                    accessToken,
                    refreshToken,
                    login,
                    logout,
                    setAuthState
                });
                navigate("/user-list");
            } else {
                return response.data.message;
            }
        } catch (err: any) {
            console.error("Login Error: ", err);
            return err.response.data.message;
        }
    };

    // Define logout function
    const logout = () => {
        Cookies.remove("accessToken", { path: '/' });
        Cookies.remove("user", { path: '/' });
        Cookies.remove("refreshToken", { path: '/' });
        setAuthState({
            isAuthenticated: false,
            user: null,
            accessToken: null,
            refreshToken: null,
            login,
            logout,
            setAuthState
        });
        navigate("/login");
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
