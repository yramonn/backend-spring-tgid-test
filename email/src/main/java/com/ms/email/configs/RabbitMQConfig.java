package com.ms.email.configs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ms.email.dtos.EmailDto;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Value("${broker.queue.email.name}")
    private String queue;

    @Bean
    public Queue queue() {
        return new Queue(queue, true);
    }

    @Bean
    public Jackson2JsonMessageConverter customMessageConverter(ObjectMapper objectMapper) {
        return new Jackson2JsonMessageConverter(objectMapper) {
            @Override
            public Object fromMessage(Message message, Object conversionHint) {

                byte[] body = message.getBody();

                try {
                    String json = new String(body);
                    return objectMapper.readValue(json, EmailDto.class);
                } catch (Exception e) {
                    throw new RuntimeException("Erro ao converter a mensagem para EmailDto", e);
                }
            }
        };
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

}

