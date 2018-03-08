package me.ashu.example.controller;

import me.ashu.example.models.AppUser;
import me.ashu.example.models.Profile;
import me.ashu.example.models.Source;
import me.ashu.example.repositories.AppRoleRepository;
import me.ashu.example.repositories.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@Controller
public class MainController {

    @Autowired
    AppUserRepository userRepository;

    @Autowired
    AppRoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @RequestMapping("/")
    public String showIndex()
    {
        return "index";
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

    @GetMapping("/addtopic")
    public String addtopic(Model model) {

        model.addAttribute("source", new Source());

        return "addtopicform";
    }


    @PostMapping("/addtopic")
    public String addtopic(@Valid Source source ,BindingResult result,Model model, Authentication auth, HttpServletRequest request ){

        if (result.hasErrors()) {
            return "addtopicform";
        }

        AppUser appUser = userRepository.findByUsername(auth.getName());
        String[] topics = request.getParameterValues("topics");
        Profile profile = new Profile();

        for (String topic :
                topics) {
            profile.addTopics(topic);
        }
        appUser.setProfile(profile);
        userRepository.save(appUser);

        return "index";
    }


    @GetMapping("/newspertopic")
    public String newsPerTopic(){


        return "userpage";
    }
}
