package me.ashu.example.controller;


import me.ashu.example.models.NewsPublishers;
import me.ashu.example.models.Source;
import me.ashu.example.models.Topheadline;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class NewsController {

    @GetMapping("/anonuser")
    public  String  showIndex(Model model){
        RestTemplate restTemplate = new RestTemplate();
        // String sunsign = request.getParameter("sunsign") ;
        Topheadline topheadline = restTemplate.getForObject("https://newsapi.org/v2/top-headlines?country=us&apiKey=5800ef4eec3e4e33821e6fc80e59e70c", Topheadline.class);
        model.addAttribute("topheadlines", topheadline);
      //
        NewsPublishers newsPublishers = restTemplate.getForObject("https://newsapi.org/v2/sources?apiKey=5800ef4eec3e4e33821e6fc80e59e70c", NewsPublishers.class);

       List<Source> sources =  newsPublishers.getSources();

       Set<String> categories = new HashSet<>();


        for (Source source :
                sources) {
            categories.add(source.getCategory());
        }




       model.addAttribute("newsPublishers", newsPublishers.getSources());



        return "topnewsforanonuser";
    }


}
