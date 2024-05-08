package com.example.ApiRest.config;

import com.example.ApiRest.entities.Phone;
import com.example.ApiRest.entities.Role;
import com.example.ApiRest.entities.User;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.ApiRest.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;

@Configuration
@EnableWebSecurity
public class ApplicationConfig {

    @Autowired
    private UserRepository userRepository;

    @Bean
    public UserDetailsService userDetailsService(){
        return username -> userRepository.findByEmail(username)
                .orElseThrow(()-> new UsernameNotFoundException("User not found"));
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder  passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    CommandLineRunner initDataBase(UserRepository userRepository) {
        return args -> {
            Phone phone = Phone.builder()
                    .number("2345234")
                    .cityCode("602")
                    .country("CO")
                    .build();
            User user = User.builder()
                    .firstname("admin")
                    .lastname("user")
                    .role(Role.ADMIN)
                    .email("admin@test.com")
                    .phones(new ArrayList<>())
                    .password("$2a$10$kW4x5fPED6ljR0kRdk611e9XjM3AAu34JmfanlySpjgGWlW2wiWDO")
                    .build();
            phone.setUser(user);
            user.getPhones().add(phone);
            userRepository.save(user);
        };
    }
}
