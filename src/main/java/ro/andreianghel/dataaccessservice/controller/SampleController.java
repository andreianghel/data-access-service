package ro.andreianghel.dataaccessservice.controller;

import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.Operator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ro.andreianghel.dataaccessservice.entity.Article;
import ro.andreianghel.dataaccessservice.service.ArticleService;

import java.util.ArrayList;
import java.util.List;

import static org.elasticsearch.index.query.QueryBuilders.matchQuery;

/**
 * @author ananghel on 2/19/2019
 */
@RestController
public class SampleController {


    private ArticleService articleService;

    private ElasticsearchTemplate elasticsearchTemplate;

    private int counter;

    @Autowired
    public SampleController(ArticleService articleService, ElasticsearchTemplate elasticsearchTemplate) {
        this.articleService = articleService;
        this.elasticsearchTemplate = elasticsearchTemplate;
        counter = 0;
    }

    @GetMapping("add")
    void add() {
        String text = "";

        if (counter % 3 == 0) {
            text += "hihi";
        }
        if (counter % 3 == 1) {
            text += "haha";
        }

        if (counter % 3 == 2) {
            text += "noop";
        }
        Article auxArticle = new Article(counter, "text: " + text);
        counter++;

        articleService.save(auxArticle);
    }

    @GetMapping("get/{id}")
    String get(@PathVariable int id) {
        Article getOne = articleService.findById(id).get();

        return getOne.getId() + " : " + getOne.getText();
    }

    @GetMapping("/getAll")
    List<String> getAll() {
        List<String> retList = new ArrayList<>();
        articleService.findAll().forEach(art -> {
            retList.add(art.getId() + " : " + art.getText());
        });

        return retList;
    }

    @GetMapping("deleteAll")
    void deleteAll() {
        articleService.deleteAll();
    }

    @GetMapping("fuzzy/{word}")
    List<Article> fuzzySearch(@PathVariable String word) {

        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(matchQuery("text", word)
                        .operator(Operator.AND)
                        .fuzziness(Fuzziness.TWO) // +2 distance that can be fuzzy
                        .prefixLength(1)) // just first letter must match
                .build();

        List<Article> articles = elasticsearchTemplate.queryForList(searchQuery, Article.class);


        return articles;
    }
}
