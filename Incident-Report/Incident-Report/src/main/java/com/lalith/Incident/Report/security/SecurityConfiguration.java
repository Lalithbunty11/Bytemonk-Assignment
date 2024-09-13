package com.lalith.Incident.Report.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {

    @Bean
    public InMemoryUserDetailsManager userDetailsManager(){
        UserDetails employee = User.builder()
                .username("lalith")
                .password("{noop}fun123")
                .roles("EMPLOYEE")
                .build();

        UserDetails manager = User.builder()
                .username("nithish")
                .password("{noop}fun123")
                .roles("EMPLOYEE", "MANAGER")
                .build();

        UserDetails admin = User.builder()
                .username("sagar")
                .password("{noop}fun123")
                .roles("EMPLOYEE", "MANAGER", "ADMIN")
                .build();

        return new InMemoryUserDetailsManager(employee, manager, admin);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.authorizeHttpRequests(
                configurer ->
                configurer
                        .requestMatchers(HttpMethod.GET, "/api/incidents").hasRole("EMPLOYEE")
                        .requestMatchers(HttpMethod.GET, "/api/incidents/**").hasRole("EMPLOYEE")
                        .requestMatchers(HttpMethod.PUT, "/api/incidents").hasRole("MANAGER")
                        .requestMatchers(HttpMethod.POST, "/api/incidents").hasRole("MANAGER")
                        .requestMatchers(HttpMethod.DELETE, "/api/incidents/**").hasRole("ADMIN")
        );

        //use basic authentication
        http.httpBasic(Customizer.withDefaults());

        return http.build();
    }

}
