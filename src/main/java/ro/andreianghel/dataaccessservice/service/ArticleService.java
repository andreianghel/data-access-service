package ro.andreianghel.dataaccessservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.stereotype.Service;
import ro.andreianghel.dataaccessservice.entity.Article;
import ro.andreianghel.dataaccessservice.repository.ArticleRepository;

import java.util.Optional;

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
        return articleRepository.findAllOrderByCreationDate();
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
}
