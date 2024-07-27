import axios from "axios";
import React, { useEffect, useState } from "react";
import { useSearchParams } from "react-router-dom";
import { SERVER_BASE_URL } from "../../constants/backend-server";

const ConfirmAccount = () => {
    const [responseStatus, setResponseStatus] = useState<number>(404);
    const [searchParams] = useSearchParams();
    const token: string | null = searchParams.get("token");

    useEffect(() => {
        async function sendEmailToken() {
            try {
                const response = await axios.post(`${SERVER_BASE_URL}/verification/account-registration`, { token: token });
                setResponseStatus(response.status);
            } catch (err: any) {
                console.log("Error ", err)
            }
        }
        sendEmailToken();
    }, [token]);

    return (
        <section className="container forms">
            <div className="form signup">
                <div className="form-content">
                    {responseStatus === 200 && (<p>Your account has been verified! Please click <a href="/login">this</a> to login to your account.</p>)}
                    {responseStatus === 202 && (<p>We have sent you a new email verification! Please check your email!</p>)}
                    {responseStatus === 404 && (<p>Your account has been verified or token is invalid! Please try again!</p>)}
                </div>
            </div>
        </section>
    );
};

export default ConfirmAccount;
