package io.zhenye;

import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableApolloConfig
@SpringBootApplication
public class ApolloClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApolloClientApplication.class, args);
    }

}
