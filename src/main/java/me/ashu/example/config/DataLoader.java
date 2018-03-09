package me.ashu.example.config;

import me.ashu.example.models.AppRole;
import me.ashu.example.models.AppUser;
import me.ashu.example.repositories.AppRoleRepository;
import me.ashu.example.repositories.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    //Include repositories for each item that will be saved in the data loader
    @Autowired
    AppUserRepository userRepository;

    @Autowired
    AppRoleRepository roleRepository;

    //Include a password encododer for data (using bcrypt hashing algorithm, but can use others)
    @Autowired
    private PasswordEncoder passwordEncoder;

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
        user.setPassword(passwordEncoder.encode("password"));
        user.setUsername("newuser");
        user.addRole(roleRepository.findByRole("ADMIN"));
        user.addRole(roleRepository.findByRole("USER"));
        userRepository.save(user);

        user = new AppUser();
        user.setPassword(passwordEncoder.encode("password"));
        user.setUsername("adminuser");
        user.addRole(roleRepository.findByRole("ADMIN"));
        userRepository.save(user);

        user = new AppUser();
        user.setPassword(passwordEncoder.encode("password"));
        user.setUsername("ordinaryuser");
        user.addRole(roleRepository.findByRole("USER"));
        userRepository.save(user);
    }
}
