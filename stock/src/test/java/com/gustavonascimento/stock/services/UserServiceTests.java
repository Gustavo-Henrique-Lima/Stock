package com.gustavonascimento.stock.services;

import com.gustavonascimento.stock.entities.Role;
import com.gustavonascimento.stock.entities.User;
import com.gustavonascimento.stock.repositories.RoleRepository;
import com.gustavonascimento.stock.repositories.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Set;

@ExtendWith(MockitoExtension.class)
class UserServiceTests {

    @InjectMocks
    private UserService service;

    @Mock
    private UserRepository repository;

    @Mock
    private RoleRepository roleRepository;

    private String existingEmail;
    private String nonExistingEmail;

    @BeforeEach
    void setUp() {
        existingEmail = "capitao@vingadores.com";
        nonExistingEmail = "naoexiste@email.com";
    }

    @Test
    void loadUserByUsernameShouldReturnUserDetailsWhenUserExists() {
        Role role = new Role(1L, "ROLE_ADMIN");

        User entity = new User();
        entity.setId(1L);
        entity.setName("Capitão América");
        entity.setEmail(existingEmail);
        entity.setPassword("123456");
        entity.setRoles(Set.of(role));

        Mockito.when(repository.findByEmail(existingEmail))
                .thenReturn(entity);

        UserDetails result = service.loadUserByUsername(existingEmail);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(existingEmail, result.getUsername());

        Mockito.verify(repository).findByEmail(existingEmail);
    }

    @Test
    void loadUserByUsernameShouldThrowUsernameNotFoundExceptionWhenUserDoesNotExist() {
        Mockito.when(repository.findByEmail(nonExistingEmail))
                .thenReturn(null);

        Assertions.assertThrows(
                UsernameNotFoundException.class,
                () -> service.loadUserByUsername(nonExistingEmail)
        );

        Mockito.verify(repository).findByEmail(nonExistingEmail);
    }
}
