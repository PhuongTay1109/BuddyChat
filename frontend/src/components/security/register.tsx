import React, { useState } from 'react';
import { Formik, Form, Field, ErrorMessage } from 'formik';
import * as Yup from 'yup';
import '../../App.css';
import 'boxicons/css/boxicons.min.css';

import validationSchema from '../../utils/vadliation-schema';
import axios from 'axios';
import { REGISTER_POST_ENDPOINT } from '../../constants/auth';

const Register: React.FC = () => {
    const [serverError, setServerError] = useState<string | null>(null);
    const [successMessage, setSuccessMessage] = useState<string | null>(null);
    const [profilePicture, setProfilePicture] = useState<File | null>(null);

    // Initial form values
    const initialValues = {
        firstName: '',
        lastName: '',
        email: '',
        username: '',
        password: '',
        profilePicture: '',
        confirmPassword: '',
    };

    // Handle form submission
    const onSubmit = async (values: any) => {
        const { confirmPassword, ...requestBody } = values;

        if (profilePicture) {
            try {
                const formData = new FormData();
                formData.append("file", profilePicture);
                const uploadPreset = process.env.REACT_APP_CLOUDINARY_UPLOAD_PRESET;

                if (uploadPreset) {
                    formData.append("upload_preset", uploadPreset);

                    const response = await axios.post(
                        `https://api.cloudinary.com/v1_1/${process.env.REACT_APP_CLOUDINARY_CLOUD_NAME}/image/upload`,
                        formData
                    );

                    requestBody.profilePicture = response.data.secure_url;
                } else {
                    throw new Error('Cloudinary upload preset is not defined');
                }
            } catch (error: any) {
                setServerError('Failed to upload profile picture. Please try again.');
                return;
            }
        }

        try {
            console.log(requestBody)
            const response = await axios.post(REGISTER_POST_ENDPOINT, requestBody);
            if (response.status === 201) { // CREATED
                setSuccessMessage('Please check your email to verify your account');
            }   

        } catch (error: any) {
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
                        validationSchema={validationSchema}
                        onSubmit={onSubmit}
                    >
                        {({ isSubmitting, setFieldValue }) => (
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
                                    <Field type="password" name="password" placeholder="Create password" className="password" />
                                    <ErrorMessage name="password" component="div" className="error" />
                                </div>
                                <div className="field input-field">
                                    <Field type="password" name="confirmPassword" placeholder="Confirm password" className="input" />
                                    <ErrorMessage name="confirmPassword" component="div" className="error" />
                                </div>
                                <div className="field input-field">
                                    <input 
                                        type="file" 
                                        placeholder="Profile picture" 
                                        className="input" 
                                        onChange={(event) => {
                                            const files = event.currentTarget.files;
                                            if (files && files.length > 0) {
                                                const file = files[0];
                                                setFieldValue("profilePicture", file);
                                                setProfilePicture(file);
                                            }
                                        }}  
                                    />
                                    <ErrorMessage name="profilePicture" component="div" className="error" />
                                </div>
                                <div className="field button-field">
                                    <button type="submit" disabled={isSubmitting} className={`btn ${isSubmitting ? 'disabled' : ''}`}>
                                        {isSubmitting ? 'Registering...' : 'Signup'}
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
