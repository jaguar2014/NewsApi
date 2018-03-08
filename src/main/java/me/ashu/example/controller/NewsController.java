package me.ashu.example.controller;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import me.ashu.example.models.Article;
import me.ashu.example.models.NewsApi;
import me.ashu.example.models.Source;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Controller
public class NewsController {

    @GetMapping("/anonuser")
    public  String  showIndex(Model model){
        RestTemplate restTemplate = new RestTemplate();
        // String sunsign = request.getParameter("sunsign") ;
        NewsApi newsApi = restTemplate.getForObject("https://newsapi.org/v2/top-headlines?country=us&apiKey=5800ef4eec3e4e33821e6fc80e59e70c", NewsApi.class);

        Source sources = restTemplate.getForObject("https://newsapi.org/v2/sources?apiKey=5800ef4eec3e4e33821e6fc80e59e70c", Source.class);


        model.addAttribute("topnewsforanon", newsApi.getArticles());
        model.addAttribute("sources", sources);


        return "topnewsforanonuser";
    }


}
