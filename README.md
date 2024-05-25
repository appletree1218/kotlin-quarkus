### 打包
```shell
mvn install -Dquarkus.container-image.build=true
```
### 构建镜像
```shell
docker build -f src/main/docker/Dockerfile.jvm -t quarkus-client .
docker build -f src/main/docker/Dockerfile.jvm -t quarkus-server .
```
### 启动容器
```shell
docker create network quarkus-network
docker run -d --name quarkus-client -p 8888:8888 --network quarkus-network quarkus-client
docker run -d --name quarkus-client --network quarkus-network quarkus-client
```