package me.afua.securitytemplate.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {
    @Override
    public void run(String... strings) throws Exception {
        System.out.println("Loading data into the application");
    }
}
