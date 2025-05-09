package org.example.consumer.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class SpringRabbitListener {
    @Autowired
    private RabbitListenerEndpointRegistry rabbitListenerEndpointRegistry;
    Logger logger = LoggerFactory.getLogger(SpringRabbitListener.class);

    @RabbitListener(queuesToDeclare = @Queue("simple.queue"))
    public void receiveMessage1(String message) throws InterruptedException {
        logger.info("Consumer1 received message: {} - {}", message, LocalDateTime.now());
        Thread.sleep(20);
    }

    @RabbitListener(queuesToDeclare = @Queue("simple.queue"))
    public void receiveMessage2(String message) throws InterruptedException {
        logger.info("Consumer2 received message: {} - {}", message, LocalDateTime.now());
        Thread.sleep(200);
    }

    @RabbitListener(queues = "fanout.queue1")
    public void listenFanoutQueue1(String message){
        logger.info("Consumer received message from fanout queue1: {} - {}", message, LocalDateTime.now());
    }

    @RabbitListener(queues = "fanout.queue2")
    public void listenFanoutQueue2(String message){
        logger.info("Consumer received message from fanout queue2: {} - {}", message, LocalDateTime.now());
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "direct.queue1"),
            exchange = @Exchange(name = "direct.exchange", type= ExchangeTypes.DIRECT),
            key = {"key1", "key2"}
    ))
    public void listenDirectQueue1(String msg){
        logger.info("Consumer received message from direct queue1: {} - {}", msg, LocalDateTime.now());
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "direct.queue2"),
            exchange = @Exchange(name = "direct.exchange", type= ExchangeTypes.DIRECT),
            key = {"key1", "key3"}
    ))
    public void listenDirectQueue2(String msg){
        logger.info("Consumer received message from direct queue2: {} - {}", msg, LocalDateTime.now());
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "topic.queue1"),
            exchange = @Exchange(name="topic.exchange", type= ExchangeTypes.TOPIC),
            key = {"#.news"}
    ))
    public void listenTopicQueue1(String msg){
        logger.info("Consumer received message from topic queue1: {} - {}", msg, LocalDateTime.now());
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "topic.queue2"),
            exchange = @Exchange(name="topic.exchange", type= ExchangeTypes.TOPIC),
            key = {"china.#"}
    ))
    public void listenTopicQueue2(String msg){
        logger.info("Consumer received message from topic queue2: {} - {}", msg, LocalDateTime.now());
    }

    @RabbitListener(queuesToDeclare = @Queue("object.queue"))
    public void listenObjecQueue(String msg){
        logger.info("Consumer received message from object queue: {}", msg);
    }
}
