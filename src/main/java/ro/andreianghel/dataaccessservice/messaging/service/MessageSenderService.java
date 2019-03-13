package ro.andreianghel.dataaccessservice.messaging.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

/**
 * @author AnAnghel on 2/20/2019
 */
@Service
public class MessageSenderService {

    public static final String TOPIC_ADD_RESPONSE = "response_add_entry";
    public static final String TOPIC_GET_ALL_RESPONSE = "response_get_all_entries";

    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    public MessageSenderService(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendResponseForAddMessage(String message) {
        sendMessage(TOPIC_ADD_RESPONSE, message);
    }

    public void sendResponseForGetAllMessage(String message) {

        // FIXME this should send a list of all the entries at first
        // aggregate somehow all messages and send them in one message
        sendMessage(TOPIC_GET_ALL_RESPONSE, message);


    }

    public void sendMessage(String topic, String message) {
        // System.out.println(String.format("#### -> Producing message -> %s", message));
        this.kafkaTemplate.send(topic, message);

        // flush data
        this.kafkaTemplate.flush();
    }

}
