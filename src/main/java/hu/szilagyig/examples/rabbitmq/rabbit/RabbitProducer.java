package hu.szilagyig.examples.rabbitmq.rabbit;

import lombok.AllArgsConstructor;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

@AllArgsConstructor
public class RabbitProducer {

    private RabbitTemplate rabbitTemplate;

    public void sendMessage(String message) {
        rabbitTemplate.send(
                MessageBuilder
                        .withBody(message.getBytes())
                        .setExpiration("1000")
                        .setDeliveryMode(MessageDeliveryMode.PERSISTENT)
                        .build()
        );
    }
}
