package com.isyll.nftstore.Service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.isyll.nftstore.Config.JwtTokenProvider;
import com.isyll.nftstore.Dto.LoginDto;
import com.isyll.nftstore.Exception.UniqueConstraintViolationException;
import com.isyll.nftstore.Model.User;
import com.isyll.nftstore.Repository.UserRepository;

@Service
public class AuthService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    public String login(LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getUsername(),
                loginDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtTokenProvider.generateToken(authentication);

        return token;
    }

    public User register(User user) throws UniqueConstraintViolationException {

        Optional<User> result = userRepository.findByEmail(user.getEmail());

        if (result.isPresent()) {
            throw new UniqueConstraintViolationException("Email address '" + user.getEmail() + "' already exists.");
        }

        result = userRepository.findByUsername(user.getUsername());

        if (result.isPresent()) {
            throw new UniqueConstraintViolationException("Username '" + user.getUsername() + "' already exists.");
        }

        return userRepository.save(user);
    }
}
