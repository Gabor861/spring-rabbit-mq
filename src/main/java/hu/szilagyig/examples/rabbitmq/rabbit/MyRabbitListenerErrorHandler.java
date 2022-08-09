package hu.szilagyig.examples.rabbitmq.rabbit;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.listener.api.RabbitListenerErrorHandler;
import org.springframework.amqp.rabbit.support.ListenerExecutionFailedException;

@Slf4j
public class MyRabbitListenerErrorHandler implements RabbitListenerErrorHandler {

    @Override public Object handleError(
            Message message, org.springframework.messaging.Message<?> message1, ListenerExecutionFailedException e) {

        log.info("Error in message processing procedure! Message: {}", message);

        return null;
    }
}
