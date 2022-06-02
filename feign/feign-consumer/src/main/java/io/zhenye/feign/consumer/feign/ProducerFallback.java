package io.zhenye.feign.consumer.feign;

import feign.Request;
import org.springframework.stereotype.Component;

@Component
public class ProducerFallback implements ProducerFeign {

    @Override
    public String get(Integer id) {
        return "请求失败";
    }

    @Override
    public String timeout(Integer milliSeconds) {
        return "请求失败";
    }

    @Override
    public String timeout2(Integer milliSeconds, Request.Options options) {
        return "请求失败";
    }

}
