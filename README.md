# Electricity Management System

This project is a Java-based web application designed to manage electricity usage and billing for users. The application uses servlets to handle user management, billing, and authentication features.

## Features

- **User Management**: Create, read, update, and delete user information.
- **Billing Management**: Check and update the billing status for users.
- **Authentication**: Securely authenticate admin and user roles.
- **Authorization**: Verify admin and user permissions. 
- **Database Integration**: Interact with the database to store and retrieve user information.

## Technologies Used

- Java Servlets
- JDBC for database interaction
- JWT for authentication
- PostgreSQL as the database
- Apache Tomcat (or another servlet container) for running the application

## Installation and Setup

1. **Clone the repository**:

    ```bash
    git clone <repository_url>
    ```

2. **Navigate to the project directory**:

    ```bash
    cd electricity-management-system
    ```

3. **Configure the database**:
    - Ensure that PostgreSQL is installed and running.
    - Update the database connection settings in your project configuration.

4. **Create the required tables in the database**:
    - Connect to your PostgreSQL database and execute the following SQL commands to create the `admininfo` and `userinfo` tables:

        ```sql
        CREATE TABLE admininfo (
            id SERIAL PRIMARY KEY,
            username VARCHAR(255) NOT NULL UNIQUE,
            password VARCHAR(255) NOT NULL
        );

        CREATE TABLE userinfo (
            id SERIAL PRIMARY KEY,
            username VARCHAR(255) NOT NULL UNIQUE,
            password VARCHAR(255) NOT NULL,
            address VARCHAR(255),
            billamount DOUBLE PRECISION,
            billstatus VARCHAR(50)
        );
        ```
        
5. **Deploy the application**:
    - Deploy the application to your servlet container (e.g., Apache Tomcat) using your preferred method.

6. **Access the application**:
    - Visit `http://localhost:8080/electricity/` in your browser to access the application.
