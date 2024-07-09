import React, { useState } from 'react';
import { Formik, Form, Field, ErrorMessage } from 'formik';
import * as Yup from 'yup';
import '../../App.css';
import 'boxicons/css/boxicons.min.css';

import { userRegistrationValidation} from '../../utils/vadliation-schema';
import axios from 'axios';
import { REGISTER_POST_ENDPOINT } from '../../constants/api';
import { DEFAULT_AVA_URL } from '../../constants/app';

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
        <section className="container forms">
            <div className="form signup">
                <div className="form-content">
                    <header>Register</header>
                    <Formik
                        initialValues={initialValues}
                        validationSchema={userRegistrationValidation}
                        onSubmit={handleSubmit}>
                        {({ isSubmitting }) => (
                            <Form className="custom-form">
                                {serverError && <div className="alert alert-danger">{serverError}</div>}
                                {successMessage && <div className="alert alert-success">{successMessage}</div>}
                                <div className="field input-field">
                                    <Field type="text" name="firstName" placeholder="Firstname" className="input" />
                                    <ErrorMessage name="firstName" component="div" className="error" />
                                </div>
                                <div className="field input-field">
                                    <Field type="text" name="lastName" placeholder="Lastname" className="input" />
                                    <ErrorMessage name="lastName" component="div" className="error" />
                                </div>
                                <div className="field input-field">
                                    <Field type="email" name="email" placeholder="Email" className="input" />
                                    <ErrorMessage name="email" component="div" className="error" />
                                </div>
                                <div className="field input-field">
                                    <Field type="text" name="username" placeholder="Username" className="input" />
                                    <ErrorMessage name="username" component="div" className="error" />
                                </div>
                                <div className="field input-field">
                                    <Field type="password" name="password" placeholder="Create password" className="input" />
                                    <ErrorMessage name="password" component="div" className="error" />
                                </div>
                                <div className="field input-field">
                                    <Field type="password" name="confirmPassword" placeholder="Confirm password" className="input" />
                                    <ErrorMessage name="confirmPassword" component="div" className="error" />
                                </div>
                                <div className="field button-field">
                                    <button type="submit" disabled={isSubmitting} className={`btn ${isSubmitting ? 'disabled' : ''}`}>
                                        {isSubmitting ? 'Registering...' : 'Register'}
                                    </button>
                                </div>
                            </Form>
                        )}
                    </Formik>
                    <div className="form-link">
                        <span>Already have an account? <a href="/login" className="link login-link">Login</a></span>
                    </div>
                </div>
            </div>
        </section>
    );
};

export default Register;
