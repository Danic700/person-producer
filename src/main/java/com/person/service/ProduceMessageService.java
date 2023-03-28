package com.person.service;

import com.person.model.Person;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProduceMessageService {
    private static final Logger logger = LoggerFactory.getLogger(ProduceMessageService.class);
    private final RabbitTemplate rabbitTemplate;
    @Value("${routingKey}")
    private String routingKey;
    @Value("${exchange}")
    private String exchange;

    public String produceMessage(Person person) {
        try {
            logger.info("Sending message to person queue ");
            rabbitTemplate.convertAndSend(exchange, routingKey, person);
            logger.info("Message sent successfully to person queue ");
            return "Person(" + person.getName() + ")" + " has been sent to person queue.";
        } catch (AmqpException e) {
            logger.info("Handling exception from person queue ");
            throw e;
        }
    }
}
