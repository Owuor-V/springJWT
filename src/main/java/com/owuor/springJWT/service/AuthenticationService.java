package com.owuor.springJWT.service;

import com.owuor.springJWT.model.AuthenticationResponse;
import com.owuor.springJWT.model.Role;
import com.owuor.springJWT.model.User;
import com.owuor.springJWT.repository.UserResitory;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthenticationService {
    private final UserResitory userResitory;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register (User user) {
        user.setFirstName(user.getFirstName());
        user.setLastName(user.getLastName());
        user.setUserName(user.getUsername());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.USER);

        user = userResitory.save(user);

        String token = jwtService.generateToken(user);
        return new AuthenticationResponse(token);
    }
    public AuthenticationResponse authenticate(User user) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        user.getUsername(),
                        user.getPassword()
                )
        );
        User user1 = userResitory.findByUserName(user.getUsername()).orElseThrow();
        String token = jwtService.generateToken(user);
        return new AuthenticationResponse(token);
    }
}
