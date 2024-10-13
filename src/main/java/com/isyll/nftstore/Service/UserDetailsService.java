package com.isyll.nftstore.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.isyll.nftstore.Model.User;
import com.isyll.nftstore.Repository.UserRepository;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class UserDetailsService
                implements org.springframework.security.core.userdetails.UserDetailsService {

        @Autowired
        private UserRepository userRepository;

        @Override
        public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

                User user = userRepository.findByUsername(username).orElseThrow(
                                () -> new UsernameNotFoundException("User not exists by Username or Email"));

                Set<GrantedAuthority> authorities = user.getRoles().stream()
                                .map((role) -> new SimpleGrantedAuthority(role.getName()))
                                .collect(Collectors.toSet());

                return new org.springframework.security.core.userdetails.User(
                                username,
                                user.getPassword(),
                                authorities);
        }

}