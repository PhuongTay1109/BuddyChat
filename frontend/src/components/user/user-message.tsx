import { faPaperclip, faPaperPlane, faSmile } from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import React, { useState, useEffect } from 'react';
import { Container, Row, Col, Card, Form, Button } from 'react-bootstrap';
import axios from 'axios'; // Import axios or use fetch API
import { SERVER_BASE_URL } from '../../constants/backend-server';
import useApi from '../../hooks/api';
import SockJS from 'sockjs-client'

interface UserBoxProps {
    user: any;
}
const UserBox = (props: UserBoxProps) => {
    const {user } = props;
    return (
        <li key={user.userId} className="p-2 border-bottom">
            <div className="d-flex justify-content-between">
                <div className="d-flex flex-row">
                    <div>
                        <img
                            src={user.profilePicture}
                            alt="avatar"
                            className="d-flex align-self-center me-3"
                            width="60"
                        />
                        <span className={`badge ${user.status === 'ONLINE' ? 'bg-success' : 'bg-secondary'} badge-dot`}></span>
                    </div>
                    <div className="pt-1">
                        <p className="fw-bold mb-0 text-decoration-none">{user.firstName + user.lastName}</p>
                    </div>
                </div>
                <div className="pt-1">
                    <p className="small text-muted mb-1">Just now</p>
                    <span className="badge bg-danger rounded-pill float-end">3</span>
                </div>
            </div>
        </li>
    )
}

const UserMessage = () => {
    const [search, setSearch] = useState('');
    const [filteredUsers, setFilteredUsers] = useState([]);
    const [loading, setLoading] = useState(false);
    const api = useApi();

    useEffect(() => {
        // Fetch users from the server based on search query
        const fetchUsers = async () => {
            setLoading(true);
            try {
                // Replace with your API endpoint
                const response = await api.get(`${SERVER_BASE_URL}/api/v1/users/search`, {
                    params: { query: search }
                });
                console.log(response)
                const users = response.data.data;
                users.forEach((user: any) => {
                    user.firstName = user.firstName ? user.firstName : "";
                    user.lastName = user.lastName ? user.lastName : "";
                })
                // console.log(users)
                setFilteredUsers(response.data.data);
            } catch (error) {
                console.error('Error fetching users:', error);
            } finally {
                setLoading(false);
            }
        };
        fetchUsers();
    }, [search]);


    // Handle search input change
    const handleSearchChange = (e: any) => {
        // console.log(e.target.value)
        setSearch(e.target.value);
    };

    return (
        <>
            {/* Add custom styles */}
            <style>
                {`
                #chat3 .form-control {
                    border-color: transparent;
                }
                #chat3 .form-control:focus {
                    border-color: transparent;
                    box-shadow: inset 0px 0px 0px 1px transparent;
                }
                .badge-dot {
                    border-radius: 50%;
                    height: 10px;
                    width: 10px;
                    margin-left: 2.9rem;
                    margin-top: -.75rem;
                }
                `}
            </style>

            <section>
                <Container className="py-5">
                    <Row>
                        <Col md={12}>
                            <Card id="chat3" style={{ borderRadius: '15px' }}>
                                <Card.Body>
                                    <Row>
                                        <Col md={6} lg={5} xl={4} className="mb-4 mb-md-0">
                                            <div className="p-3">
                                                <div className="input-group rounded mb-3">
                                                    <Form.Control
                                                        type="search"
                                                        placeholder="Search"
                                                        aria-label="Search"
                                                        aria-describedby="search-addon"
                                                        className="rounded"
                                                        value={search}
                                                        onChange={handleSearchChange}
                                                    />
                                                    <span className="input-group-text border-0" id="search-addon">
                                                        <i className="fas fa-search"></i>
                                                    </span>
                                                </div>

                                                <div style={{ position: 'relative', height: '400px', overflowY: 'auto' }}>
                                                    {/* List of users */}
                                                    <ul className="list-unstyled mb-0">
                                                        {loading ? (
                                                            <li className="p-2 text-center">Loading...</li>
                                                        ) : filteredUsers.length > 0 ? (
                                                            filteredUsers.map((user: any) => (
                                                                <UserBox user={user}/>
                                                            ))
                                                        ) : (
                                                            <li className="p-2 text-center">No users found</li>
                                                        )}
                                                    </ul>
                                                </div>
                                            </div>
                                        </Col>

                                        {/* List of messages with a user */}
                                        <Col md={6} lg={7} xl={8}>
                                            <div
                                                className="pt-3 pe-3"
                                                style={{ position: 'relative', height: '400px', overflowY: 'auto' }}
                                            >
                                                <div className="d-flex flex-row justify-content-start">
                                                    <img
                                                        src="https://mdbcdn.b-cdn.net/img/Photos/new-templates/bootstrap-chat/ava6-bg.webp"
                                                        alt="avatar 1"
                                                        style={{ width: '45px', height: '100%' }}
                                                    />
                                                    <div>
                                                        <p
                                                            className="small p-2 ms-3 mb-1 rounded-3"
                                                            style={{ backgroundColor: '#f5f6f7' }}
                                                        >
                                                            Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.
                                                        </p>
                                                        <p className="small ms-3 mb-3 rounded-3 text-muted float-end">12:00 PM | Aug 13</p>
                                                    </div>
                                                </div>

                                                <div className="d-flex flex-row justify-content-end">
                                                    <div>
                                                        <p className="small p-2 me-3 mb-1 text-white rounded-3 bg-primary">
                                                            Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui ratione voluptatem sequi nesciunt.
                                                        </p>
                                                        <p className="small me-3 mb-3 rounded-3 text-muted">12:00 PM | Aug 13</p>
                                                    </div>
                                                    <img
                                                        src="https://mdbcdn.b-cdn.net/img/Photos/new-templates/bootstrap-chat/ava1-bg.webp"
                                                        alt="avatar 1"
                                                        style={{ width: '45px', height: '100%' }}
                                                    />
                                                </div>


                                                <div className="d-flex flex-row justify-content-start">
                                                    <img
                                                        src="https://mdbcdn.b-cdn.net/img/Photos/new-templates/bootstrap-chat/ava6-bg.webp"
                                                        alt="avatar 1"
                                                        style={{ width: '45px', height: '100%' }}
                                                    />
                                                    <div>
                                                        <p
                                                            className="small p-2 ms-3 mb-1 rounded-3"
                                                            style={{ backgroundColor: '#f5f6f7' }}
                                                        >
                                                            Neque porro quisquam est, qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit, sed quia non numquam eius modi tempora incidunt ut labore et dolore magnam aliquam quaerat voluptatem.
                                                        </p>
                                                        <p className="small ms-3 mb-3 rounded-3 text-muted float-end">12:00 PM | Aug 13</p>
                                                    </div>
                                                </div>
                                            </div>

                                            <div className="text-muted d-flex justify-content-start align-items-center pe-3 pt-3 mt-2">
                                                <img
                                                    src="https://mdbcdn.b-cdn.net/img/Photos/new-templates/bootstrap-chat/ava6-bg.webp"
                                                    alt="avatar 3"
                                                    style={{ width: '40px', height: '100%' }}
                                                />
                                                <Form.Control
                                                    type="text"
                                                    className="form-control form-control-lg"
                                                    placeholder="Type message"
                                                />
                                                <a className="ms-1 text-muted" href="#!"><FontAwesomeIcon icon={faPaperclip} /></a>
                                                <a className="ms-3 text-muted" href="#!"><FontAwesomeIcon icon={faSmile} /></a>
                                                <Button variant="primary" className="ms-3">
                                                    <FontAwesomeIcon icon={faPaperPlane} />
                                                </Button>
                                            </div>
                                        </Col>
                                    </Row>
                                </Card.Body>
                            </Card>
                        </Col>
                    </Row>
                </Container>
            </section>
        </>
    );
};

export default UserMessage;
