# Gift & Go

## Notes

- Feature based using clean architecture principles.
- [./post.sh](./post.sh) may be used to post the contents of the file [./data.csv](./data.csv) with `curl` when the
  application is running.
- Gradle composite build.

## Requirements

- JDK 17
- Kotlin 1.9.22
- Linux with `curl` (for [./post.sh](./post.sh))

## Branches

- [main](https://github.com/chrisdenman/giftandgo) - Spring Boot
- [modulith](https://github.com/chrisdenman/giftandgo/tree/modulith) - Utilising Spring Boot Modulith
- [composite](https://github.com/chrisdenman/giftandgo/tree/composite) - Gradle Composite Build Modular Spring Boot

## ToDo

- Gatling performance tests
- Annotations for enforcing architecture constraints

## Links

- [H2 Console](http://localhost:8080/h2-console)
