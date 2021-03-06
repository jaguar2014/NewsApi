package me.ashu.example.controller;

import me.ashu.example.models.*;
import me.ashu.example.repositories.AppRoleRepository;
import me.ashu.example.repositories.AppUserRepository;
import me.ashu.example.repositories.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class MainController {

    @Autowired
    AppUserRepository userRepository;

    @Autowired
    AppRoleRepository roleRepository;

    @Autowired
    ProfileRepository profileRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @RequestMapping("/")
    public String showIndex()
    {
        return "landing";
    }

    @GetMapping("/login")
    public String showLogin(){
        return "login";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model)
    {
        model.addAttribute("newuser",new AppUser());
        return "register";
    }

    @PostMapping("/register")
    public String registerNewUser(@Valid @ModelAttribute("newuser") AppUser user, BindingResult result)
    {
        String thePassword = user.getPassword();
        if(result.hasErrors())
        {
            return "register";
        }
        user.addRole(roleRepository.findByRole("USER"));
        user.setPassword(passwordEncoder.encode(thePassword));
        userRepository.save(user);
        return "redirect:/login";

    }



    @GetMapping("/anonuser")
    public  String  showIndex(Model model){
        RestTemplate restTemplate = new RestTemplate();

        Topheadline topheadline = restTemplate.getForObject("https://newsapi.org/v2/top-headlines?country=us&apiKey=5800ef4eec3e4e33821e6fc80e59e70c", Topheadline.class);



        model.addAttribute("topheadlines", topheadline.getArticles());




        return "topnewsforanonuser";
    }


    @GetMapping("/addtoprofile")
    public String addCategory(Model model) {

        RestTemplate restTemplate = new RestTemplate();

        NewsPublishers newsPublishers = restTemplate.getForObject("https://newsapi.org/v2/sources?apiKey=5800ef4eec3e4e33821e6fc80e59e70c", NewsPublishers.class);

        List<Source> sources =  newsPublishers.getSources();

        Set<String> categories = new HashSet<>();


        for (Source source :
                sources) {
            if(source.getCategory()!=null) {
                categories.add(source.getCategory());
            }
        }


        model.addAttribute("categories", categories);


        model.addAttribute("profile", new Profile());

        return "addtoprofile";
    }




    @PostMapping("/addtoprofile")
    public String addcategory(@Valid Profile profile, BindingResult result, Model model, Authentication auth, HttpServletRequest request ){

        if (result.hasErrors()) {
            return "addtoprofile";
        }

        AppUser appUser = userRepository.findByUsername(auth.getName());
        profile.addAppUser(appUser);
        profileRepository.save(profile);



        return "redirect:/newspertopic";
    }


    @GetMapping("/newspercategory")
    public String showNewsPerCategory(Model model,Authentication auth){



        AppUser appUser = userRepository.findByUsername(auth.getName());

        List<Profile> catForUser = profileRepository.findByAppUsersIn(appUser);


        RestTemplate restTemplate = new RestTemplate();

        NewsPublishers newsPublishers = restTemplate.getForObject("https://newsapi.org/v2/sources?apiKey=5800ef4eec3e4e33821e6fc80e59e70c", NewsPublishers.class);

        List<Source> sources =  newsPublishers.getSources();




        List<Source> sourceMacthingProfile = new ArrayList<>();



        Set<String> newsUrl = new HashSet<>();


        for (Source source :
                sources) {

            for (Profile profile :
                    catForUser) {

                if(source.getCategory().equals(profile.getCategory())){

                    System.out.println(source.getCategory());

                    sourceMacthingProfile.add(source);

                    newsUrl.add(source.getUrl());

                }

            }

        }


        model.addAttribute("catForUser", catForUser);

        model.addAttribute("sourceMacthingProfile", sourceMacthingProfile);






        return "newspercategory";

    }

    @GetMapping("/addtopic")
    public String addtopic(Model model) {

        model.addAttribute("profile", new Profile());

        return "addtopic";
    }

    @PostMapping("/addtopic")
    public String addtopic(@Valid Profile profile, BindingResult result, Authentication auth){

        if (result.hasErrors()) {
            return "addtopic";
        }


        AppUser appUser = userRepository.findByUsername(auth.getName());
        profile.addAppUser(appUser);
        profileRepository.save(profile);

        return "redirect:/newspertopic";
    }





    @GetMapping("/newspertopic")
    public String newsPerTopic(Model model, Authentication auth){






        AppUser appUser = userRepository.findByUsername(auth.getName());

        List<Profile> catForUser = profileRepository.findByAppUsersIn(appUser);


        RestTemplate restTemplate = new RestTemplate();

        NewsPublishers newsPublishers = restTemplate.getForObject("https://newsapi.org/v2/sources?apiKey=5800ef4eec3e4e33821e6fc80e59e70c", NewsPublishers.class);

        List<Source> sources =  newsPublishers.getSources();

        List<Source> sourceMacthingProfile = new ArrayList<>();


        Set<String> newsUrl = new HashSet<>();


        for (Source source :
                sources) {

            for (Profile profile :
                    catForUser) {

               if(source.getCategory().equals(profile.getCategory())){

                   System.out.println(source.getCategory());

                   sourceMacthingProfile.add(source);

                   newsUrl.add(source.getUrl());

               }

       }

        }


        model.addAttribute("catForUser", catForUser);

        model.addAttribute("sourceMacthingProfile", sourceMacthingProfile);


        Set<String> topics = new HashSet<>();



        List<Profile> profiles = profileRepository.findByAppUsersIn(appUser);

        for (Profile p :
                profiles) {
            System.out.println(p.getTopic());
            topics.add(p.getTopic());
        }


        Topheadline topheadline;
       restTemplate = new RestTemplate();

        List<Topheadline> topheadlines = new ArrayList<>(); //hold collection of headlines


        for (String topic :
                topics) {
            topheadlines.add(restTemplate.getForObject("https://newsapi.org/v2/top-headlines?q="+topic+"&apiKey=5800ef4eec3e4e33821e6fc80e59e70c", Topheadline.class));
        }




        model.addAttribute("topheadlines", topheadlines);


        return "newspertopic";
    }


    @RequestMapping("/removecategory/{id}")
    public String delAddressBook(@PathVariable("id") long id) {
        profileRepository.delete(id);
        return "redirect:/newspertopic";
    }

}
