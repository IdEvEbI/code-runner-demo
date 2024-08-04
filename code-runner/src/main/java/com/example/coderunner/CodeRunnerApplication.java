package com.example.coderunner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class CodeRunnerApplication {

    public static void main(String[] args) {
        SpringApplication.run(CodeRunnerApplication.class, args);
    }

    /**
     * 配置 RestTemplate Bean，用于发送 HTTP 请求
     *
     * @return RestTemplate
     */
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
