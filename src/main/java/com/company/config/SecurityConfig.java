package com.company.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser("admin").password("{noop}123admin").roles("admin")
                .and().withUser("user").password("{noop}123user").roles("user");
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests().antMatchers("/card/**").permitAll().
                antMatchers("/profile/**").hasRole("BANK_ROLE").
                antMatchers("/Transaction/create/**").hasAnyRole("BANK_ROLE", "PAYMENT_ROLE").
                antMatchers("/Transaction/getTransactionByCardId/**").hasAnyRole("BANK_ROLE","PAYMENT_ROLE").
                antMatchers("/Transaction/getAllTransaction/**").hasRole("admin").
                antMatchers("/Transaction/getAllTransactionByProfileId/**").hasRole("BANK_ROLE").
                anyRequest().authenticated().and().httpBasic();

    }

}
