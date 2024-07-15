<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>
<body>
    <h1>Buddy chat</h1>
    <h2>Description</h2>
    <p>
       A full-stack application for realtime messaging built with Spring Boot, ReactJS, MongoDB, and Spring WebSocket.
    </p>    
    <h2>Features</h2>
    <ul>
         <li>User authentication and authorization with Spring Security</li>
        <li>Login with google and facebook</li>
        <li>Forget password (send an email link to reset)</li>
        <li>JWT Authentication (Access Token & Refresh Token)</li>
        <li>Real-time chat using WebSocket</li>
        <li>MongoDB for data persistence</li>
        <li>ReactJS for the front-end</li>
    </ul>
    <h2>Installation</h2>
    <h3>Prerequisites</h3>
    <ul>
        <li><strong>JDK 11</strong> or higher</li>
        <li><strong>Maven</strong></li>
        <li><strong>Node.js and npm</strong></li>
        <li><strong>MongoDB</strong></li>
      <li><strong>Google and Facebook Developer Accounts for OAuth</strong></li>
    </ul>    
    <h3>Steps</h3>
    <ol>
        <li><strong>Clone the repository</strong>:
            <pre><code>git clone https://github.com/PhuongTay1109/BuddyChat.git</code></pre>
        </li>
        <li><strong>Navigate to the project directory</strong>:
            <pre><code>cd BuddyChat</code></pre>
        </li>
        <li><strong>Set up the back-end</strong>:
            <pre><code>cd backend
mvn clean install</code></pre>
        </li>
        <li><strong>Set up the front-end</strong>:
            <pre><code>cd frontend
npm install</code></pre>
        </li>
    </ol>
   <h2>Configuration</h2>
    <h3>Back-end</h3>
    <ol>
        <li><strong>Create an application.properties file in the src/main/resources directory.</strong> </li>
        <li><strong>Add your MongoDB connection details, JWT secret key, and OAuth credentials</strong>:
            <pre><code>
spring.data.mongodb.uri=connection-string
jwt.secret=your-jwt-secret-key
spring.security.oauth2.client.registration.google.client-id=your-google-client-id
spring.security.oauth2.client.registration.google.client-secret=your-google-client-secret
spring.security.oauth2.client.registration.facebook.client-id=your-facebook-client-id
spring.security.oauth2.client.registration.facebook.client-secret=your-facebook-client-secret
            </code></pre>
        </li>
    </ol>
    <h3>Front-end</h3>
    <ol>
        <li><strong>Configure the API endpoints and OAuth credentials in your ReactJS application, typically in an environment file or directly in the code where you make API calls.</strong> </li>
    </ol>
     <h2>Running the application</h2> 
    <ol>
        <li><strong>Start MongoDB server</strong></li>
        <li><strong>Start the back-end</strong>:
            <pre><code>mvn spring-boot:run</code></pre>
        </li>
        <li><strong>Start the front-end</strong>:
            <pre><code>npm start</code></pre>
        </li>
    </ol>
    <h2>Usage</h2>
    <ul>
         <li>Navigate to http://localhost:3000 to use the application.</li>
        <li>Register and log in using email/password or via Google/Facebook.</li>
        <li>Use the chat feature to communicate in real-time.</li>
    </ul>
</body>
</html>
