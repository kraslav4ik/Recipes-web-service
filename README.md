# Recipes Web Service

Back-end part of the Web application for recipe processing based on Spring-Boot. Spring security is used for authentication. MySQL as db. 

Each recipe is related to registered user. Application allows save | load recipes from Database and gives rights to change | delete concrete recipe only for corresponding user (for user who uploaded it).

## Launch

This application has embedded server (Apache Tomcat).

Add your database info (url, username, password) using next bash commands.

```bash
Recipes-Web-Service/> 
  echo spring.datasource.url=jdbc:mysql://YOUR_DATABASE_URL >> ./src/main/resourses/application.properties
  echo spring.datasource.username=YOUR_USERNAME >> ./src/main/resourses/application.properties
  echo spring.datasource.password=YOUR_PASSWORD >> ./src/main/resourses/application.properties
```
To launch the app:
```bash
Recipes-Web-Service/> ./gradlew bootRun
```

To shut down the server send the empty POST request to localhost: PORT/actuator/shutdown.
```bash
/> curl -X POST localhost:PORT/actuator/shutdown
```
Where `PORT` - your port.
