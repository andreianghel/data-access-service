package ro.andreianghel.dataaccessservice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;
import ro.andreianghel.dataaccessservice.entity.Article;

import java.util.List;

/**
 * @author ananghel on 2/19/2019
 */
@Repository
public interface ArticleRepository extends ElasticsearchRepository<Article, Integer> {


 }
