
# CsvUploader

CsvUploader is a Spring Boot application designed to demonstrate capabilities in working with:

 - Rest Services,
 - Rest frameworks
 - Databases
 - Simple data processing and searching with CSV files.

## Features
- Upload musician details via CSV file
- Search for musicians by name or age
- Search musician details by ID
- List all musicians

## Getting Started

### Prerequisites
- Java 17
- Maven 3.6.0 or later
- PostgreSQL (or any other database of your choice)

### Installation

1. Clone the repository
    ```sh
    git clone https://github.com/Jason-Silvestre/csvUploader-java-test-task1.git
    ```

2. Update the `application.properties` file with your database configuration
    ```properties
    #H2 Database
    spring.datasource.url=jdbc:h2:mem:testdb
    spring.datasource.driverClassName=org.h2.Driver
    spring.datasource.username=sa
    spring.datasource.password=
    spring.jpa.hibernate.ddl-auto=update
    spring.h2.console.enabled=true
    spring.h2.console.path=/h2-console
    spring.datasource.platform=h2
    spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
    ```

3. Build the project
    ```sh
    mvn clean install
    ```
## Usage

### Running the Application
To run the application, use the following command:
    ```sh
        mvn spring-boot:run
    ```
# Restful API
- With multiple HTTP endpoints.

## API Endpoints:
### Upload Musicians:
    POST /api/v1/musicians/upload
    Content-Type: multipart/form-data
    Params:
    - file: CSV file containing musician details
    - delimiter (optional): Delimiter used in the CSV file (default is ",")

### Find All Musicians:
    GET /api/v1/musicians/findAllMusicians

### Search Musicians by Name or Age:
    GET /api/v1/musicians/searchByNameOrAge
    Params:
    - query: Name or age to search for

### Get Musician by ID:
    GET /api/v1/musicians/{id}
    
    Example Request:
    http://localhost:8080/api/v1/musicians/searchByNameOrAge?query=Tatiana

#   Built With
#### Spring Boot - The web framework used
#### Maven - Dependency Management
#### The application uses an in-memory H2 database.

####  You can access the H2 console at:
    http://localhost:8080/h2-console

#### with the following credentials:
    JDBC URL: jdbc:h2:mem:testdb
    Username: sa
    Password: (leave blank)

## Delimiters:
- It's supports CSVs with comma, semicolon and dash delimiters.

### Project Structure:

    src
    ├── main
    │   ├── java
    │   │   └── com
    │   │       └── haken
    │   │           └── csvUploader
    │   │               ├── CsvUploaderApplication.java
    │   │               ├── controller
    │   │               ├── service
    │   │               └── repository
    │   └── resources
    │       ├── application.properties
    │       └── schema.sql
    └── test
    └── java
    └── com
    └── haken
    └── csvUploader





