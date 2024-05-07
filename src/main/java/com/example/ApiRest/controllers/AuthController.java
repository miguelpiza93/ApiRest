package com.example.ApiRest.controllers;

import com.example.ApiRest.config.JwtService;
import com.example.ApiRest.dto.auth.authenticate.AuthResponse;
import com.example.ApiRest.dto.auth.register.RegisterRequest;
import com.example.ApiRest.dto.auth.register.RegisterResponse;
import com.example.ApiRest.dto.auth.authenticate.AuthRequest;
import com.example.ApiRest.entities.User;
import com.example.ApiRest.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Value("${emailRegex}")
    private String emailRegex;

    @Value("${passwordRegex}")
    private String passwordRegex;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate(@RequestBody AuthRequest authRequest) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
        );

        User user = this.userService.findUserByEmail(authRequest.getUsername())
                .orElseThrow();
        String token = jwtService.generateToken(user);
        return ResponseEntity.ok(new AuthResponse(token));
    }

    @PostMapping("/register")
    public ResponseEntity<Object> registerUser(@RequestBody RegisterRequest body) throws Exception {
        validateRequest(body);

        boolean found = userService.findUserByEmail(body.getEmail()).isPresent();
        if(found){
            throw new Exception("El correo ya registrado");
        }
        User user = body.toUserEntity();
        user.setPassword(passwordEncoder.encode(body.getPassword()));
        String token = jwtService.generateToken(user);
        userService.addUser(user);
        return new ResponseEntity<>(new RegisterResponse(token), HttpStatus.CREATED);
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
