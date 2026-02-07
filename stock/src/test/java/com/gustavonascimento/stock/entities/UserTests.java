package com.gustavonascimento.stock.entities;

import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

class UserTests {

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Test
    void userShouldHaveCorrectStructure() {

        User entity = new User();
        entity.setId(1L);
        entity.setName("Name");
        entity.setEmail("email@gmail.com");
        entity.setPassword(encoder.encode("123"));
        entity.setStatus(true);
        entity.getRoles().add(new Role(1L, "ROLE_ADMIN"));

        Assertions.assertNotNull(entity.getClass());
        Assertions.assertEquals(1, entity.getId());
        Assertions.assertNotNull(entity.getName());
        Assertions.assertEquals("Name", entity.getName());
        Assertions.assertNotNull(entity.getEmail());
        Assertions.assertNotNull(entity.getPassword());
        Assertions.assertEquals("email@gmail.com", entity.getEmail());
        Assertions.assertTrue(entity.isStatus());
        Assertions.assertEquals(1, entity.getRoles().size());
    }

    @Test
    void userConstructorShouldInitializeFieldsCorrectly() {
        User user = new User(1L, "John Doe", "johndoe@example.com", "password");
        Assertions.assertEquals(1L, user.getId());
        Assertions.assertEquals("John Doe", user.getName());
        Assertions.assertEquals("johndoe@example.com", user.getEmail());
        Assertions.assertEquals("password", user.getPassword());
        Assertions.assertTrue(user.isStatus());
    }

    @Test
    void hasRoleShouldReturnTrueIfUserHasRole() {
        Role role = new Role(1L, "ROLE_ADMIN");
        User user = new User();
        user.setRoles(Set.of(role));

        Assertions.assertTrue(user.hasRole("ROLE_ADMIN"));
    }

    @Test
    void hasRoleShouldReturnFalseIfUserDoesNotHaveRole() {
        Role role = new Role(1L, "ROLE_USER");
        User user = new User();
        user.setRoles(Set.of(role));

        Assertions.assertFalse(user.hasRole("ROLE_ADMIN"));
    }

    @Test
    void getAuthoritiesShouldReturnGrantedAuthorities() {
        Role role1 = new Role(1L, "ROLE_ADMIN");
        Role role2 = new Role(2L, "ROLE_USER");
        User user = new User();
        user.setRoles(Set.of(role1, role2));

        Set<String> authorities = user.getAuthorities().stream().map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());

        Assertions.assertTrue(authorities.contains("ROLE_ADMIN"));
        Assertions.assertTrue(authorities.contains("ROLE_USER"));
    }

    @Test
    void equalsShouldReturnTrueWhenIdIsEqual() {
        User user1 = new User(1L, "John Doe", "johndoe@example.com", "password");
        User user2 = new User(1L, "Jane Doe", "janedoe@example.com", "password");

        Assertions.assertEquals(user1, user2);
    }

    @Test
    void equalsShouldReturnFalseWhenIdIsDifferent() {
        User user1 = new User(1L, "John Doe", "johndoe@example.com", "password");
        User user2 = new User(2L, "Jane Doe", "janedoe@example.com", "password");

        Assertions.assertNotEquals(user1, user2);
    }

    @Test
    void equalsShouldReturnFalseWhenComparingWithNull() {
        User user = new User(1L, "John Doe", "johndoe@example.com", "password");

        Assertions.assertNotEquals(null, user);
    }

    @Test
    void equalsShouldReturnFalseWhenComparingWithDifferentClass() {
        User user = new User(1L, "John Doe", "johndoe@example.com", "password");
        String differentClassObject = "Different Class";

        Assertions.assertNotEquals(differentClassObject, user);
    }

    @Test
    void isAccountNonExpiredShouldReturnFalse() {
        User user = new User();
        Assertions.assertFalse(user.isAccountNonExpired());
    }

    @Test
    void isAccountNonLockedShouldReturnFalse() {
        User user = new User();
        Assertions.assertFalse(user.isAccountNonLocked());
    }

    @Test
    void isCredentialsNonExpiredShouldReturnFalse() {
        User user = new User();
        Assertions.assertFalse(user.isCredentialsNonExpired());
    }

    @Test
    void isEnabledShouldReturnFalse() {
        User user = new User();
        Assertions.assertFalse(user.isEnabled());
    }
}