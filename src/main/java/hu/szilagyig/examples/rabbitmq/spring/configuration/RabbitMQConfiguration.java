package hu.szilagyig.examples.rabbitmq.spring.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.AMQP;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitAdminEvent;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableRabbit
public class RabbitMQConfiguration {

    @Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter(ObjectMapper objectMapper) {
        return new Jackson2JsonMessageConverter(objectMapper);
    }

    @Bean
    public RabbitTemplate rabbitTemplate(
            ConnectionFactory connectionFactory,
            Jackson2JsonMessageConverter jackson2JsonMessageConverter) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jackson2JsonMessageConverter);

        rabbitTemplate.setMandatory(true);

        rabbitTemplate.setReturnsCallback(
                returnedMessage -> {
                    System.out.println(returnedMessage.getReplyCode());
                    System.out.println(returnedMessage.getReplyText());
                    if (AMQP.REPLY_SUCCESS == returnedMessage.getReplyCode() ) {
                        throw new IllegalArgumentException(returnedMessage.getReplyText());
                    }
                }
        );
/*
        rabbitTemplate.setRetryTemplate(

        );
*/
        return rabbitTemplate;
    }

    @Bean
    public RabbitAdmin asd(RabbitTemplate rabbitTemplate) {
        return new RabbitAdmin(rabbitTemplate);
    }
}
