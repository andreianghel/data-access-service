package ro.andreianghel.dataaccessservice.messaging.service;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ro.andreianghel.dataaccessservice.entity.Article;
import ro.andreianghel.dataaccessservice.service.ArticleService;

import java.util.List;


@Service
public class MessageReceiverService {

    // FIXME this class should consume message from a topic, call the appropiate method in the articleService
    // comunicate with the elasticsearch
    // and then call the producer to put a response in the topic


    public static final String TOPIC_ADD = "add_entry";
    public static final String TOPIC_GET_ALL = "get_all_entries";


    private KafkaTemplate<String, String> kafkaTemplate;

    private ArticleService articleService;

    private ElasticsearchTemplate elasticsearchTemplate;

    private MessageSenderService messageSenderService;

    private Gson gson;

    @Autowired
    public MessageReceiverService(KafkaTemplate<String, String> kafkaTemplate,
                                  ArticleService articleService,
                                  ElasticsearchTemplate elasticsearchTemplate,
                                  MessageSenderService messageSenderService,
                                  Gson gson) {
        this.kafkaTemplate = kafkaTemplate;
        this.articleService = articleService;
        this.elasticsearchTemplate = elasticsearchTemplate;
        this.messageSenderService = messageSenderService;
        this.gson = gson;
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
        List<Article> allEntries = articleService.getAllOrdered(elasticsearchTemplate);

        // create a message with all this entries;
        String allEntriesAsJson = gson.toJson(allEntries);

        // send it
        messageSenderService.sendResponseForGetAllMessage(allEntriesAsJson);
    }
}

