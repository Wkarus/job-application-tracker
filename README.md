# Job Application Tracker

REST API to track job applications, built with Java and Spring Boot.
*API REST para acompanhar candidaturas a vagas, feita com Java e Spring Boot.*

> 🚧 Work in progress / Em desenvolvimento. Built step by step while I learn Java and Spring.

## Features / Funcionalidades

- Create, list, update and delete applications
- Find an application by id (404 when it does not exist)
- Filter by status: `ENVIADA`, `ENTREVISTA`, `RECUSADA`, `OFERTA`
- Input validation (company and job title are required)

## Tech stack

- Java 21
- Spring Boot 4 (Web, Data JPA, Validation)
- H2 in-memory database (PostgreSQL planned)
- Maven

## Running locally / Como rodar

```bash
./mvnw spring-boot:run
```

The API starts at `http://localhost:8080`. No Windows, use `mvnw.cmd spring-boot:run`.

## Endpoints

| Method | Route | Description |
|---|---|---|
| POST | `/candidaturas` | Create an application |
| GET | `/candidaturas` | List all (optional `?status=ENTREVISTA`) |
| GET | `/candidaturas/{id}` | Get one by id |
| PUT | `/candidaturas/{id}` | Update |
| DELETE | `/candidaturas/{id}` | Delete |

Example / Exemplo:

```bash
curl -X POST http://localhost:8080/candidaturas \
  -H "Content-Type: application/json" \
  -d '{"empresa":"Example Ltd","vaga":"Junior Developer","link":"https://example.com/job"}'
```

## Roadmap

- [x] CRUD and validation
- [ ] Automated tests (JUnit) and CI with GitHub Actions
- [ ] PostgreSQL with Docker Compose
- [ ] Login with Spring Security and deployment
- [ ] Funnel metrics and follow-up reminders
- [ ] Simple React front end

## Author

[Icaro Souza](https://www.linkedin.com/in/icaro-souza-dev/)
