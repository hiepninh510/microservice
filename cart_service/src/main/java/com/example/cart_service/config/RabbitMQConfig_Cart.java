package com.example.cart_service.config;

import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


@Configuration
@ComponentScan
public class RabbitMQConfig_Cart {
    
    @Bean
    public Queue cart_Queue_(){
        return new Queue("cart_queue");
    }

    @Bean
    public DirectExchange cart_Exchange(){
        return new DirectExchange("cart-exchange");
    }

    @Bean
    public org.springframework.amqp.core.Binding binding(){
        return BindingBuilder.bind(cart_Queue_()).to(cart_Exchange()).with("cart-routing-key");

    }
}
