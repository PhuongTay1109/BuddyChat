import React from 'react';
import '../../App.css';
import 'boxicons/css/boxicons.min.css'; 

const Register: React.FC = () => {
    return (
        <section className="container forms">
            <div className="form signup">
                <div className="form-content">
                    <header>Signup</header>
                    <form action="#">
                        <div className="field input-field">
                            <input type="email" placeholder="Email" className="input" />
                        </div>
                        <div className="field input-field">
                            <input type="text" placeholder="Username" className="input" />
                        </div>

                        <div className="field input-field">
                            <input type="password" placeholder="Create password" className="password" />
                            <i className='bx bx-hide eye-icon'></i>
                        </div>

                        <div className="field input-field">
                            <input type="password" placeholder="Confirm password" className="password" />
                            <i className='bx bx-hide eye-icon'></i>
                        </div>

                        <div className="field button-field">
                            <button>Signup</button>
                        </div>
                    </form>

                    <div className="form-link">
                        <span>Already have an account? <a href="#" className="link login-link">Login</a></span>
                    </div>
                </div>

                <div className="line"></div>

                <div className="media-options">
                    <a href="#" className="field facebook">
                        <img src="https://www.logo.wine/a/logo/Facebook/Facebook-f_Logo-White-Dark-Background-Logo.wine.svg" alt="Facebook Icon" className="facebook-icon" />
                        <span>Signup with Facebook</span>
                    </a>
                </div>

                <div className="media-options">
                    <a href="#" className="field google">
                        <img src="https://upload.wikimedia.org/wikipedia/commons/thumb/c/c1/Google_%22G%22_logo.svg/1024px-Google_%22G%22_logo.svg.png" alt="" className="google-img" />
                        <span>Signup with Google</span>
                    </a>
                </div>
            </div>
        </section>
    );
}

export default Register;
