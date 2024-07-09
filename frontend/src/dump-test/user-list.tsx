// components/UserList.tsx

import React, { useEffect, useState } from 'react';
import useApi from '../hooks/api';

interface User {
    userId: number;
    username: string;
    // Add other fields as necessary
}

const UserList: React.FC = () => {
    const [users, setUsers] = useState<User[]>([]);

    const api = useApi()

    useEffect(() => {
        const fetchUsers = async () => {
            try {
                const response = await api.get("/api/v1/users");
                const data = response.data;
                console.log(data)
                setUsers(data.data);
            } catch (error) {
                console.log(error)
            }
        };

        fetchUsers();
    }, []);

    return (
        <div>
            <h1>User List</h1>
            <ul>
                {users.map(user => (
                    <li key={user.userId}>{user.username}</li> 
                ))}
            </ul>
        </div>
    );
};

export default UserList;
