# backbase-backend-assignment

This project created to solve https://www.backbase.com/ coding challenge.

## Build the project

I can add the below features for the next version to have a better product:

1) Configure the project to using Swagger to providing API documentation
2) Add new tests(integration and unit tests. Because of time limits, I just added unit tests for the most important
   use-cases)
3) Refactor the security parts to using OAuth2
4) Add metric to capture more details and provide more data to create monitoring system
5) Use message brokers(e.g Kafa) to save movie details which is not existing in our movie database, after calling
   OMDBClient in asynchronous way
6) Provide CI/CD scripts to make the CI/CD process automatically
7) Refactor DBInit to use Spring batch for reading and storing csv file in database
8) Better exception handling
9) Use flyway or liquibase to provide automatic DB migration