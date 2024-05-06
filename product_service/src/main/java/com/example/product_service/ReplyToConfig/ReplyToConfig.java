package com.example.product_service.ReplyToConfig;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ReplyToConfig {
    
    @Bean
    public Queue reply_To(){
        return new Queue("reply-to");
    }
}
