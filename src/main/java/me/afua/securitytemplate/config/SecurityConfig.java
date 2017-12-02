package me.afua.securitytemplate.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(HttpSecurity http) throws Exception
    {
        http.authorizeRequests()
                .anyRequest().authenticated()
                .antMatchers("/").permitAll()
                .antMatchers("/granteduser").access("hasAuthority('USER')")
                .antMatchers("/grantedadmin").access("hasAuthority('ADMIN')")

                .and()
                .formLogin()
                .and()
                .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/login").permitAll();
    }

    @Override
    public void configure (AuthenticationManagerBuilder auth) throws Exception
    {
        auth.inMemoryAuthentication().withUser("user").password("password").authorities("USER")
                .and().withUser("admin").password("administrator").authorities("ADMIN");
    }
}
