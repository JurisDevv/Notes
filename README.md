# Notes

A simple task management web application built with Java.

I created this project to improve my Java skills and get more experience with backend development, databases, and working with HTTP requests.

## Features

- User registration and login
- Password hashing with BCrypt
- User sessions
- Create and manage tasks
- Set task priorities and statuses
- Store user accounts and tasks in PostgreSQL

## Built With

- Java
- Java Servlets
- PostgreSQL
- JDBC
- BCrypt
- HTML, CSS, JavaScript
- Apache Tomcat 9
- Maven

## How It Works

The application uses Java Servlets to handle requests from the frontend. JDBC is used to communicate with the PostgreSQL database.

Users can register an account, log in, and manage their own tasks. Passwords are hashed with BCrypt before being stored in the database.

## Running Locally

1. Install Java, PostgreSQL, and Apache Tomcat 9.
2. Create a PostgreSQL database.
3. Configure the database connection in `Database.java`.
4. Build the project using Maven.
5. Deploy the generated WAR file to Tomcat.
6. Open the application in your browser.

**Status:** In Development
