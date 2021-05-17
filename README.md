### Start application

```sh
./gradlew bootRun
```

A sample set of Spring Cloud Gateway routes can be tested the following way:

### Simple route handling request to /get path

```sh
http http://localhost:8080/get
```

### Add a request header

```sh
http :8080/anything Host:test.header.org
```


### Rewrite path from /foo/anything to /anything
```sh
http :8080/foo/anything Host:test.setpath.org
```

### Rewrite path from /foo/anything to /anything

```sh
http :8080/foo/anything Host:test.rewrite.org
```

### Throttling - 1 request every 10 seconds

```sh
http :8080/anything Host:test.throttle.org
```
