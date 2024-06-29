import React from 'react';
import '../../App.css';

const Login: React.FC = () => {
    return (
        <section className="container forms">
            <div className="form login">
                <div className="form-content">
                    <header>Login</header>
                    <form action="#">
                        <div className="field input-field">
                            <input type="email" placeholder="Email" className="input" />
                        </div>

                        <div className="field input-field">
                            <input type="password" placeholder="Password" className="password" />
                            <i className='bx bx-hide eye-icon'></i>
                        </div>

                        <div className="form-link">
                            <a href="#" className="forgot-pass">Forgot password?</a>
                        </div>

                        <div className="field button-field">
                            <button>Login</button>
                        </div>
                    </form>

                    <div className="form-link">
                        <span>Don't have an account? <a href="/register" className="link signup-link">Register</a></span>
                    </div>
                </div>

                <div className="line"></div>

                <div className="media-options">
                    <a href="#" className="field facebook">
                        <img src="https://www.logo.wine/a/logo/Facebook/Facebook-f_Logo-White-Dark-Background-Logo.wine.svg" alt="Facebook Icon" className="facebook-icon" />
                        <span>Login with Facebook</span>
                    </a>
                </div>


                <div className="media-options">
                    <a href="#" className="field google">
                        <img src="https://upload.wikimedia.org/wikipedia/commons/thumb/c/c1/Google_%22G%22_logo.svg/1024px-Google_%22G%22_logo.svg.png" alt="" className="google-img" />
                        <span>Login with Google</span>
                    </a>
                </div>
            </div>
        </section>
    );
}

export default Login;
