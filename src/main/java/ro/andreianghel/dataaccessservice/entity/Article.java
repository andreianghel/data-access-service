package ro.andreianghel.dataaccessservice.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;


/**
 * @author ananghel on 2/19/2019
 */
@Document(indexName = "blog2", type = "article2")
public class Article implements Serializable {

    @Id
    private String id;

    private String text;

    @Temporal(TemporalType.DATE)
    private Date creationDate;

    public Article() {
    }

    public Article(String text) {
        this.id = generateRandomStringBounded();
        this.text = text;
        this.creationDate = Calendar.getInstance().getTime();
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    private String generateRandomStringBounded() {

        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 25;
        Random random = new Random();
        StringBuilder buffer = new StringBuilder(targetStringLength);
        for (int i = 0; i < targetStringLength; i++) {
            int randomLimitedInt = leftLimit + (int)
                    (random.nextFloat() * (rightLimit - leftLimit + 1));
            buffer.append((char) randomLimitedInt);
        }
        String generatedString = buffer.toString();

        return generatedString;
    }

    @Override
    public String toString() {
        return "Article {" +
                " creationDate=" + creationDate +
                ", text='" + text + '\'' +
                '}';
    }
}
