import React, { useState } from 'react';
import { Modal, Button, Form, Spinner } from 'react-bootstrap';
import { SERVER_BASE_URL } from '../../constants/backend-server';
import useApi from '../../hooks/api';

interface ChangeProfilePictureModalProps {
    show: any, handleClose: any, userId: any, setProfilePicture: any,
}

const ChangeProfilePictureModal = (props: ChangeProfilePictureModalProps) => {
    const { show, handleClose, userId, setProfilePicture } = props;
    const [selectedFile, setSelectedFile] = useState<File | null>(null);
    const [previewUrl, setPreviewUrl] = useState<string | null>(null);
    const [loading, setLoading] = useState<boolean>(false); // New loading state

    const api = useApi();

    const handleFileChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        const file = event.target.files?.[0] || null;
        setSelectedFile(file);


        if (file) {
            const reader = new FileReader();
            reader.onloadend = () => {
                setPreviewUrl(reader.result as string);
            };
            reader.readAsDataURL(file);
        } else {
            setPreviewUrl(null);
        }
    };

    const handleSubmit = async (event: any) => {
        event.preventDefault();
        if (selectedFile) {
            const formData = new FormData();
            formData.append('image', selectedFile);
            formData.append('userId', userId);
            setLoading(true); // Start loading
            try {
                const response = await api.post(`${SERVER_BASE_URL}/api/v1/upload/user-image`, formData, {
                    headers: {
                        'Content-Type': 'multipart/form-data',
                    },
                });

                const newImageUrl = response.data.data;
                setProfilePicture(newImageUrl);
                handleCloseModal();
            } catch (err) {
                console.error('Error uploading image:', err);
            } finally {
                setLoading(false)
            }
        }
    };

    const handleCloseModal = () => {
        setPreviewUrl(null);
        handleClose();
    }

    return (
        <Modal show={show} onHide={handleCloseModal}>
            <Modal.Header closeButton>
                <Modal.Title>Change Profile Picture</Modal.Title>
            </Modal.Header>
            <Modal.Body>
                <Form onSubmit={handleSubmit}>
                    <Form.Group>
                        <Form.Label>Choose new profile picture</Form.Label>
                        <input
                            type="file"
                            accept="image/*"
                            onChange={handleFileChange}
                            className="form-control"
                        />
                    </Form.Group>
                    {previewUrl && (
                        <div className="mt-3 text-center">
                            <img src={previewUrl} alt="Preview" style={{ width: '80%', height: '80%' }} className="img-fluid rounded" />
                        </div>
                    )}
                    {loading ? (
                        <div className="text-center mt-3">
                            <Spinner animation="border" role="status">
                                <span className="visually-hidden">Loading...</span>
                            </Spinner>
                        </div>
                    ) : (
                        <Button variant="primary" type="submit" className="mt-3" disabled={!previewUrl}>
                            Save Changes
                        </Button>
                    )}
                </Form>
            </Modal.Body>
        </Modal>
    );
};

export default ChangeProfilePictureModal;
