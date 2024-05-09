package com.example.ApiRest.services.authentication;

import com.example.ApiRest.config.JwtService;
import com.example.ApiRest.dto.auth.authenticate.AuthRequest;
import com.example.ApiRest.dto.auth.register.RegisterRequest;
import com.example.ApiRest.entities.User;
import com.example.ApiRest.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    @Value("${emailRegex}")
    private String emailRegex;

    @Value("${passwordRegex}")
    private String passwordRegex;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public String authenticate(AuthRequest authRequest) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
        );
        User user = this.userRepository.findByEmail(authRequest.getUsername()).orElseThrow();
        return jwtService.generateToken(user);
    }

    @Override
    public String register(RegisterRequest registerRequest) throws Exception {
        validateRequest(registerRequest);
        boolean found = this.userRepository.findByEmail(registerRequest.getEmail()).isPresent();
        if(found){
            throw new Exception("El correo ya está registrado");
        }
        User user = registerRequest.toUserEntity();
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        String token = jwtService.generateToken(user);
        this.userRepository.save(user);
        return token;
    }

    private void validateRequest(RegisterRequest body) throws Exception {
        if(body.getEmail() == null){
            throw new Exception("El email es un campo requerido");
        }

        if(body.getPassword() == null){
            throw new Exception("La contraseña es un campo requerido");
        }

        Pattern pattern = Pattern.compile(emailRegex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(body.getEmail());
        if(!matcher.find()){
            throw new Exception("El correo no es válido");
        }

        pattern = Pattern.compile(passwordRegex, Pattern.CASE_INSENSITIVE);
        matcher = pattern.matcher(body.getPassword());
        if(!matcher.find()){
            throw new Exception("La contraseña no cumple con los requerimientos");
        }
    }
}
