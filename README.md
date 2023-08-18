# codbex-iapetus

Iapetus Edition provides the Integrations modeling backed by Apache Camel, management and operation components.

It is good for exploration the Integration scenarios included the hundreds of available adapters.

#### Docker

```
docker pull ghcr.io/codbex/codbex-iapetus:latest
docker run --name codbex-iapetus --rm -p 80:80 ghcr.io/codbex/codbex-iapetus:latest
```

#### Build

```
mvn clean install
```
	
#### Run

```
java -jar application/target/codbex-iapetus-application-*.jar
```

#### Debug

```
java -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=8000 -jar application/target/codbex-iapetus-application-*.jar
```
	
#### Web

```
http://localhost
```

#### REST API

```
http://localhost/swagger-ui/index.html
```
