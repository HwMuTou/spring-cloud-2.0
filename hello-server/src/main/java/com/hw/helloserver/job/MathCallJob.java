package com.hw.helloserver.job;

import com.hw.service.common.math.MathClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.stream.Stream;

@Component
public class MathCallJob {

    private static final Logger log = LoggerFactory.getLogger(MathCallJob.class);

    private final MathClient mathClient;

    public MathCallJob(MathClient mathClient) {
        this.mathClient = mathClient;
    }

    @Scheduled(cron = "0/15 * * * * *")
    public void reportCurrentTime() {
        Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
                .forEach(it -> log.info(
                        Arrays.asList(
                                mathClient.sin(it),
                                mathClient.cos(it),
                                mathClient.tan(it),
                                mathClient.asin(it),
                                mathClient.acos(it),
                                mathClient.atan(it),
                                mathClient.exp(it),
                                mathClient.log(it),
                                mathClient.log10(it)).toString()
                        )
                );
    }
}
