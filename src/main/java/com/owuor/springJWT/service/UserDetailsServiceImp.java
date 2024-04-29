package com.owuor.springJWT.service;

import com.owuor.springJWT.repository.UserResitory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Data
@Builder
public class UserDetailsServiceImp implements UserDetailsService {
    private final UserResitory userResitory;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userResitory.findByUserName(username).orElseThrow(() -> new UsernameNotFoundException("User name not found."));
    }
}
