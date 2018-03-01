Vowpal Wabbit smoke test
========================

To build a vw environment in docker copy and build the Dockerfile.

```docker build . -t vw```

```docker run -it --rm smoke-test:1.0-SNAPSHOT java -jar target/smoke-test*.jardocker run -it --rm smoke-test:1.0-SNAPSHOT bash -c "java -jar target/smoke-test*.jar"```

or

```docker run -it --rm vw bash```

```vw --help```

```cd /tmp/work/smoke-test```

```mvn clean install```

```java -jar target/smoke-test*.jar```

No need to clone the repo.

Note that the build will take a few minutes.
