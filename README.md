# earthquake-svc

Forked from: https://javarev.blogspot.com/

Adding i18n support

# docker

## build
docker build -t earthquake-svc .
## launch
docker run --rm -d -p 8092:8092 earthquake-svc
## check
Open in browser: http://localhost:8092/earthquake-svc/index.gg