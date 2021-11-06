# Jakarta EE Laboratory

Serverlet with context and dependency injections.

## Description


PASA app provides a simple purchase service that enables selling and buying thousand of items in several categories.
## Build and run

**Firstly**, clean your repository

```bash
mvn clean 
```

**Secondly**, pull all dependency for open liberty

```bash
mvn -P liberty package
```


**Thirdly**, to run app using Open Liberty Application put:

```bash
mvn -P liberty liberty:dev
```



## Author

Leśniewski Paweł - *xvc_studios &copy;* 2021
