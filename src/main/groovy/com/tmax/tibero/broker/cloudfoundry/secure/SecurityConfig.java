package com.tmax.tibero.broker.cloudfoundry.secure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.yaml.snakeyaml.Yaml;

import java.util.Map;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic().and().
                 authorizeRequests()
                .antMatchers("/**").hasRole("USER")
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().csrf().disable();
    }
    @Autowired
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        Yaml yaml = new Yaml();
        Map<String,String> loginData = (Map<String,String>) yaml.load(this.getClass().getClassLoader().getResourceAsStream("secure.yml"));
        auth.inMemoryAuthentication().withUser(loginData.get("id")).password(loginData.get("password")).roles(loginData.get("role"));
    }
}


