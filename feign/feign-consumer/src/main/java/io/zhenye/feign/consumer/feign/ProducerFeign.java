package io.zhenye.feign.consumer.feign;

import feign.Request;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "feign-producer", fallback = ProducerFallback.class)
public interface ProducerFeign {

    @GetMapping("/user/{id}")
    String get(@PathVariable("id") Integer id);

    @GetMapping("/timeout/{milliSeconds}")
    String timeout(@PathVariable("milliSeconds") Integer milliSeconds);

    @GetMapping("/timeout/{milliSeconds}")
    String timeout2(@PathVariable("milliSeconds") Integer milliSeconds, Request.Options options);

}
