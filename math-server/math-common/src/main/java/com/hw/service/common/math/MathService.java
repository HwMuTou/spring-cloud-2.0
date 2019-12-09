package com.hw.service.common.math;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping(value = "/math")
public interface MathService {

    @GetMapping("/sum")
    Integer sum(@RequestParam("numbers") Integer[] numbers);

    @GetMapping("/sin")
    double sin(@RequestParam("number") double number);

    @GetMapping("/cos")
    double cos(@RequestParam("number") double number);

    @GetMapping("/tan")
    double tan(@RequestParam("number") double number);

    @GetMapping("/asin")
    double asin(@RequestParam("number") double number);

    @GetMapping("/acos")
    double acos(@RequestParam("number") double number);

    @GetMapping("/atan")
    double atan(@RequestParam("number") double number);

    @GetMapping("/toRadians")
    double toRadians(@RequestParam("number") double number);

    @GetMapping("/toDegrees")
    double toDegrees(@RequestParam("number") double number);

    @GetMapping("/exp")
    double exp(@RequestParam("number") double number);

    @GetMapping("/log")
    double log(@RequestParam("number") double number);

    @GetMapping("/log10")
    double log10(@RequestParam("number") double number);

    @GetMapping("/sqrt")
    double sqrt(@RequestParam("number") double number);
}
