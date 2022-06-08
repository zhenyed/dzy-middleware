package io.zhenye.feign.producer.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
public class Controller {

    @GetMapping("/user/{id}")
    public String get(@PathVariable("id") Integer id) {
        log.info("dispose id[{}]", id);
        return "success";
    }

    @GetMapping("/timeout/{milliSeconds}")
    public String timeout(@PathVariable("milliSeconds") Integer milliSeconds) throws InterruptedException {
        log.info("dispose timeout[{}ms]", milliSeconds);
        TimeUnit.MILLISECONDS.sleep(milliSeconds);
        return "success";
    }

}
