# JavaFX Chat Application
## Description
This is a simple multi-client chat application using Java sockets and JavaFX (GUI). First, the user goes through registration, then authentication, and only after that the chat window opens and you can communicate.
Currently, in development

### Steps to run the application
- Import this project as a Maven project.
- Run the Server.
- Run the ClientApplication and ClientApplicationTwo.
- Add a new sensor to table sensor from POST request. Sensor name must be unique.
- Add a new measurement to table measurement: value temperature,  (The sensor field must match one of the sensor names registered in the sensor table)

Test the API with client tool such as special app (RestClientForWeatherApp) or Postman to perform various operations.
The app will start running at <http://localhost:8080/api/v1>.