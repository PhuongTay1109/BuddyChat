import axios from 'axios';
import { useAuth } from './auth-provider';
import { SERVER_BASE_URL } from '../constants/backend-server';
import Cookies from 'js-cookie';

const useApi = () => {
  const { accessToken, refreshToken, setAuthState } = useAuth();
  
  const api = axios.create({
    baseURL: SERVER_BASE_URL,
    withCredentials: true
  });

  api.interceptors.request.use(
    config => {
      if (accessToken) {
        config.headers['Authorization'] = `Bearer ${accessToken}`;
      }
      return config;
    },
    error => {
      return Promise.reject(error);
    }
  );

  api.interceptors.response.use(
    response => response,
    async error => {
      const originalRequest = error.config;
      if (error.response.status === 401 && !originalRequest._retry) {
        originalRequest._retry = true;
        try {
          const response = await axios.post(`${SERVER_BASE_URL}/api/v1/auth/refresh-token`, { token: refreshToken }, { withCredentials: true });
          if (response.status === 200) {
            const newAccessToken = response.data.data.newAccessToken;
            Cookies.set('accessToken', newAccessToken, { path: '/' });
            setAuthState((prevState : any)=> ({
              ...prevState,
              accessToken: newAccessToken
            }));
            axios.defaults.headers.common['Authorization'] = `Bearer ${newAccessToken}`;
            return api(originalRequest);
          }
        } catch (refreshError) {
          console.error('Failed to refresh access token:', refreshError);
          setAuthState({
            isAuthenticated: false,
            user: null,
            accessToken: null,
            refreshToken: null
          });
          Cookies.remove('accessToken');
          Cookies.remove('refreshToken');
          window.location.href = '/login';
        }
      }
      return Promise.reject(error);
    }
  );

  return api;
};

export default useApi;
