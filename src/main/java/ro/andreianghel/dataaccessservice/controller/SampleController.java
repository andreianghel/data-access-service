package ro.andreianghel.dataaccessservice.controller;

import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ro.andreianghel.dataaccessservice.entity.Article;
import ro.andreianghel.dataaccessservice.service.ArticleService;

import java.util.ArrayList;
import java.util.List;

import static org.elasticsearch.index.query.QueryBuilders.*;

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
        counter = 10;
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
        Article auxArticle = new Article("text: " + text);
        counter++;

        System.out.println("added one");
        articleService.save(auxArticle);
    }

    @GetMapping("getAll")
    List<String> getAll() {
        List<String> retList = new ArrayList<>();
        articleService.findAll().forEach(art -> {
            retList.add(art.getId() + " | " + art.getText() + " | " + art.getCreationDate());
        });

        return retList;
    }

    @GetMapping("getAllSorted")
    List<String> getAllSorted() {
        List<String> retList = new ArrayList<>();
        articleService.findAllOrderByCreationDate().forEach(art -> {
            retList.add(art.getId() + " | " + art.getText() + " | " + art.getCreationDate());
        });

        return retList;
    }

    @GetMapping("getAllOrdered")
    List<Article> getAllOrdered() {
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(wildcardQuery("text", "*"))
                .withSort(SortBuilders.fieldSort("creationDate").order(SortOrder.DESC))
                .build();

        List<Article> list = elasticsearchTemplate.queryForList(searchQuery, Article.class);
        System.out.println("begin-----------------");
        list.forEach(x -> System.out.print(x + " "));
        System.out.println("done------------------");
        return list;
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
                .withSort(SortBuilders.fieldSort("creationDate").order(SortOrder.DESC))
                .build();


        List<Article> articles = elasticsearchTemplate.queryForList(searchQuery, Article.class);


        return articles;
    }
}
