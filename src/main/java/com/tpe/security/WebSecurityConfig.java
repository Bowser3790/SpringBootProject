package com.tpe.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) // Security will be enabled in method base
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    /*
    1. AuthenticationManager
    2. AuthenticationProvider
    3. PassEncode
     */

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable(). // Disable Cross Site Request Forgery // by default this is enabled
                authorizeHttpRequests(). // check requests if they are authorized
                antMatchers("/", "/index.html", "/css/*", "/js/*","/register").permitAll(). // giving permission to all of these paths. no authentication or authorization.
                anyRequest().// except other requests
                authenticated().// authenticate
                and().
                httpBasic(); // use basic authorization for this
    }

    // encode our password
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(10); // number between 4-31 (4 will be weakest, 31 strongest and will take time and energy) so normally middle is around 10 is enough.

    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider autoProvider = new DaoAuthenticationProvider();
        autoProvider.setPasswordEncoder(passwordEncoder());
        autoProvider.setUserDetailsService(userDetailsService);
        return autoProvider;
    }
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.authenticationProvider(authenticationProvider());
    }
}

/*
What is an example of cross site request forgery?

suppose you are in your bank app doing some banking and then you receive an email while you are in your bank app,
you open the email on the same browser on a different tab. Hacker could send some data that will copy all the information
from the other tabs which are under 1 browser.  This is the reason why we are disabling this feature.

we are going to add our own security levels.
 */
