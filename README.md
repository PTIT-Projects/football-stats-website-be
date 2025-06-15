


# Football Stats Website Backend

**GitHub Repository URL:** https://github.com/PTIT-Projects/football-stats-website-be

## Project Purpose

This is a Spring Boot web application designed to manage English Premier League (EPL) football operations. The system serves as a comprehensive backend API for football management, handling core entities including leagues, seasons, clubs, players, coaches, transfers, matches, and statistics tracking with automated league table calculations.

The application provides RESTful endpoints for CRUD operations, complex business logic for transfer management, match processing, and league ranking calculations.

## Major Functions

### Core Management Features
- **Player Management**: Complete CRUD operations, squad tracking, and transfer history management
- **Club Management**: Club information, coaching staff, and season participation tracking
- **Transfer System**: Transfer recording, fee tracking, and player valuations with complex validation
- **Match Management**: Match scheduling, score tracking, and statistics with automated league table updates
- **League Operations**: Season management, standings, and real-time ranking calculations
- **Authentication**: JWT-based security with user management and token refresh capabilities

### Advanced Features
- **File Upload**: Cloudinary integration for image and document management
- **Dynamic Querying**: Advanced filtering capabilities using Spring Filter JPA
- **API Documentation**: Interactive Swagger UI documentation
- **Complex Squad Queries**: Sophisticated player-club relationship tracking across seasons

## Dependencies and Technology Stack

### Core Technologies
- **Java**: 17
- **Spring Boot**: 3.4.2
- **Database**: MySQL with Spring Data JPA
- **Security**: Spring Security with JWT authentication
- **Build Tool**: Maven
### Key Dependencies

**Spring Boot Starters:** 

**External Libraries:**
- ModelMapper 3.2.2 - Object mapping between DTOs and entities
- Cloudinary 1.39.0 - File storage and CDN
- SpringDoc OpenAPI 2.8.4 - API documentation
- Spring Filter JPA 3.1.9 - Dynamic query filtering 

**Database and Development Tools:** 

## Build and Deploy Instructions

### Prerequisites
- Java 17 or higher
- Maven 3.6+
- MySQL 8.0+

### Environment Configuration
Set up the following environment variables for application configuration:
- Database connection properties
- Cloudinary credentials (cloud-name, api-key, api-secret)
- JWT secret keys  

### Build Instructions

#### Using Maven Wrapper (Recommended)
```bash
# Navigate to the project directory
cd epl-web

# Build the project
./mvnw clean package

# Run the application
./mvnw spring-boot:run
```

#### Using System Maven
```bash
# Navigate to the project directory
cd epl-web

# Build the project
mvn clean package

# Run the application
mvn spring-boot:run
```


### Deployment Options

#### Docker Deployment
The project includes Docker support for containerized deployment: 

#### Production Deployment
1. Build the JAR file: `./mvnw clean package`
2. Set up production database and environment variables
3. Deploy the generated JAR file from `target/epl-web-0.0.1-SNAPSHOT.jar`
4. Build the docker image `docker build -t epl-web .`
5. Run the docker image: `docker container run -p 8080:8080 epl-web`

### API Documentation
Once running, access the interactive API documentation at:
- Swagger UI: `http://localhost:8080/swagger-ui.html/index.html`
- OpenAPI JSON: `http://localhost:8080/v3/api-docs`

