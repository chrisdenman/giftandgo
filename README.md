# Gift & Go

## Notes

- Feature based using clean architecture principles.
- [./post.sh](./post.sh) may be used to post the contents of the file [./data.csv](./data.csv) with `curl` when the
  application is running.
- `spring-modulith` based.

## Requirements

- JDK 17
- Kotlin 1.9.22

## Branches

- main - Spring Boot
- modulith - Utilising Spring Boot Modulith

## Modulith ToDo

- refactor the event transceiver to handle multi-requesting
- convert the rest of the features to messaging
- convert into a multi-project gradle project with each module as nested project
    - use internal modifier?

## Links

- [H2 Console](http://localhost:8080/h2-console)
