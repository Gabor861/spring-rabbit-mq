package hu.szilagyig.examples.rabbitmq.examples;

import com.rabbitmq.client.AMQP;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate.ReturnsCallback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.expression.Expression;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

@SpringBootTest
@Slf4j
public class ExamplesTest {

    public static final String MESSAGE_IN_STRING = "One random message, UUID: " + UUID.randomUUID();

    @Autowired RabbitTemplate rabbitTemplate;

    @Test
    void sendMessageToDefaultExchangeByExitsRoutingKey() {
        Message message = MessageBuilder
                .withBody(MESSAGE_IN_STRING.getBytes())
                .setTimestamp(Date.from(Instant.now()))
                .setDeliveryMode(MessageDeliveryMode.PERSISTENT)
                .build();

        rabbitTemplate.send("", "default.queue", message);
    }

    @Test
    void sendMessageToNotExitsRoutingKeyCatchInReturnsCallback() {
        rabbitTemplate.send("example.direct", "ilyen.queue.nincs.definialva", MessageBuilder
                .withBody(MESSAGE_IN_STRING.getBytes())
                .build());
    }
}
