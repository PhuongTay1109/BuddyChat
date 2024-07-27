
import React, { useState } from 'react';
import { Formik, Form, Field, ErrorMessage } from 'formik';
import { userLoginValidation } from '../../utils/vadliation-schema';
import { useAuth } from '../../hooks/auth-provider';
import { GoogleLogin, useGoogleLogin } from '@react-oauth/google';
import axios from 'axios';
import Cookies from 'js-cookie';
import { SERVER_BASE_URL } from '../../constants/backend-server';
import { Link, useNavigate } from 'react-router-dom';
import FacebookLogin from '@greatsumini/react-facebook-login';
import Header from '../header';
const facebookClientId = process.env.REACT_APP_FACEBOOK_CLIENT_ID || '';


const Login: React.FC = () => {
    const navigate = useNavigate();

    const [showPassword, setShowPassword] = useState(false);
    const [message, setMessage] = useState<string | null>(null);

    const togglePasswordVisibility = () => {
        setShowPassword(!showPassword);
    };

    const auth = useAuth();

    const initialValues = {
        username: '',
        password: ''
    };

    const localLogin = async (values: any) => {
        try {
            const msg: any = await auth.login(values);
            setMessage(msg);
        } catch (err) {
            throw err;
        }
    };

    const googleLogin = useGoogleLogin({
        onSuccess: async (response) => {
            // console.log(response)
            const { access_token } = response;
            // console.log(access_token)

            try {
                const response = await axios.post(`${SERVER_BASE_URL}/api/v1/auth/google-login`, { token: access_token })
                if (response.status === 200) {
                    // console.log(response)
                    const accessToken = response.data.data.accessToken;
                    const refreshToken = response.data.data.refreshToken;
                    Cookies.set('accessToken', accessToken, { path: '/', secure: true });
                    Cookies.set('refreshToken', refreshToken, { path: '/', secure: true });



                    auth.setAuthState((prevState: any) => ({
                        ...prevState,
                        isAuthenticated: true,
                        accessToken,
                        refreshToken,
                    }));
                    navigate("/");
                }

            } catch (error) {
                console.error('Error fetching user info:', error);
            }
        },


    })
    const facebookLogin = async (response: any) => {
        // console.log("response ", response)
        if (response.accessToken) {
            try {
                const res = await axios.post(`${SERVER_BASE_URL}/api/v1/auth/facebook-login`, { token: response.accessToken });
                if (res.status === 200) {
                    const accessToken = res.data.data.accessToken;
                    const refreshToken = res.data.data.refreshToken;
                    Cookies.set('accessToken', accessToken, { path: '/', secure: true });
                    Cookies.set('refreshToken', refreshToken, { path: '/', secure: true });


                    auth.setAuthState((prevState: any) => ({
                        ...prevState,
                        isAuthenticated: true,
                        accessToken,
                        refreshToken,
                    }));
                    navigate("/");
                }
            } catch (error) {
                console.error('Error logging in with Facebook:', error);
            }
        }
    }

    return (<>
        <section style={{height: '100vh', width: '150vh', padding: "100px"}} className="app-background container">
            <div className="row justify-content-center">
                <div className="col-md-6">
                    <div className="card m-auto" >
                        <div style={{padding: "40px"}} className="card-body ">
                        <h1 style={{ fontFamily: 'Billabong' }} className="card-title text-center mb-2">
                            <Link to="/login" className="text-decoration-none text-black">Simple Blog</Link>                
                        </h1>
                        <h5 style={{ fontFamily: 'Billabong' }} className="card-title text-center mb-4">Login</h5>
                            <Formik
                                initialValues={initialValues}
                                validationSchema={userLoginValidation}
                                onSubmit={localLogin}
                            >
                                {({ isSubmitting }) => (
                                    <Form>
                                        {message && (
                                            <div className="alert alert-danger">{message}</div>
                                        )}
                                        <div className="mb-3">
                                            <Field type="text" placeholder="Username" name="username" id="username" className="form-control" />
                                            <ErrorMessage name="username" component="div" className="text-danger" />
                                        </div>
                                        <div className="mb-3">
                                            <Field type="password" placeholder="Password" name="password" id="password" className="form-control" />
                                            <ErrorMessage name="password" component="div" className="text-danger" />
                                        </div>
                                        
                                        <div className="mb-3 text-center">
                                            <button type="submit" disabled={isSubmitting} className="w-100 btn btn-primary">
                                                {isSubmitting ? 'Signing...' : 'Login'}
                                            </button>
                                        </div>
                                        <div className="mb-1">
                                            <a href="/forgot-password" className="forgot-pass text-decoration-none">Forgot password?</a>
                                        </div>
                                    </Form>
                                )}
                            </Formik>
                            <div className="d-flex flex-column justify-content-between align-items-center mt-1">
                                <FacebookLogin
                                    appId={facebookClientId}
                                    scope='email,public_profile'
                                    onSuccess={facebookLogin}
                                    fields='email'
                                    onFail={(error) => console.error('Facebook login error:', error)}
                                    render={({ onClick }) => (
                                        <button onClick={onClick} style={{ cursor: 'pointer', marginTop: '10px'}} className="mb-1 w-100 btn btn-primary">
                                            <img src="https://www.logo.wine/a/logo/Facebook/Facebook-f_Logo-White-Dark-Background-Logo.wine.svg" style={{ width: '24px', height: '24px' }} alt="Facebook Icon" className="me-2" />
                                            Login with Facebook
                                        </button>
                                    )}
                                />
                                <button onClick={() => googleLogin()} style={{ cursor: 'pointer', marginTop: '5px' }} className="w-100 btn btn-danger">
                                    <img src="https://upload.wikimedia.org/wikipedia/commons/thumb/c/c1/Google_%22G%22_logo.svg/1024px-Google_%22G%22_logo.svg.png" style={{ width: '24px', height: '24px' }} alt="Google Icon" className="me-2" />
                                    Login with Google
                                </button>
                            </div>

                            <div className="mt-3 text-center">
                                <span>Don't have an account? <a href="/register" className='text-decoration-none'>Register</a></span>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </section>
    </>
    );
}

export default Login;
