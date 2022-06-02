package io.zhenye.feign.producer.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RestController
public class Controller {

    @GetMapping("/user/{id}")
    public String get(@PathVariable("id") Integer id) {
        return "success";
    }

    @GetMapping("/timeout/{milliSeconds}")
    public String timeout(@PathVariable("milliSeconds") Integer milliSeconds) throws InterruptedException {
        TimeUnit.MILLISECONDS.sleep(milliSeconds);
        return "success";
    }

}
