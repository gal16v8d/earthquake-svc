# earthquake-svc

Forked from: https://javarev.blogspot.com/
Adding i18n support

### Pre-requisites

* Maven 3
* Java 21

### Docker Image

- In the project dir, build using the command:

```bash
docker build -t gsdd-earthquake-svc .
```

- Run the docker image as:

```bash
docker run --rm -d -p 8092:8092 gsdd-earthquake-svc
```

## check

Open in browser: 
- http://localhost:8092/earthquake-svc/index.gg