# Feign demo

| Feature| Test url |
|--------|----------|
|Feign | http://localhost:9080/hello-server/hello/1?numbers=1,2,3 |
|Feign | http://localhost:9080/hello-server/ |

## Code Example

The Controller 

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
    @HystrixCommand(fallbackMethod = "objectNotFound")
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

注入用的Service接口实现如下：

```Java
package com.demo.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = "math-server", path = "/math")
public interface MathService {

    @GetMapping("/sum")
    Integer sum(@RequestParam("numbers") List<Integer> numbers);
}

```

值得注意的是， @RequestParam  @GetMapping 尽量写全, 否则Feign框架的识别容易出错。