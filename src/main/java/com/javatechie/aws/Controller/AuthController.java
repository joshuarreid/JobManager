package com.javatechie.aws.Controller;


import com.javatechie.aws.DAO.RoleRepository;
import com.javatechie.aws.DAO.UserRepository;
import com.javatechie.aws.Model.ERole;
import com.javatechie.aws.Model.LoginRequest;
import com.javatechie.aws.Model.Role;
import com.javatechie.aws.Model.User;
import com.javatechie.aws.Service.AuthService;
import com.javatechie.aws.common.security.*;
import com.javatechie.aws.common.utility.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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
