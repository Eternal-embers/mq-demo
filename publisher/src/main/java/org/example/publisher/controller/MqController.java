package org.example.publisher.controller;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class MqController {
    @Autowired
    RabbitTemplate rabbitTemplate;

    @GetMapping("/")
    public String index(){
        return "<h1>Welcome to RabbitMQ Publisher</h1>";
    }

    @GetMapping("/send")
    public String sendMessage(){
        String queueName = "simple.queue";
        String message = "Hello World!";
        rabbitTemplate.convertAndSend(queueName, message);

        return "<h1>send message success</h1>";
    }

    @GetMapping("/send2")
    public String sendMessageByWorkQueue() throws InterruptedException {
        String queueName = "simple.queue";
        String message = "hello, message - ";

        for(int i = 0;i <= 50;i++){
            rabbitTemplate.convertAndSend(queueName, message + i);
            Thread.sleep(20);
        }

        return "<h1>Send 50 messages by work queue success.</h1>";
    }

    @GetMapping("/send3")
    public String sendFanoutExchange(){
        //交换机名称
        String exchangeName = "fanout.exchange";

        //消息
        String message = "Hello, the message from fanout exchange";

        rabbitTemplate.convertAndSend(exchangeName, "", message);

        return "<h1>send message to fanout exchange success</h1>";
    }

    @GetMapping("/send4")
    public String sendDirectExchange(){
        //交换机名称
        String exchangeName = "direct.exchange";

        //消息
        String message = "Hello, the message from direct exchange";

        rabbitTemplate.convertAndSend("direct.exchange", "key1", message + ", routingkey: key1");
        rabbitTemplate.convertAndSend("direct.exchange", "key2", message + ", routingkey: key2");
        rabbitTemplate.convertAndSend("direct.exchange", "key3", message + ", routingkey: key3");

        return "<h1>send message to direct exchange success</h1>";
    }

    @GetMapping("/send5")
    public String sendTopicExchange(){
        //交换机名称
        String exchangename = "topic.exchange";

        //消息
        String message = "Hello, the message from topic exchange";

        rabbitTemplate.convertAndSend("topic.exchange", "china.news", message + ", routingkey: china.news");
        rabbitTemplate.convertAndSend("topic.exchange", "china.sports", message + ", routingkey: china.sports");
        rabbitTemplate.convertAndSend("topic.exchange", "finance.news", message + ", routingkey: finance.news");

        return "<h1>send message to topic exchange success</h1>";
    }

    @GetMapping("/send6")
    public String sendObjectQueue(){
        String queueName = "object.queue";
        Map<String, Object> map = new HashMap<>();
        map.put("name", "Jack");
        map.put("age", 18);
        rabbitTemplate.convertAndSend(queueName, map);

        return "<h1>send object message success</h1>";
    }
}
