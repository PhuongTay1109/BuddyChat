import React, { useState } from 'react';
import { Formik, Form, Field, ErrorMessage } from 'formik';
import { userRegistrationValidation } from '../../utils/vadliation-schema';
import axios from 'axios';
import { REGISTER_POST_ENDPOINT } from '../../constants/backend-server';
import { DEFAULT_AVA_URL } from '../../constants/app';
import { Link } from 'react-router-dom';

const Register: React.FC = () => {
    const [serverError, setServerError] = useState<string | null>(null);
    const [successMessage, setSuccessMessage] = useState<string | null>(null);

    // Initial form values
    const initialValues = {
        firstName: '',
        lastName: '',
        email: '',
        username: '',
        password: '',
        provider: '',
        profilePicture: '',
        confirmPassword: '',
    };

    // Handle form submission
    const handleSubmit = async (values: any) => {
        console.log("sign up")
        const { confirmPassword, ...requestBody } = values;
        requestBody.provider = "LOCAL";
        requestBody.profilePicture = DEFAULT_AVA_URL;


        try {
            console.log(requestBody)
            const response = await axios.post(REGISTER_POST_ENDPOINT, requestBody);
            if (response.status === 201) { // CREATED
                setServerError("");
                setSuccessMessage('Please check your email to verify your account');
            }

        } catch (error: any) {
            setSuccessMessage('');
            if (error.response && error.response.data && error.response.data.message) {
                setServerError(error.response.data.message);

            } else {
                setServerError('An unexpected error occurred. Please try again later.');
            }
        }
    };


    return (
        <section style={{height: '100vh', width: '150vh', padding: "100px"}} className="app-background container">
            <div className="row  justify-content-center">
                <div className="col-md-6">
                    <div className="card" >
                        <div className="card-body ">
                        <h1 style={{ fontFamily: 'Billabong' }} className="card-title text-center mb-2">
                            <Link to="/login" className="text-decoration-none text-black">Simple Blog</Link>                
                        </h1>
                        <h5 style={{ fontFamily: 'Billabong' }} className="card-title text-center mb-4">Register</h5>
                            <Formik
                                initialValues={initialValues}
                                validationSchema={userRegistrationValidation}
                                onSubmit={handleSubmit}
                            >
                                {({ isSubmitting }) => (
                                    <Form>
                                        {serverError && <div className="alert alert-danger">{serverError}</div>}
                                         {successMessage && <div className="alert alert-success">{successMessage}</div>}
                                        <div className="mb-3">
                                            <Field type="text" name="firstName" placeholder="Firstname" className="form-control" />
                                            <ErrorMessage name="firstName" component="div" className="text-danger" />
                                        </div>
                                        <div className="mb-3">
                                            <Field type="text" name="lastName" placeholder="Lastname" className="form-control" />
                                            <ErrorMessage name="lastName" component="div" className="text-danger" />
                                        </div>
                                        <div className="mb-3">
                                            <Field type="email" name="email" placeholder="Email" className="form-control" />
                                            <ErrorMessage name="email" component="div" className="text-danger" />
                                        </div>
                                        <div className="mb-3">
                                            <Field type="text" name="username" placeholder="Username" className="form-control" />
                                            <ErrorMessage name="username" component="div" className="text-danger" />
                                        </div>
                                        <div className="mb-3">
                                            <Field type="password" name="password" placeholder="Create password" className="form-control" />
                                            <ErrorMessage name="password" component="div" className="text-danger" />
                                        </div>
                                        <div className="mb-3">
                                            <Field type="password" name="confirmPassword" placeholder="Confirm password" className="form-control" />
                                            <ErrorMessage name="confirmPassword" component="div" className="text-danger" />
                                        </div>
                                        <div className="text-center field button-field">
                                            <button type="submit" disabled={isSubmitting} className={`btn ${isSubmitting ? 'w-100 disabled' : 'w-100 btn btn-primary'}`}>
                                                {isSubmitting ? 'Registering...' : 'Register'}
                                            </button>
                                        </div>
                                    </Form>
                                )}
                            </Formik>
                            <div className="mt-3 text-center form-link">
                                <span>Already have an account? <a href="/login" className="link login-link text-decoration-none">Login</a></span>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </section>
    );
};

export default Register;
