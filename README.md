# Docker 部署 RabbitMQ Demo

## 部署目录结构

```text
mq-demo
├── docker-compose.yml
├── consumer
│   └── consumer.jar
│   └── Dockerfile
└── publisher
    └── Dockerfile
    └── publisher.jar
```

## docker 部署文件

`consumer/Dockerfile`

```bash
# 使用基础镜像
FROM azul/zulu-openjdk-alpine:21

# 设置工作目录
WORKDIR /app

# 将app.jar 复制到工作目录
copy consumer.jar .

# 指定容器启动时运行的命令
CMD ["java", "-jar", "consumer.jar"]
```

`publisher/Dockerfile`

```bash
# 使用基础镜像
FROM azul/zulu-openjdk-alpine:21

# 设置工作目录
WORKDIR /app

# 将app.jar 复制到工作目录
copy publisher.jar .

# 指定容器启动时运行的命令
CMD ["java", "-jar", "publisher.jar"]
```

`docker-compose.yml`

```yaml
services:
  consumer:
    build: ./consumer
    ports:
      - "8082:8082"
    depends_on:
      - rabbitmq

  publisher:
    build: ./publisher
    ports:
      - "8081:8081"
    depends_on:
      - rabbitmq

  rabbitmq:
    image: rabbitmq:3.12-management
    container_name: rabbitmq-server
    ports:
      - "5672:5672"  # RabbitMQ 服务端口
      - "15672:15672"  # 管理界面端口
    environment:
      RABBITMQ_DEFAULT_USER: "admin" # 用户
      RABBITMQ_DEFAULT_PASS: "admin" # 密码
    volumes:
      - /usr/local/docker/rabbitmq:/var/lib/rabbitmq
```

## 启动项目

```bash
docker compose up -d
```

在 `localhost:15672` 登录 RabbitMQ 进行测试学习。