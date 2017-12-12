package me.afua.securitytemplate.config;

import me.afua.securitytemplate.repositories.AppUserRepository;
import me.afua.securitytemplate.services.SSUDS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    AppUserRepository userRepository;

    @Bean
    public UserDetailsService userDetailsServiceBean() throws Exception {
        return new SSUDS(userRepository);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception
    {
        //Restricts access to routes
        http.authorizeRequests()
                .antMatchers("/","/register").permitAll()
                .antMatchers("/granteduser").access("hasAuthority('USER')")
                .antMatchers("/grantedadmin").access("hasAuthority('ADMIN')")
                //Indicate all of the permitted routes above, before the line below
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .and()
                .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/login").permitAll();
    }

    @Override
    public void configure (AuthenticationManagerBuilder auth) throws Exception
    {
        //Allows database authentication
        auth.userDetailsService(userDetailsServiceBean()).passwordEncoder(encoder());
    }
}
