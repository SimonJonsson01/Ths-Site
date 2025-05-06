# Ths-Site
This is the Ths-Site project. It's a fully-developed fullstack-website with a React frontend and a Spring Boot backend. It's purpose is to provide smaller companies (as shown in the project, a service for essential household appliances) a way to receive potential customers and jobs.


## Setup
The following tools is required to run this project:
- Node - *FRONTEND*
- Java 21 (Long Term Support) - *BACKEND*
- Maven (latest) - *BACKEND*
- [Docker](https://www.docker.com/get-started/) - *TO RUN THE FINISHED PROJECT*
- Postgres (through Docker) - *DATABASE*

**Make sure to have the tools above installed, before continuing the setup.**

## Run

### The full project
- Run `docker compose -f 'docker-compose.yml' up -d --build` inside the project's root folder `./Ths-Site`.
- Access the site at `localhost:3000`.

### Frontend
- Install all dependencies: `npm install`.
- Run `npm run dev` inside the project's frontend folder `./Ths-Site/ths-site-frontend`.

### Backend
- Run `./mvnw spring-boot:run` inside the project's backend folder `./Ths-Site/ths-site-backend`.  

### Database
- Run this version of the database command: `docker run -p 5432:5432 -e POSTGRES_PASSWORD=postgres --name ths-site-database postgres:latest`.

## Docker
**A guide of command lines, to set up parts of the project as containers in Docker.**
### The following commands does the following:

`docker compose -f 'docker-compose.yml' up -d --build`
* This will build containers for the frontend, backend and database for the project.
  * This command will need to be run inside the project's root folder `./Ths-Site`. 
  * Having the [Docker extension for VS Code](https://code.visualstudio.com/docs/containers/overview) makes it easier to run the compose-file.
  * To verify that it worked, open Docker Desktop and find 3 **running** containers under the tab labeled `ths-site`.

`docker network create ths-site`
* Creates a network to connect containers to.

`docker run -p 5432:5432 --network ths-site -e POSTGRES_PASSWORD=postgres --name ths-site-database postgres:latest`
* Creates a **database** container for the project.

`docker build -t ths-site-backend .`
* Creates a **backend** container for the project.

`docker build -t ths-site-backend .`
* Creates a **frontend** container for the project.
