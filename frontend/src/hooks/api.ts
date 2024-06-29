import axios from 'axios';

import { SERVER_BASE_URL } from '../constants/app';
import { useAuth } from './auth-provider';


const useApi = () => {
  const { accessToken } = useAuth();
  
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

  return api;
};

export default useApi;