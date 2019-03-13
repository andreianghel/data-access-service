package ro.andreianghel.dataaccessservice.service;

import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.stereotype.Service;
import ro.andreianghel.dataaccessservice.entity.Article;
import ro.andreianghel.dataaccessservice.repository.ArticleRepository;

import java.util.List;

import static org.elasticsearch.index.query.QueryBuilders.matchQuery;
import static org.elasticsearch.index.query.QueryBuilders.wildcardQuery;

/**
 * @author ananghel on 2/19/2019
 */
@EnableElasticsearchRepositories(basePackages = "ro.andreianghel.dataaccessservice.repository")
@Service
public class ArticleService {


    private final ArticleRepository articleRepository;

    @Autowired
    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public Article save(Article article) {
        return articleRepository.save(article);
    }


  /*  public Optional<Article> findOne(int id) {
        return articleRepository.findById(id);
    }
*/

    public Iterable<Article> findAll() {
        return articleRepository.findAll();
    }

    public Iterable<Article> findAllOrderByCreationDate() {
        return articleRepository.findAllByOrderByCreationDateDesc();
    }

    public long count() {
        return articleRepository.count();
    }

    public void delete(Article article) {
        articleRepository.delete(article);
    }

    public void deleteAll() {
        articleRepository.deleteAll();
    }

    public List<Article> getAllOrdered(ElasticsearchOperations elasticsearchTemplate) {

        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(wildcardQuery("text", "*"))
                .withSort(SortBuilders.fieldSort("creationDate").order(SortOrder.DESC))
                .build();

        List<Article> list = elasticsearchTemplate.queryForList(searchQuery, Article.class);
        System.out.println("begin-----------------");
        list.forEach(x -> System.out.print(x + " "));
        System.out.println("\ndone------------------");

        return list;
    }

    public List<Article> fuzzySearch(ElasticsearchTemplate elasticsearchTemplate, String word) {
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
