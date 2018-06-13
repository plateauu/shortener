package tech.plateauu.shortener.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@EnableWebSecurity
@Slf4j
public class WebMvcConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        log.debug("Using default configure(HttpSecurity). " +
                "If subclassed this will potentially override subclass configure(HttpSecurity).");

        http
                .authorizeRequests()
                .anyRequest().permitAll()
                .and()
                .formLogin().permitAll()
                .loginPage("/start")
                .and()
                .httpBasic();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();

        UserDetails user = User.withDefaultPasswordEncoder()
                .username("plateauu")
                .password("password")
                .roles("USER")
                .build();

        log.info("Security staring: user {} / password {} with authorities: {}",
                user.getUsername(), user.getPassword(), user.getAuthorities());

        manager.createUser(user);
        return manager;
    }
}
