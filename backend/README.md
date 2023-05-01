# Flock
Flock is a simple yet fully functional Twitter clone, built using modern web technologies. The project is divided into two main parts: the frontend and the backend.

## Frontend
The frontend of Flock is built using Angular, a popular and powerful web application framework. We used HTML, TypeScript, and SCSS for creating the user interface and handling user interactions. The frontend communicates with the backend using HTTP requests over a RESTful API, allowing users to perform CRUD (Create, Read, Update, and Delete) operations for tweets and replies.

Firebase authentication is integrated for user logins, providing a secure and reliable way to manage user accounts. The frontend is designed to be responsive and user-friendly, ensuring a smooth user experience across various devices.

## Backend
The backend of Flock is developed using the Spring Boot framework, which provides a fast and efficient way to build Java-based web applications. The backend serves as the API that the frontend interacts with and handles all the business logic and database operations.

The project uses MySQL for data storage, with the data access layer implemented using JDBC. The backend supports JSON data exchange for seamless communication with the frontend. Tomcat is used as the embedded server, providing a lightweight and easy-to-deploy solution for running the application.

The backend is designed to handle CRUD operations, including creating, reading, updating, and deleting tweets and replies. The business logic for these operations is mainly implemented in the FlockServiceLayerImpl and FlockDaoImpl classes, ensuring proper data validation and persistence.

Overall, Flock showcases a modern, full-stack web application built using best practices and up-to-date technologies. This project serves as an excellent starting point for anyone looking to learn web development or expand their existing skillset.
