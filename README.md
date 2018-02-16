Vowpal Wabbit smoke test
========================

To build a vw environment in docker copy and build the Dockerfile.

```docker build . -t vw```

```docker run -it --rm vw bash```

```vw --help```

```cd /tmp/work/smoke-test```

```mvn clean install exec:java```

No need to clone the repo.

Note that the build will take a few minutes.
