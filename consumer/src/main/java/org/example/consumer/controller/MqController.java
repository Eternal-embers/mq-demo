package org.example.consumer.controller;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.MessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
public class MqController {
    @Autowired
    RabbitTemplate rabbitTemplate;

    @GetMapping("/")
    public String index(){
        return "<h1>Welcome to RabbitMQ Consumer</h1>";
    }

    @GetMapping("/receive")
    public String receiveMessage() {
        // 从队列中接收消息
        String message = (String) rabbitTemplate.receiveAndConvert("simple.queue");
        if (message != null) {
            return "<h1>Received message: " + message + "</h1>";
        } else {
            return "<h1>No message available in the queue.</h1>";
        }
    }

    @GetMapping("/receive2")
    public String receiveMessage2() {
        StringBuilder sb = new StringBuilder();
        sb.append("<h1>Received messages: </h1>");
        while (true) {
            Object message = rabbitTemplate.receiveAndConvert("simple.queue");
            if (message == null) {
                break;
            }
            sb.append("<p>").append(message).append("</p>");
        }
        return sb.toString();
    }
}
