# Cloud Config

| Feature| Test url | Show Value
|--------|----------|
| Show Cloud Config Value | http://localhost:9080/hello-server/hello/ | Hello Key Config is: This Cloud Config Value of hello key.

## CloudConfig server

Cloud Config server 给其他服务提供高可用性的配置服务，其他服务可以从Config server加载他们的配置文件。
Config Server基于Git管理配置文件的版本，在服务发布上有着一定优势。

  例如，配置文件改动之后，提交到Git服务，然后重启服务之后，Config-Server会通过Git同步Git服务的文件内容。
重启将会加载到最新的配置内容。

使用时需要导入 spring-cloud-config-server to pom.xml

```xml
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-config-server</artifactId>
        </dependency>
```

需要使用Java Config @EnableConfigServer 像下面这样,

```Java
    @SpringBootApplication
    @EnableConfigServer
    public class ConfigServerApplication {
    
        public static void main(String[] args) {
            SpringApplication.run(ConfigServerApplication.class, args);
        }
    }
```

## The Client

其他客户端服务需要创建配置文件bootstrap.yml, 配置配置服务器的地址 bootstrap.yml.

Example:

```yml
spring:
  cloud:
    config:
      uri: http://localhost:9096
```

此时可以使用类似 @Value("${name}") 注入Cloud-config-server提供的配置内容

Example:

```Java
    public class Controller {
        @Value("${hello}")
        String hello;
    }
```

@Value("${hello}") 注入配置文件中hello的配置值，但是clien的配置文件并不包含 hello配置，此配置来自Cloud Config Server提供的内容