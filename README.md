### 打包
```shell
mvn install -Dquarkus.container-image.build=true
```
### 启动容器
```shell
docker create network quarkus-network
docker build -f src/main/docker/Dockerfile.jvm --network quarkus-network -p 8888:8888 -t quarkus-client .
docker build -f src/main/docker/Dockerfile.jvm --network quarkus-network -t quarkus-server .
```