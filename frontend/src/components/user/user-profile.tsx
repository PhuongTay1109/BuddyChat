import React, { useEffect, useState } from 'react';
import { Container, Row, Col, Form as BootstrapForm, Button, Image, Alert, Spinner } from 'react-bootstrap';
import { Formik, Field, ErrorMessage } from 'formik';
import { userUpdateValidation } from '../../utils/vadliation-schema';
import useApi from '../../hooks/api';
import { SERVER_BASE_URL } from '../../constants/backend-server';
import { DEFAULT_AVA_URL } from '../../constants/app';
import ChangeProfilePictureModal from './user-profile-picture-modal';

const UserProfile = () => {
  const [userId, setUserId] = useState<number>(-1);
  const [profilePicture, setProfilePicture] = useState<string>(DEFAULT_AVA_URL);
  // const [selectedFile, setSelectedFile] = useState<File | null>(null);
  const [showModal, setShowModal] = useState(false);
  const [formValues, setFormValues] = useState({
    username: '',
    firstName: '',
    lastName: '',
    email: '',
  })
  const [notification, setNotification] = useState<{ show: boolean, message: string } | null>(null);
  const [loading, setLoading] = useState<boolean>(true); // Loading state
  const api = useApi();

  useEffect(() => {
    async function fetchData() {
      try {
        const response = await api.get(`${SERVER_BASE_URL}/api/v1/users/me`);
        const data = response.data.data;
        setUserId(data.userId)
        setFormValues({
          username: data.username,
          firstName: data.firstName,
          lastName: data.lastName,
          email: data.email
        })
        setProfilePicture(data.profilePicture)


      } catch (err) {

        throw err
      } finally {
        setLoading(false)
      }
    }
    fetchData();

  }, [])

  // Initial form values

  const handleSubmit = async (values: any) => {
    // Handle form submission
    try {
      console.log(userId)
      const response = await api.put(`${SERVER_BASE_URL}/api/v1/users/${userId}`, values)
      const data = response.data.data;
      setFormValues({
        username: data.username,
        firstName: data.firstName,
        lastName: data.lastName,
        email: data.email
      })
      setNotification({ show: true, message: 'Profile updated successfully!' });

      setTimeout(() => {
        setNotification(null);
      }, 3000); // Hide notification after 3 seconds
    } catch (err) {
      setNotification({ show: true, message: 'Failed to update profile.' });

      setTimeout(() => {
        setNotification(null);
      }, 3000); // Hide notification after 3 seconds
      throw err;
    }
  };

  if (loading) {
    return (
      <Container>
        <Row className="justify-content-center">
          <Col md={8} lg={6} className="text-center">
            <Spinner animation="border" role="status">
              <span className="visually-hidden">Loading...</span>
            </Spinner>
          </Col>
        </Row>
      </Container>
    );
  }

  const handleModalClose = () => setShowModal(false);
  const handleModalShow = () => setShowModal(true);

  return (
    <Container>
      <Row className="justify-content-center">
        <Col md={8} lg={6}>
          <div className="profile-header text-center mb-1 rounded p-4">
            <Image src={profilePicture} roundedCircle alt="Profile Picture" className="profile-picture mb-1" />
            <h2 className="mb-2">{`${formValues.firstName} ${formValues.lastName}`}</h2>
            <Button variant="outline-secondary" type="submit" onClick={handleModalShow} size="sm">Change Profile Picture</Button>
          </div>
          <ChangeProfilePictureModal
            show={showModal}
            handleClose={handleModalClose}
            userId={userId}
            setProfilePicture={setProfilePicture}
          />
          {notification && notification.show && (
            <Alert variant={notification.message.includes('Failed') ? 'danger' : 'success'}>
              {notification.message}
            </Alert>
          )}
          <Formik
            enableReinitialize={true}
            initialValues={formValues}
            validationSchema={userUpdateValidation}
            onSubmit={handleSubmit}
          >
            {({ handleSubmit }) => (
              <BootstrapForm onSubmit={handleSubmit} className="profile-form">
                <BootstrapForm.Group className="mb-4">
                  <BootstrapForm.Label className='text-dark fw-bold'>Username</BootstrapForm.Label>
                  <Field
                    type="text"
                    name="username"
                    as={BootstrapForm.Control}
                    placeholder="Enter your username"
                    className="shadow form-control-lg"
                  />
                  <ErrorMessage name="username" component="div" className="text-secondary fw-bold mt-1" />
                </BootstrapForm.Group>

                <BootstrapForm.Group className="mb-4">
                  <BootstrapForm.Label className='text-dark fw-bold'>First Name</BootstrapForm.Label>
                  <Field
                    type="text"
                    name="firstName"
                    as={BootstrapForm.Control}
                    placeholder="Enter your first name"
                    className="shadow form-control-lg"
                  />
                  <ErrorMessage name="firstName" component="div" className="text-secondary fw-bold mt-1" />
                </BootstrapForm.Group>

                <BootstrapForm.Group className="mb-4">
                  <BootstrapForm.Label className='text-dark fw-bold'>Last Name</BootstrapForm.Label>
                  <Field
                    type="text"
                    name="lastName"
                    as={BootstrapForm.Control}
                    placeholder="Enter your last name"
                    className="shadow form-control-lg"
                  />
                  <ErrorMessage name="lastName" component="div" className="text-secondary fw-bold mt-1" />
                </BootstrapForm.Group>

                <BootstrapForm.Group className="mb-4">
                  <BootstrapForm.Label className='text-dark fw-bold'>Email</BootstrapForm.Label>
                  <Field
                    type="text"
                    name="email"
                    as={BootstrapForm.Control}
                    placeholder="Enter your email"
                    readOnly
                    className="shadow form-control-lg"
                  />
                </BootstrapForm.Group>

                <div className="d-flex justify-content-between">
                  <Button variant="primary" type="submit" className="btn-m">Save Changes</Button>
                  <Button variant="secondary" type="button" className="btn-m" onClick={() => alert('Change password functionality')}>Change Password</Button>
                </div>
              </BootstrapForm>
            )}
          </Formik>
        </Col>
      </Row>
    </Container>
  );
};

export default UserProfile;
