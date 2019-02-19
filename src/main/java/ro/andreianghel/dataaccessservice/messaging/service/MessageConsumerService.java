package ro.andreianghel.dataaccessservice.messaging.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;


@Service
public class MessageConsumerService {

    public static final String TOPIC_ADD = "add_entry";
    public static final String TOPIC_GET_ALL = "get_all_entries";

    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    public MessageConsumerService(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }


    @KafkaListener(topics = TOPIC_ADD)
    public void consumeAddMessage(String message) {
        System.out.println(String.format("#### -> Consumed message -> %s", message));
        //FIXME persist this in the elasticsearch
    }

    @KafkaListener(topics = TOPIC_GET_ALL, groupId = "group_id")
    public void consumeGetAllMessage(String message) {
        System.out.println(String.format("#### -> Consumed message -> %s", message));
        //FIXME retrieve form elastic search and put a new message on a send topic
    }


}

