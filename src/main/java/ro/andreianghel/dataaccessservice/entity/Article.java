package ro.andreianghel.dataaccessservice.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.util.Map;

/**
 * @author ananghel on 2/19/2019
 */
@Document(indexName = "blog1", type = "article1")
public class Article {

    @Id
    private int id;

    private String text;

    public Article(int id, String text) {
        this.id = id;
        this.text = text;
    }


    public Article(){}




    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
