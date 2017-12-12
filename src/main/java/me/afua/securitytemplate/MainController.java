package me.afua.securitytemplate;

import me.afua.securitytemplate.models.AppUser;
import me.afua.securitytemplate.repositories.AppRoleRepository;
import me.afua.securitytemplate.repositories.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

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

    @RequestMapping("/granteduser")
    public String showUser()
    {
        return "userpage";
    }

    @RequestMapping("/grantedadmin")
    public String showAdmin()
    {
        //You can call methods instead of redirecting
        System.out.println("Admin...");
        return showUser();
    }
}
