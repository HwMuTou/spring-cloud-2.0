package com.hw.mathserver.service.math;

import com.hw.service.common.math.MathService;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Stream;

@RestController
public class MathServiceImpl implements MathService {

    @Override
    public Integer sum(Integer[] numbers) {
        return Stream.of(numbers).mapToInt(Integer::intValue).sum();
    }

    @Override
    public double sin(double number) {
        return Math.sin(number);
    }

    @Override
    public double cos(double number) {
        return Math.cos(number);
    }

    @Override
    public double tan(double number) {
        return Math.tan(number);
    }

    @Override
    public double asin(double number) {
        return Math.asin(number);
    }

    @Override
    public double acos(double number) {
        return Math.acos(number);
    }

    @Override
    public double atan(double number) {
        return Math.atan(number);
    }

    @Override
    public double toRadians(double number) {
        return Math.toRadians(number);
    }

    @Override
    public double toDegrees(double number) {
        return Math.toDegrees(number);
    }

    @Override
    public double exp(double number) {
        return Math.exp(number);
    }

    @Override
    public double log(double number) {
        return Math.log(number);
    }

    @Override
    public double log10(double number) {
        return Math.log10(number);
    }

    @Override
    public double sqrt(double number) {
        return Math.sqrt(number);
    }
}
