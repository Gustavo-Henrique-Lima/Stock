package com.gustavonascimento.stock.services;

import com.gustavonascimento.stock.entities.User;
import com.gustavonascimento.stock.repositories.RoleRepository;
import com.gustavonascimento.stock.repositories.UserRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    private static final Logger LOG = LoggerFactory.getLogger(UserService.class);

    private final UserRepository repository;

    private final RoleRepository roleRepository;

    public UserService(UserRepository repository, RoleRepository roleRepository) {
        this.repository = repository;
        this.roleRepository = roleRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User entity = repository.findByEmail(username);
        if (entity == null) {
            throw new UsernameNotFoundException("Usuário e/ou senha inválidos.");
        }
        return entity;
    }
}
