package io.zhenye.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ServiceA implements Service {

    @Override
    public void get() {
        log.info("B");
    }

}
