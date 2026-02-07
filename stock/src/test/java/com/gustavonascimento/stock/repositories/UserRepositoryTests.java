package com.gustavonascimento.stock.repositories;

import com.gustavonascimento.stock.entities.Role;
import com.gustavonascimento.stock.entities.User;
import com.gustavonascimento.stock.factories.RoleFactory;
import com.gustavonascimento.stock.factories.UserFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

@DataJpaTest
class UserRepositoryTests {

    @Autowired
    private UserRepository repository;

    @Autowired
    private RoleRepository roleRepository;

    private User user;
    private Role role;
    private String nonExistingEmail;

    @BeforeEach
    void setUp() {
        user = UserFactory.createUser();
        role = RoleFactory.createAdminRole();
        nonExistingEmail = "naoexiste@email.com";
    }

    @Test
    void findByEmailShouldReturnNullWhenEmailNonExists() {
        User result = repository.findByEmail(nonExistingEmail);

        Assertions.assertNull(result);
    }

    @Test
    void findByEmailShouldReturnNonNullWhenEmailExists() {
        roleRepository.save(role);
        user.getRoles().add(role);
        repository.save(user);

        User result = repository.findByEmail("capitão@vingadores.com");

        Assertions.assertNotNull(result);
    }

}
