package io.zhenye.demo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ValueComponent {

    @Value("${demo.annotation.name:test}")
    private String name;

}
