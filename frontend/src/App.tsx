import AuthProvider from './hooks/auth-provider';
import { Route, Routes } from 'react-router-dom';
import Login from './components/security/login';
import Register from './components/security/register';
import ProtectedRoute from './routes/protected-route';
import ConfirmAccount from './components/security/confirm-account';
import 'bootstrap/dist/css/bootstrap.min.css';

import { GoogleOAuthProvider } from '@react-oauth/google';
import Homepage from './components/homepage';
const googleClientId = process.env.REACT_APP_GOOGLE_CLIENT_ID ||''



function App() {
  return (
    <GoogleOAuthProvider clientId={googleClientId}>
      <AuthProvider>

        <div className="App" style={{backgroundColor:'#91a2d2'}}>
          <Routes>
            <Route path='/login' element={<Login />} />
            <Route path='/register' element={<Register />} />
            <Route path='/confirm-account' element={<ConfirmAccount />} />
            <Route element={<ProtectedRoute />}>
              <Route path='/' element={<Homepage />} />
            </Route>
          </Routes>
        </div>

      </AuthProvider>
    </GoogleOAuthProvider>

  );
}

export default App;
