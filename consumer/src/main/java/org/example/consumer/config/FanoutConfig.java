package org.example.consumer.config;

import com.rabbitmq.client.impl.AMQImpl;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FanoutConfig {
    //声明FanoutExchange交换机
    @Bean
    public FanoutExchange FanoutExchange(){
        return new FanoutExchange("fanout.exchange");
    }

    //声明第一个队列
    @Bean
    public Queue fanoutQueue1(){
        return new Queue("fanout.queue1");
    }

    //绑定交换机1到交换机
    @Bean
    public Binding fanoutBanding1(Queue fanoutQueue1, FanoutExchange fanoutExchange){
        return BindingBuilder.bind(fanoutQueue1).to(fanoutExchange);
    }

    //声明第二个队列
    @Bean
    public Queue fanoutQueue2(){
        return new Queue("fanout.queue2");
    }

    //绑定交换机2到交换机
    @Bean
    public Binding fanoutBanding2(Queue fanoutQueue2, FanoutExchange fanoutExchange){
        return BindingBuilder.bind(fanoutQueue2).to(fanoutExchange);
    }
}
