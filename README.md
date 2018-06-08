```
./gradlew shadowJar
```

```
java -jar build/libs/todobackend-mongo-1.0-SNAPSHOT-all.jar
```

`GET http://localhost:9090/todos` 

-> 

```Error: Could not find MessageBodyWriter for response object of type: io.reactivex.internal.operators.single.SingleJust of media type: text/html;charset=UTF-8```
