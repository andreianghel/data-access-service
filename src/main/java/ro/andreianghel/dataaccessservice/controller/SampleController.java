package ro.andreianghel.dataaccessservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ro.andreianghel.dataaccessservice.entity.Article;
import ro.andreianghel.dataaccessservice.service.ArticleService;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ananghel on 2/19/2019
 */
@RestController
public class SampleController {


    private ArticleService articleService;

    private int counter;

    @Autowired
    public SampleController(ArticleService articleService) {
        this.articleService = articleService;
        counter = 0;
    }

    @GetMapping("add")
    void add() {
        Article auxArticle = new Article(counter, "text+" + counter);
        counter++;

        articleService.save(auxArticle);
    }

    @GetMapping("get/{id}")
    String get(@PathVariable int id) {
        Article getOne = articleService.findById(id).get();

        return getOne.getId()+" : "+ getOne.getText();
    }

    @GetMapping("/getAll")
    List<String> getAll() {
        List<String> retList = new ArrayList<>();
        articleService.findAll().forEach(art-> {
            retList.add(art.getText());
        });

        return retList;
    }

    @GetMapping("deleteAll")
    void deleteAll() {
        articleService.deleteAll();
    }
}
