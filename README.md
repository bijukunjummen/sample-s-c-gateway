Start up using:

```
./gradlew bootRun
```

A sample set of Spring Cloud Gateway routes can be tested the following way:

### Simple route handling request to /get path

```
http http://localhost:8080/get
```

### Rewrite path from /foo/anything to /anything
```
http :8080/foo/anything Host:test.setpath.org
```
### Rewrite path from /foo/anything to /anything

```
http :8080/foo/anything Host:test.rewrite.org
```

### Add a request header
```
http :8080/anything Host:test.header.org
```
### Throttling - 1 request every 10 seconds

```
http :8080/anything Host:test.throttle.org
```