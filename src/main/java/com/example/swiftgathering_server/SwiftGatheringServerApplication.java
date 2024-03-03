package com.example.swiftgathering_server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class SwiftGatheringServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SwiftGatheringServerApplication.class, args);
    }

}
