
# IMDb Backend API

This project is a backend API for managing movies, actors, users, and reviews using **Spring Boot**, **PostgreSQL**, and **GraphQL**. The project is structured using **Hexagonal Architecture**, ensuring loose coupling and separation of concerns. It also utilizes **Spring Security** for user authentication and **Docker** for containerization. **JUnit** is used for testing purposes.

## Features

- **Spring Boot** for backend development
- **PostgreSQL** for data storage
- **GraphQL** for API interactions
- **Spring Security** for user authentication and authorization
- **Docker** for easy deployment and containerization
- **JUnit** for unit testing

## Technologies Used

- **Spring Boot** 2.x
- **GraphQL** (Spring Boot integration)
- **PostgreSQL** database
- **Spring Security**
- **Docker**
- **JUnit 5** (for unit testing)

## Getting Started

To get a local copy up and running, follow these simple steps.

### Prerequisites

- **Java 17** or higher
- **PostgreSQL** database installed
- **Docker** 

### Installing

1. Clone the repository:

   ```bash
   git clone https://github.com/Mumtaz203/jmdb-backend.git
   ```

2. Clean the package:

   ```bash
   ./mvnw clean package
   ```

3. Docker :
   ```bash
   docker-compose down -v
   ```

4. Set up PostgreSQL locally or use Docker:

    - **Using Docker** (Recommended):

      ```bash
      docker-compose up --build
      ```

    - **Without Docker**: Set up PostgreSQL on your machine and update the database connection details in the `application.yml` or `application.properties` file.

4. Build and run the application:

   ```bash
   ./mvnw spring-boot:run
   ```

5. The API will be running at `http://localhost:8080`.

## GraphQL Endpoints

- **Mutation** for creating and updating data:
    - `createUser(input: UserInput!): UserResponse`
    - `createMovie(input: MovieInput!): MovieResponse`
    - `createReview(input: ReviewInput!): ReviewResponse`

- **Query** for retrieving data:
    - `getActor(id: Int!): ActorResponse`
    - `getAllActors: [ActorResponse!]!`
    - `getMovie(id: Int!): MovieResponse`
    - `me: String` (For logged-in users to fetch their information)

You can look .graphqls schemas for all endpoints.
## User Authentication

This project includes user authentication via **Spring Security**:

- **Login**:
    - Input: `email`, `password`
    - Returns: A boolean value indicating whether the login is successful.

- **Register**:
    - Input: `email`, `username`, `password`
    - Returns: A `UserResponse` object containing user details.

## Database Relationships

- **Actor Movie (Many-to-Many)**: An actor can be associated with multiple movies, and a movie can have multiple actors.
- **User Review (One-to-Many)**: A user can write multiple reviews for different movies.
- **Director Movie (One-to-Many)**: A director can direct multiple movies, but a movie can have only one director.

## Running Tests

You can run tests with **JUnit**:

```bash
./mvnw test
```

## Docker

Docker is used to containerize the application. To run the project inside a Docker container:

1. Build the Docker image:

   ```bash
   docker build -t spring_app .
   ```

2. Run the Docker container:

   ```bash
   docker run -p 8080:8080 spring_app
   ```

## Hexagonal Architecture

The project follows the **Hexagonal Architecture**, also known as the **Ports and Adapters** pattern, which ensures that the core logic of the application is independent of the frameworks, databases, and other external dependencies. This design helps in creating maintainable, testable, and flexible code.

## Contributing

1. Fork the project
2. Create a new branch for your feature (`git checkout -b feature-name`)
3. Commit your changes (`git commit -am 'Add new feature'`)
4. Push to the branch (`git push origin feature-name`)
5. Create a new Pull Request

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
