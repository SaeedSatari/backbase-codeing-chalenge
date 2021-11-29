# backbase-backend-assignment

This project created to solve https://www.backbase.com/ coding challenge.

This project provided to be able to run using docker-compose, so please make sure you already have Docker and Docker
Compose installed on your machine.

## Build the project

Build the Docker image using the following command:

```bash
   docker build -f Dockerfile -t backbase .
```

For being sure that the image is build successfully, use the following command:

```bash
   docker images
```

## Run the project

As mentioned above, this project is able to run using docker-compose, for starting the project follow the command:

```bash
   docker-compose up -d
```

You can access to the logs using the following command:

```bash
   docker-compose logs -f
```

Make sure your service is running with the below command:

```bash
   docker ps
```

## Stop the project

You can use the below command to stop the project:

```bash
   docker-compose down********
```

## Test the project
I provided a postman collection which is existing in postman folder in the root of the project.

Please import the postman collection in your postman app.
Watch the below video to see, how test the project.
https://www.awesomescreenshot.com/video/6038995