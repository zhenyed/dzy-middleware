package io.zhenye.feign.consumer.controller;

import feign.Request;
import io.zhenye.feign.consumer.feign.ProducerFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RestController
public class Controller {

    @Autowired
    private ProducerFeign producerFeign;

    @GetMapping("/user/{id}")
    public String get(@PathVariable("id") Integer id) {
        return producerFeign.get(id);
    }

    @GetMapping("/timeout/{milliSeconds}")
    public String timeout(@PathVariable("milliSeconds") Integer milliSeconds) {
        return producerFeign.timeout(milliSeconds);
    }

    @GetMapping("/timeout2/{milliSeconds}")
    public String timeout2(@PathVariable("milliSeconds") Integer milliSeconds) {
        // 指定 feign url 超时时间
        Request.Options options = new Request.Options(1000, TimeUnit.MILLISECONDS, 750, TimeUnit.MILLISECONDS, false);
        return producerFeign.timeout2(milliSeconds, options);
    }

}
