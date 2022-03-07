package io.zhenye;

import io.zhenye.service.Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Slf4j
@RestController
public class Controller {

    @Resource
    private Service service;

    @GetMapping("/test")
    public String test(String param) {
        service.get();
        log.info(param);
        return param;
    }

}
