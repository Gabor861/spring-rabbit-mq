package hu.szilagyig.examples.rabbitmq.spring.configuration;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;

@Configuration
public class RabbitMQDefinitionConfiguration {

    @Bean
    public Queue exampleDirectQueue2() {
        return new Queue("example.direct.queue3", true, false, false, Map.of());
    }

    @Bean
    public Exchange exampleDirectExchange() {
        return new DirectExchange("example.direct", true, false, Map.of());
    }

    @Bean
    public Binding binding(
            @Qualifier("exampleDirectQueue2") Queue queue,
            @Qualifier("exampleDirectExchange") Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("t1").noargs();
    }


    @Bean
    public Queue exampleAlternateQueue() {
        return new Queue("example.alternate.queue", true, false, false, Map.of());
    }

    @Bean
    public Exchange exampleFanoutAlternateExchange() {
        return new FanoutExchange("example.alternate.fanout", true, false, Map.of());
    }

    @Bean
    public Binding bindingAlternate(
            @Qualifier("exampleAlternateQueue") Queue queue,
            @Qualifier("exampleFanoutAlternateExchange") Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("").noargs();
    }


    @Bean
    public Exchange exampleExchange() {
        return new DirectExchange(
                "example.alternated.direct",
                true,
                false,
                Map.of(

                )
        );
    }

    @Bean
    public Declarables declarablesDeclaring(List<Declarable> declarableList) {
        return new Declarables(declarableList);

    }
}
