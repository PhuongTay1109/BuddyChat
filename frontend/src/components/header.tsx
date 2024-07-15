// Header.jsx
import React from 'react';
import { Link } from 'react-router-dom';

const Header = () => {
    return (
        <header className="header">
            <div className="header-container">
                <div className="header-flex">
                    <div className="header-title">
                        <h1 className="header-title">My Application</h1>
                    </div>
                    <div className="header-nav">
                        <nav>
                            <ul className="header-nav">
                                <li>
                                    <Link to="/" className="header-nav-link">Home</Link>
                                </li>
                                <li>
                                    <Link to="/profile" className="header-nav-link">Profile</Link>
                                </li>
                                <li>
                                    <Link to="/settings" className="header-nav-link">Settings</Link>
                                </li>
                            </ul>
                        </nav>
                    </div>
                </div>
            </div>
        </header>
    );
}

export default Header;
