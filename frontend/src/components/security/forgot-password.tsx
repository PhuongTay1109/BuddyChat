import React, { useState } from 'react';
import { Formik, Form, Field, ErrorMessage } from 'formik';
import axios from 'axios';
import { SERVER_BASE_URL } from '../../constants/backend-server';
import { Link } from 'react-router-dom';

const ForgotPassword: React.FC = () => {
  const [message, setMessage] = useState<string | null>(null);

  const initialValues = { email: '' };

  const handleSubmit = async (values: { email: string }, { setSubmitting }: { setSubmitting: (isSubmitting: boolean) => void }) => {
    try {
      const response = await axios.post(`${SERVER_BASE_URL}/api/v1/auth/forgot-password`, null, { params: { email: values.email } });
      setMessage(response.data.message);
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
              <h5 style={{ fontFamily: 'Billabong' }} className="card-title text-center mb-4">Forgot password</h5>
              <Formik
                initialValues={initialValues}
                onSubmit={handleSubmit}
              >
                {({ isSubmitting }) => (
                  <Form>
                    {message && (
                      <div className={`alert ${message.includes('successfully') ? 'alert-success' : 'alert-danger'}`}>{message}</div>
                    )}
                    <div className="mb-3">
                      <Field type="text" placeholder="Email" name="email" id="email" className="form-control" />
                      <ErrorMessage name="email" component="div" className="text-danger" />
                    </div>
                    <div className="mb-3 text-center">
                      <button type="submit" disabled={isSubmitting} className="w-100 btn btn-primary">
                        {isSubmitting ? 'Sending...' : 'Send Reset Email'}
                      </button>
                    </div>
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

export default ForgotPassword;
