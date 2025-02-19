# JavaFX Chat Application
## Description
This is a simple multi-client chat application using Java sockets and JavaFX (GUI). First, the user goes through **registration**, then **authentication**, and only after that the chat window opens and you can **communicate**.
The project is currently under development, below is To-Do-list. 

This video demonstrates the current version of the application:

https://github.com/user-attachments/assets/b2654dad-4adc-42ff-a545-df19d1938c62


### Project Requirements

- JDK 17
- JavaFX
- PostgresSQL

### Project Structure

- The "Clients" module contains the client-side of the project.
- The "Server" module contains the server-side of the project.
- The "lib" folder contains the required JavaFX JAR files.
- The "script.sql" file contains the database schema for generation table users.

## Instructions

### Database Setup
1. Create a PostgresSQL database named "chat".
2. Import the "script.sql" into the database.

### Application Setup
All application components run on a localhost.
1. Run the Server(port 9001).
2. Run the ClientsApplication (port 8080).
3. Run the ClientsApplicationTwo (port 9003).
