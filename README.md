# java-rate-limiter

This project can mount a server and expose a `GET /message` endpoint from which a message is returned from the [Fuck Off as a Service](https://www.foaas.com/) service.

The only restriction is that a user consuming this endpoint can use it UP TO 5 times within a 10 second period.

The userID is identified from the `user-id` header.

## Dependencies
- [Spring Boot](https://spring.io/projects/spring-boot) 🚀
- [Guava Cache](https://guava.dev/releases/21.0/api/docs/com/google/common/cache/Cache.html) ⚡️
- [Lombok](https://guava.dev/releases/21.0/api/docs/com/google/common/cache/Cache.html) ⚡️

## Use Cases
- The API with a given userId is consumed once and returns the service message
- The API is consumed 5 times within a period of 10 seconds and it returns the 5 messages from the service
- The API is consumed 6 times within a period of 10 seconds and the sixth call returns an error.
- The API is consumed 6 times within a period of 10 seconds, a seventh call is made 10 seconds after the first call and it returns a message from the service
