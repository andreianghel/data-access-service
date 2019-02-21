package ro.andreianghel.dataaccessservice.messaging.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ro.andreianghel.dataaccessservice.entity.Article;
import ro.andreianghel.dataaccessservice.service.ArticleService;


@Service
public class MessageReceiverService {

    //FIXME this class should consume message from a topic, call the appropiate method in the articleService
    // comunicate with the elasticsearch
    // and then call the producer to put a response in the topic


    public static final String TOPIC_ADD = "add_entry";
    public static final String TOPIC_GET_ALL = "get_all_entries";

    private KafkaTemplate<String, String> kafkaTemplate;

    private ArticleService articleService;

    private MessageSenderService messageSenderService;

    @Autowired
    public MessageReceiverService(KafkaTemplate<String, String> kafkaTemplate, ArticleService articleService, MessageSenderService messageSenderService) {
        this.kafkaTemplate = kafkaTemplate;
        this.articleService = articleService;
        this.messageSenderService = messageSenderService;
    }

    @KafkaListener(topics = TOPIC_ADD)
    public void consumeAddMessage(String message) {
        System.out.println(String.format("#### -> Consumed message -> %s", message));

        // persist this in the elasticsearch
        articleService.save(new Article(message));
    }

    @KafkaListener(topics = TOPIC_GET_ALL, groupId = "group_id")
    public void consumeGetAllMessage(String message) {
        System.out.println(String.format("#### -> Consumed message -> %s", message));

        // FIXME retrieve from elasticSearch
        Iterable<Article> retrievedEntries = articleService.findAllOrderByCreationDate();

        // call the producer/sender and send the message
        for (Article article : retrievedEntries) {
            messageSenderService.sendResponseForGetAllMessage(article.toString());
        }
    }
}

