# spring cloud Gateway eureka example
 
 
| Feature| Test url |
|--------|----------|
|Service auto registration via Eureka|http://localhost:9080/hello-server OR http://localhost:9080/math-server|
|Hystrix response | http://localhost:9080/hello-server/hello |
|Hystrix response | http://localhost:9080/hello-server/hello/1?numbers=1,2,3 OR http://localhost:9080/math-server/math/sum?numbers=1,2,3 |
|Hystrix response | http://localhost:9080/hello-server/hello/3?numbers=1,2,3 |
|Eureka Console UI | http://localhost:8761/ |

## Gateway with Eureka Server Framework

spring cloud gateway 就是一个可以定制的代理服务软件

## Hystrix

程序设计增强,如果服务请求失败,启用备用方案,增强容错能力。使用方法

```Java
    @SpringBootApplication
    @EnableEurekaClient
    @EnableDiscoveryClient
    @EnableFeignClients
    @EnableHystrix
    public class LoveServerApplication {
    
        public static void main(String[] args) {
            SpringApplication.run(LoveServerApplication.class, args);
        }
    }
```

config @EnableHystrix ,spring boot Enable Hystrix, and the service code.
    
```Java
package com.demo.controller;

import com.demo.service.math.MathService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by RainBox on 2017/7/8.
 * .
 */
@RestController
@RequestMapping(value = "/hello")
public class HelloController {

    @Value("${hello}")
    String hello;

    private MathService mathService;

    @Autowired
    public HelloController(MathService mathService) {
        this.mathService = mathService;
    }

    @GetMapping
    public ResponseEntity index() {
        return ResponseEntity.ok("Hello Key Config is: " + hello);
    }

    @GetMapping(value = "/{id}")
    @HystrixCommand(fallbackMethod = "objectNotFound", commandKey = "HelloController.show")
    public ResponseEntity show(@PathVariable Integer id,
                               @RequestParam List<Integer> numbers) {
        if (id > 2) {
            throw new RuntimeException("You id big than 2.");
        }

        Integer sum = mathService.sum(numbers);
        return ResponseEntity.ok("Hello Key Config is: " + hello + ", The sum = " + sum);
    }

    public ResponseEntity objectNotFound(Integer id, List<Integer> numbers) {
        if (id > 2) {
            return ResponseEntity.ok(String.format("The id %s is big than 2.", id));
        } else {
            return ResponseEntity.ok("The math server is not found.");
        }
    }
}
```

核心点在于

    @HystrixCommand(fallbackMethod = "objectNotFound", commandKey = "HelloController.show")
    public ResponseEntity show(@PathVariable Integer id,
                               @RequestParam int[] numbers) {......

    和方法
    
    public ResponseEntity objectNotFound(Integer id, int[] numbers) {
        return ResponseEntity.ok("The math server is not found. or the id > 2 of id = " + id);
    }

值得注意: 两个方法的参数定义需要保持一致。