package com.javatechie.aws.Controller;


import com.javatechie.aws.Model.LoginRequest;
import com.javatechie.aws.Service.AuthService;
import com.javatechie.aws.common.security.SignupRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class AuthController {

    @Autowired
    AuthService authService;

    @PostMapping("/auth/signin")
    public ResponseEntity<Object> authenticateUser(@RequestBody LoginRequest loginRequest) {
        return authService.authenticateUser(loginRequest);
    }

    @PostMapping("/auth/signup")
    public ResponseEntity<Object> registerUser(@RequestBody SignupRequest signUpRequest) {
        return authService.registerUser(signUpRequest);
    }
}
