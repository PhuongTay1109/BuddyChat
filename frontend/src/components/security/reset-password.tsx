import React, { useState } from 'react';
import { useLocation, Link } from 'react-router-dom';
import { Formik, Form, Field, ErrorMessage } from 'formik';
import axios from 'axios';
import * as Yup from 'yup';

const ResetPassword: React.FC = () => {
  const [message, setMessage] = useState<string | null>(null);
  const [success, setSuccess] = useState<boolean>(false);

  const query = new URLSearchParams(useLocation().search);
  const token = query.get('token');

  const initialValues = { password: '', confirmPassword: '' };

  const validationSchema = Yup.object({
    password: Yup.string()
      .min(5, 'Password must be at least 5 characters')
      .max(15, 'Password must be at most 15 characters')
      .required('Password is required'),
    confirmPassword: Yup.string()
      .oneOf([Yup.ref('password') as unknown as string | undefined, undefined], 'Passwords must match')
      .required('Confirm password is required'),
  });

  const handleSubmit = async (values: { password: string; confirmPassword: string }, { setSubmitting }: { setSubmitting: (isSubmitting: boolean) => void }) => {
    try {
      const response = await axios.post('http://localhost:8080/api/v1/verification/reset-password', {
        token,
        password: values.password,
      });
      setMessage(response.data.message);
      if (response.data.statusCode === 200) {
        setSuccess(true);
      }
    } catch (error: any) {
      setMessage(error.response?.data?.message || 'Something went wrong');
    } finally {
      setSubmitting(false);
    }
  };

  return (
    <section style={{ height: '100vh', width: '150vh', padding: '100px' }} className="app-background container">
      <div className="row justify-content-center">
        <div className="col-md-6">
          <div className="card m-auto">
            <div style={{ padding: '40px' }} className="card-body">
            <h1 style={{ fontFamily: 'Billabong' }} className="card-title text-center mb-2">
                <Link to="/login" className="text-decoration-none text-black">Simple Blog</Link>                
            </h1>
            <h5 style={{ fontFamily: 'Billabong' }} className="card-title text-center mb-4">Reset password</h5>
              <Formik
                initialValues={initialValues}
                validationSchema={validationSchema}
                onSubmit={handleSubmit}
              >
                {({ isSubmitting }) => (
                  <Form>
                    {message && (
                      <div className={`alert ${message.includes('successfully') ? 'alert-success' : 'alert-danger'}`}>{message}</div>
                    )}
                    {!success && (
                      <>
                        <div className="mb-3">
                          <Field type="password" placeholder="New Password" name="password" id="password" className="form-control" />
                          <ErrorMessage name="password" component="div" className="text-danger" />
                        </div>
                        <div className="mb-3">
                          <Field type="password" placeholder="Confirm New Password" name="confirmPassword" id="confirmPassword" className="form-control" />
                          <ErrorMessage name="confirmPassword" component="div" className="text-danger" />
                        </div>
                        <div className="mb-3 text-center">
                          <button type="submit" disabled={isSubmitting} className="w-100 btn btn-primary">
                            {isSubmitting ? 'Resetting...' : 'Reset Password'}
                          </button>
                        </div>
                      </>
                    )}
                    {success && (
                      <div className="text-center">
                        <Link to="/login" className="btn btn-link">Click here to log in</Link>
                      </div>
                    )}
                  </Form>
                )}
              </Formik>
            </div>
          </div>
        </div>
      </div>
    </section>
  );
};

export default ResetPassword;
