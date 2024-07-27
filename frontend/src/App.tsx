import AuthProvider from './hooks/auth-provider';
import { Route, Routes } from 'react-router-dom';
import Login from './components/security/login';
import Register from './components/security/register';
import ProtectedRoute from './routes/protected-route';
import ConfirmAccount from './components/security/confirm-account';
import ForgotPassword from './components/security/forgot-password';
import 'bootstrap/dist/css/bootstrap.min.css';
import "./App.css"

import { GoogleOAuthProvider } from '@react-oauth/google';
import Homepage from './components/homepage';
import ResetPassword from './components/security/reset-password';
const googleClientId = process.env.REACT_APP_GOOGLE_CLIENT_ID ||''



function App() {
  return (
    <GoogleOAuthProvider clientId={googleClientId}>
      <AuthProvider>

        <div className="App" style={{backgroundColor:'#80808021'}}>
          <Routes>
            <Route path='/login' element={<Login />} />
            <Route path='/register' element={<Register />} />
            <Route path='/confirm-account' element={<ConfirmAccount />} />
            <Route path='/forgot-password' element={<ForgotPassword />} />
            <Route path="/reset-password" element={<ResetPassword/>} />
            <Route element={<ProtectedRoute />}>
              <Route path='/*' element={<Homepage />} />
            </Route>
          </Routes>
        </div>

      </AuthProvider>
    </GoogleOAuthProvider>

  );
}

export default App;
