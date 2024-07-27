import React, { useEffect, useState, useMemo } from 'react';
import { Navbar, Nav, NavDropdown, Container } from 'react-bootstrap';
import { Link } from 'react-router-dom';
import useApi from '../hooks/api';
import { SERVER_BASE_URL } from '../constants/backend-server';
import { link } from 'fs';
import { useAuth } from '../hooks/auth-provider';

const Header = () => {
  const api = useApi();
  const auth = useAuth();
  const [userData, setUserData] = useState({
    username: '',
    firstName: '',
    lastName: '',
    email: '',
    profilePicture: '',
  });

  useEffect(() => {
    async function fetchData() {
      try {
        const response = await api.get(`${SERVER_BASE_URL}/api/v1/users/me`);
        const data = response.data.data;
        setUserData({
          username: data.username,
          firstName: data.firstName,
          lastName: data.lastName,
          email: data.email,
          profilePicture: data.profilePicture,
        });
      } catch (err) {
        console.error(err);
      }
    }
    fetchData();
  }, [api]);

  // Memoize the profile image and display name to avoid unnecessary re-renders
  const profileImage = useMemo(() => (
    <img src={userData.profilePicture} style={{width: "30px", height: "30px"}} className="rounded-circle" alt="User Profile" />
  ), [userData.profilePicture]);

  const displayName = useMemo(() => (
    `${userData.firstName} ${userData.lastName}`
  ), [userData.firstName, userData.lastName]);

  return (
    <Navbar bg="light" expand="lg" fixed='top'>
      <Container>
        <Navbar.Brand as={Link} to="/" className='navbar-brand-name'>
          Simple Blog
        </Navbar.Brand>
        <Navbar.Toggle aria-controls="navbarSupportedContent" />
        <Navbar.Collapse id="navbarSupportedContent">

          <Nav className="me-auto mb-2 mb-lg-0">
            <Nav.Link  as={Link} to="/">
              <svg xmlns="http://www.w3.org/2000/svg" x="0px" y="0px" width="30" height="30" viewBox="0 0 24 24">
                <path d="M 12 2.0996094 L 1 12 L 4 12 L 4 21 L 11 21 L 11 15 L 13 15 L 13 21 L 20 21 L 20 12 L 23 12 L 12 2.0996094 z M 12 4.7910156 L 18 10.191406 L 18 11 L 18 19 L 15 19 L 15 13 L 9 13 L 9 19 L 6 19 L 6 10.191406 L 12 4.7910156 z"></path>
              </svg>
            </Nav.Link>
            <Nav.Link as={Link} to="/message">
              <svg xmlns="http://www.w3.org/2000/svg" x="0px" y="0px" width="30" height="30" viewBox="0 0 24 24">
                <path d="M 12 2 C 6.486 2 2 6.262 2 11.5 C 2 14.045 3.088 16.487484 5 18.271484 L 5 22.617188 L 9.0800781 20.578125 C 10.039078 20.857125 11.02 21 12 21 C 17.514 21 22 16.738 22 11.5 C 22 6.262 17.514 2 12 2 z M 12 4 C 16.411 4 20 7.365 20 11.5 C 20 15.635 16.411 19 12 19 C 11.211 19 10.415672 18.884203 9.6386719 18.658203 L 8.8867188 18.439453 L 8.1855469 18.789062 L 7 19.382812 L 7 18.271484 L 7 17.402344 L 6.3632812 16.810547 C 4.8612813 15.408547 4 13.472 4 11.5 C 4 7.365 7.589 4 12 4 z M 11 9 L 6 14 L 10.5 12 L 13 14 L 18 9 L 13.5 11 L 11 9 z"></path>
              </svg>
            </Nav.Link>
            {/* Navigation Links */}
          </Nav>

          <Nav.Link as={Link} to="/profile" className="mr-2">
            <Navbar.Brand >
              {displayName}
            </Navbar.Brand>
          </Nav.Link>
          <NavDropdown title={profileImage} id="basic-nav-dropdown">
              <NavDropdown.Item as={Link} to="/profile">Profile</NavDropdown.Item>
              <NavDropdown.Item as={Link} to="/saved">Saved</NavDropdown.Item>
              <NavDropdown.Item as={Link} to="/settings">Settings</NavDropdown.Item>
              <NavDropdown.Divider />
              <NavDropdown.Item onClick={() => auth.logout()}>Log Out</NavDropdown.Item>
            </NavDropdown>
        </Navbar.Collapse>
      </Container>
    </Navbar>
  );
}

export default Header;
