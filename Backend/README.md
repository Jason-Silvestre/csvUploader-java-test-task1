
Project to show the capability of working with Rest Services, Rest frameworks, database, data processing and searching with CSV files.

# Using Maven
maven build

Build the Docker image: Run the command below to build the Docker image:
docker build -t app

Run the container: After building the image, you can run the container:

docker run -p 8080:8080 my-spring-boot-app

# Steps for using the Docker Compose
# Build the Spring Boot application image
docker build -t my-spring-boot-app .


# Run the containers
docker-compose up
With this configuration, the Spring Boot application will run in the springboot-app container and connect to the PostgreSQL database in the postgres container. 
The application will be accessible at http://localhost:8080.

# Swagger 
After launching your Spring Boot application, 
you can access Swagger's interactive documentation at
http://localhost:8080/swagger-ui/.