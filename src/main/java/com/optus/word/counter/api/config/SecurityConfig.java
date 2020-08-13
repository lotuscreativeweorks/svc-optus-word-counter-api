package com.optus.word.counter.api.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter
{
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception
    {
        httpSecurity.csrf().disable()
                .authorizeRequests().anyRequest().authenticated()
                .and()
                .httpBasic();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder authMngerBuilder)
            throws Exception
    {
        //TODO implement JDBC or LDAP authentication.
        authMngerBuilder.inMemoryAuthentication()
                .withUser("optus").password("{noop}candidates").roles("user").and()
                .withUser("admin").password("{noop}admin").roles("user", "admin");
    }
}
