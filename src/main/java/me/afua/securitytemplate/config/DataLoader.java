package me.afua.securitytemplate.config;

import me.afua.securitytemplate.models.AppRole;
import me.afua.securitytemplate.models.AppUser;
import me.afua.securitytemplate.repositories.AppRoleRepository;
import me.afua.securitytemplate.repositories.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {
    @Autowired
    AppUserRepository userRepository;

    @Autowired
    AppRoleRepository roleRepository;

    @Override
    public void run(String... strings) throws Exception {



        System.out.println("Loading data into the application");

        System.out.println("Loading roles into the application");


        //Create new roles for the database
        AppRole aRole = new AppRole();
        aRole.setRole("ADMIN");
        roleRepository.save(aRole);

        aRole = new AppRole();
        aRole.setRole("USER");
        roleRepository.save(aRole);


        //Create new users for the database
        System.out.println("Loading users into the application");
        AppUser user = new AppUser();
        user.setPassword("password");
        user.setUsername("newuser");
        user.addRole(roleRepository.findByRole("ADMIN"));
        user.addRole(roleRepository.findByRole("USER"));
        userRepository.save(user);


        user = new AppUser();
        user.setPassword("password");
        user.setUsername("adminuser");
        user.addRole(roleRepository.findByRole("ADMIN"));
        userRepository.save(user);

        user = new AppUser();
        user.setPassword("password");
        user.setUsername("ordinaryuser");
        user.addRole(roleRepository.findByRole("USER"));
        userRepository.save(user);





    }
}
