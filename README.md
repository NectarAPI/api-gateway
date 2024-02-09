
# Nectar API API gateway

NectarAPI is a microservices-based, integrated meter device management (MDM) and head-end system (HES) tool for prepaid, STSEd2 meters. It is developed to support high availability for small, medium and large utilities and is intended to be deployed on kubernetes or similar orchestrators. NectarAPI allows utilities to generate and decode IEC62055-41 tokens using its internal virtual HSM or a Prism HSM via the Prism Thrift API. In addition, it allows for subscriber, meter and utility management and multiple STS configurations can be managed using the NectarAPI. NectarAPI uses an API-first approach and exposes feature-rich, REST API endpoints that allow for token generation/decoding, subscribers/users/utility management, logging e.t.c. NectarAPI's virtual HSM is IEC62055-41:2018 (STS6) compliant and supports DES (DKGA02) and KDF-HMAC-SHA-256 (DKGA04) as well as STA (EA07) and MISTY1 (EA11).

The api-gateway is one of the micro-services required to run NectarAPI. Once run, it creates a number of REST endpoints that provide access to an entire NectarAPI deployment. These REST endpoints require authentication and authorization to perform actions on a NectarAPI cluster deployment such as generate STS tokens, decode tokens, create meters, manage subscribers and utilities e.t.c.

Rather thanm authenticating and making calls to the api-gateway directly, NectarAPI SDKs e.g. [nectar-java-sdk](https://github.com/NectarAPI/nectar-java-sdk) may be used.


# Built with

NectarAPI api-gateway is built using Springboot version 3, OpenJDK (Java) version 17, Redis and Gradle8 for builds. 

# Getting Started

To run the api-gateway, first run the [nectar-db](https://github.com/NectarAPI/nectar-db) service and ensure that the credentials and port match those defined in this service's `/src/main/resources/application.yml` configurations. 

Then use gradle to deploy and run the service

`./gradlew build -x test && ./gradlew bootRun`


You should have output similar to the following:

```
  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::                (v3.2.1)

2024-02-08T08:53:17.338Z  INFO 1 --- [           main] k.c.n.api.NectarApiGatewayApplication    : Starting NectarApiGatewayApplication using Java 17.0.10 with PID 1 (/etc/api-gateway/api-gateway.jar started by root in /etc/api-gateway)
2024-02-08T08:53:17.343Z  INFO 1 --- [           main] k.c.n.api.NectarApiGatewayApplication    : No active profile set, falling back to 1 default profile: "default"
2024-02-08T08:53:18.790Z  INFO 1 --- [           main] .s.d.r.c.RepositoryConfigurationDelegate : Multiple Spring Data modules found, entering strict repository configuration mode
2024-02-08T08:53:18.793Z  INFO 1 --- [           main] .s.d.r.c.RepositoryConfigurationDelegate : Bootstrapping Spring Data Redis repositories in DEFAULT mode.
2024-02-08T08:53:18.828Z  INFO 1 --- [           main] .s.d.r.c.RepositoryConfigurationDelegate : Finished Spring Data repository scanning in 21 ms. Found 0 Redis repository interfaces.
2024-02-08T08:53:19.455Z  INFO 1 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat initialized with port 2000 (http)
2024-02-08T08:53:19.469Z  INFO 1 --- [           main] o.apache.catalina.core.StandardService   : Starting service [Tomcat]
2024-02-08T08:53:19.470Z  INFO 1 --- [           main] o.apache.catalina.core.StandardEngine    : Starting Servlet engine: [Apache Tomcat/10.1.17]
2024-02-08T08:53:19.522Z  INFO 1 --- [           main] o.a.c.c.C.[Tomcat].[localhost].[/]       : Initializing Spring embedded WebApplicationContext
2024-02-08T08:53:19.524Z  INFO 1 --- [           main] w.s.c.ServletWebServerApplicationContext : Root WebApplicationContext: initialization completed in 2032 ms
2024-02-08T08:53:20.317Z  WARN 1 --- [           main] .s.s.UserDetailsServiceAutoConfiguration : 

Using generated security password: e565ff8e-460a-4c5a-b6fb-e69621a291a9

This generated password is for development use only. Your security configuration must be updated before running your application in production.

2024-02-08T08:53:20.710Z  INFO 1 --- [           main] o.s.b.a.e.web.EndpointLinksResolver      : Exposing 1 endpoint(s) beneath base path '/actuator'
2024-02-08T08:53:21.379Z  INFO 1 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port 2000 (http) with context path ''
2024-02-08T08:53:21.401Z  INFO 1 --- [           main] k.c.n.api.NectarApiGatewayApplication    : Started NectarApiGatewayApplication in 4.69 seconds (process running for 5.172)
2024-02-08T08:54:29.432Z  INFO 1 --- [nio-2000-exec-1] o.a.c.c.C.[Tomcat].[localhost].[/]       : Initializing Spring DispatcherServlet 'dispatcherServlet'
2024-02-08T08:54:29.432Z  INFO 1 --- [nio-2000-exec-1] o.s.web.servlet.DispatcherServlet        : Initializing Servlet 'dispatcherServlet'
2024-02-08T08:54:29.434Z  INFO 1 --- [nio-2000-exec-1] o.s.web.servlet.DispatcherServlet        : Completed initialization in 1 ms


```

# Usage

While the `api-gateway` may be run independent of other NectarAPI micro-services, it is recommended that the nectar-deploy script be used to launch the tokens-service as part of NectarAPI. REST API access may then be available via the [api-gateway](https://github.com/NectarAPI/api-gateway).

# Contributing

Contributions are what make the open source community such an amazing place to be learn, inspire, and create. Any contributions are greatly appreciated.

If you have suggestions for adding or removing projects, feel free to open an issue to discuss it, or directly create a pull request after you edit the README.md file with necessary changes.

Please make sure you check your spelling and grammar.

Please create individual PRs for each suggestion.

# Contact
Please reach out to info@nectar.software or visit (www.nectar.software)[https://nectar.software] for more details on NectarAPI.


# Creating A Pull Request
To create a PR, please use the following steps:
1. Fork the Project
2. Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
3. Commit your Changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the Branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

# License 

Distributed under the  AGPL-3.0 License. See LICENSE for more information
