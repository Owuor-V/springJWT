package com.owuor.springJWT.controller;

import com.owuor.springJWT.model.AuthenticationResponse;
import com.owuor.springJWT.model.User;
import com.owuor.springJWT.service.AuthenticationService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@Data
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity <AuthenticationResponse> register (@RequestBody User user) {
        return ResponseEntity.ok(authenticationService.register(user));
    }
    public ResponseEntity <AuthenticationResponse> login (@RequestBody User user) {
        return ResponseEntity.ok(authenticationService.authenticate(user));
    }
}
