# BrewQuest

BrewQuest is an immersive and feature-rich full-stack web application developed using an impressive stack of technologies, including HTML/CSS, JavaScript, Java, Spring Boot, Bootstrap, and MySql. With a user-friendly interface, BrewQuest empowers users to explore a vast array of breweries, share their valuable reviews, participate as drivers, and earn badges for their brewery visits and insightful reviews. This engaging platform seamlessly combines the love for breweries with a rewarding experience for its users.

Technologies Used

BrewQuest is built using a range of modern technologies, including:

    HTML 5
    CSS
    Spring Boot 3.0.2
    Tomcat
    Spring JPA Data
    Spring Web
    Spring Security
    Maven
    Thymeleaf
    Java 20
    Javascript
    MySQL 8
    Bootstrap 5.3
    Openbrewery_db
    Mapbox api

## Authors

- [@NicholasHubacek03](https://github.com/NicholasHubacek03)
- [@Dfarias1](https://github.com/Dfarias1)
- [@Jim](https://github.com/Jimolson-git)
- [@XavierFrancis9](https://github.com/XavierFrancis9)


## How to Run
Clone the project
```bash 
git clone SSH git@github.com:BrewQuest/BrewQuest.git
```
Make sure to have a mySQL server running with terminal command
```bash
mySQL.server start
```
Create an Application.properties in the following directory.
```bash
touch {project directory}/src/main/java/resources/Application.properties

```
Populate Application.properties with the following content
```bash
server.port=8080
spring.datasource.url=jdbc:mysql://localhost/BrewQuest_db?createDatabaseIfNotExist=true&useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
spring.datasource.username=root
spring.datasource.password=codeup
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect
spring.datasource.hikari.maximum-pool-size=4
```

Ensure latest TomCat 9.0.74 server is ready to deploy on local host. Install all Maven dependencies.

Enjoy!
