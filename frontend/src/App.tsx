import React from 'react';
import logo from './logo.svg';
import './App.css';
import AuthProvider from './hooks/auth-provider';
import { Route, Routes } from 'react-router-dom';
import Login from './components/security/login';
import Register from './components/security/register';
import ProtectedRoute from './routes/protected-route';
import Homepage from './components/homepage';
import ConfirmAccount from './components/security/confirm-account';

import { GoogleOAuthProvider } from '@react-oauth/google';
const googleClientId = process.env.REACT_APP_GOOGLE_CLIENT_ID ||''
console.log(googleClientId);



function App() {
  return (
    <GoogleOAuthProvider clientId={googleClientId}>
      <AuthProvider>

        <div className="App">
          <Routes>
            <Route path='/login' element={<Login />} />
            <Route path='/register' element={<Register />} />
            <Route path='/confirm-account' element={<ConfirmAccount />} />
            <Route element={<ProtectedRoute />}>
              <Route path='*' element={<Homepage />} />
            </Route>
          </Routes>
        </div>

      </AuthProvider>
    </GoogleOAuthProvider>

  );
}

export default App;
