package com.hw.helloserver.controller;

import com.hw.service.common.math.MathClient;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
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

    private final MathClient mathClient;

    @Autowired
    public HelloController(MathClient mathClient) {
        this.mathClient = mathClient;
    }

    @GetMapping
    public String index() {
        return "Hello Key Config is: " + hello;
    }

    @GetMapping(value = "/{id}")
    @HystrixCommand(fallbackMethod = "objectNotFound")
    public String show(@PathVariable Integer id,
                       @RequestParam Integer[] numbers) {
        if (id > 5) {
            throw new RuntimeException("You id big than 2.");
        }

        Integer sum = mathClient.sum(numbers);
        List<String> result = new ArrayList<>();
        result.add(hello);
        result.add(sum.toString());

        Arrays.stream(numbers).forEach(
                (value) -> result.add(
                        Arrays.asList(
                                mathClient.sin(value),
                                mathClient.cos(value),
                                mathClient.tan(value),
                                mathClient.asin(value),
                                mathClient.acos(value),
                                mathClient.atan(value),
                                mathClient.exp(value),
                                mathClient.log(value),
                                mathClient.log10(value)
                        ).toString()
                )
        );
        return result.toString();
    }

    public String objectNotFound(Integer id, Integer[] numbers) {
        if (id > 2) {
            return String.format("The id %s is big than 2.", id);
        } else {
            return "The math server is not found.";
        }
    }
}
