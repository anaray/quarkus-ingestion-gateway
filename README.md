# ingestion-gateway project


## Packaging and running the application

The application can be packaged using:
```shell script
./gradlew build
```
It produces the `quarkus-run.jar` file in the `build/quarkus-app/` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `build/quarkus-app/lib/` directory.


The application is now runnable using `java -jar build/quarkus-app/quarkus-run.jar`.

## Load testing
I used https://github.com/nakabonne/ali to generate load.

Install ali in mac: 
```
brew install nakabonne/ali/ali
```

## Run the load test
```
ali --rate=500 --duration=5m --body-file=quarkus-ingestion-gateway/load-test-data/400k --method=POST http://localhost:8080/events
```
I have committed a 400k file in load-test-data

## Steps to recreate issue

* checkout https://github.com/anaray/quarkus-ingestion-gateway.git (Quarkus app)
* ./gradlew clean build -x test
* java -jar build/quarkus-app/quarkus-run.jar 
* checkout https://github.com/anaray/test-gateway-service.git (downstream test app)
* ./gradlew clean build -x test
* java -jar build/libs/test-gateway-0.0.1-SNAPSHOT.jar
* To generate load - ```ali --rate=500 --duration=5m --body-file=quarkus-ingestion-gateway/load-test-data/400k --method=POST http://localhost:8080/events```
* At some point the quarkus-ingestion-gateway would throw
  ```
  java.lang.IllegalStateException: SRMSG00034: Insufficient downstream requests to emit item
  	at io.smallrye.reactive.messaging.extension.ThrowingEmitter.emit(ThrowingEmitter.java:60)
  	at io.smallrye.reactive.messaging.extension.AbstractEmitter.emit(AbstractEmitter.java:146)
  	at io.smallrye.reactive.messaging.extension.EmitterImpl.send(EmitterImpl.java:29)
  ```
 
  
 



