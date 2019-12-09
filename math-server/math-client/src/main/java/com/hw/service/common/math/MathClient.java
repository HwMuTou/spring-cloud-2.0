package com.hw.service.common.math;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "math-server")
public interface MathClient extends MathService {
}
