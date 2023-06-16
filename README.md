# codbex-iapetus

Iapetus Edition provides the Integrations modeling backed by Apache Camel, management and operation components.

It is good for exploration the Integration scenarios included the hundreds of available adapters.

#### Docker

```
docker pull ghcr.io/codbex/codbex-iapetus:latest
docker run --name codbex-iapetus --rm -p 8080:8080 ghcr.io/codbex/codbex-iapetus:latest
```

#### Build

```
mvn clean install
```
	
#### Run

```
java -jar application/target/codbex-iapetus-application-1.0.0-SNAPSHOT.jar
```

#### Debug

```
java -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=8000 -jar application/target/codbex-iapetus-application-1.0.0-SNAPSHOT.jar
```
	
#### Web

```
http://localhost:8080
```

#### REST API

```
http://localhost:8080/swagger-ui/index.html
```
