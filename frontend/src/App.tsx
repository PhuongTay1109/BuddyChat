import React from 'react';
import logo from './logo.svg';
import './App.css';
import AuthProvider from './hooks/auth-provider';
import { Route, Routes } from 'react-router-dom';
import Login from './components/security/login';
import Register from './components/security/register';
import ProtectedRoute from './routes/protected-route';
import Homepage from './components/homepage';

function App() {
  return (
    <div className="App">
      <AuthProvider>
        <Routes>
          <Route path='/login' element={<Login />} />
          <Route path='/register' element={<Register />} />
          <Route element={<ProtectedRoute/>}>
            <Route path='*' element={<Homepage/>} />
          </Route>
        </Routes>
      </AuthProvider>
    </div>
  );
}

export default App;
