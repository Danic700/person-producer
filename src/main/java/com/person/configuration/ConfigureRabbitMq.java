package com.person.configuration;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigureRabbitMq {


    @Bean
    DirectExchange deadLetterExchange() {
        return new DirectExchange("deadLetterExchange");
    }

    @Bean
    DirectExchange exchange() {
        return new DirectExchange("personExchange");
    }

    @Bean
    Queue dlq() {
        return QueueBuilder.durable("deadLetter.queue").build();
    }

    @Bean
    Queue queue() {
        return QueueBuilder.durable("person.queue")
                .withArgument("x-dead-letter-exchange", "deadLetterExchange")
                .withArgument("x-dead-letter-routing-key", "deadLetter").build();
    }

    @Bean
    Binding DLQbinding() {
        return BindingBuilder.bind(dlq())
                .to(deadLetterExchange()).with("deadLetter");
    }

    @Bean
    Binding binding() {
        return BindingBuilder.bind(queue())
                .to(exchange()).with("person");
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

}