# Gift & Go

## Notes

- Feature based using clean architecture principles.
- [./post.sh](./post.sh) may be used to post the contents of the test resource [data.csv](./modules/app-spring/src/test/resources/data.csv) with `curl` when the
  application is running.
- Gradle composite build.

## Requirements

- JDK 22
- Kotlin 1.9.22
- Linux with `curl` (for [./post.sh](./post.sh))
- Linux with `kill` (for [./gatling.sh](./post.sh))

## Branches

- [main](https://github.com/chrisdenman/giftandgo) - Spring Boot
- [modulith](https://github.com/chrisdenman/giftandgo/tree/modulith) - Utilising Spring Boot Modulith
- [composite](https://github.com/chrisdenman/giftandgo/tree/composite) - Gradle Composite Build Modular Spring Boot


## Performance Tests

- Gatling performance tests may be run either by:
  - Executing [./gatling.sh](./gatling.sh) or,
  - By first invoking the Gradle(w) task `:app-spring:bootRun` task to start the application and when it has settled, invoking the `:app-spring:gatlingRun` task. 

## ToDo

- Annotations for enforcing architecture constraints

## Links

- [H2 Console](http://localhost:8080/h2-console)
