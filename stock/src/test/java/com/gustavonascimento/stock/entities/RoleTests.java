package com.gustavonascimento.stock.entities;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class RoleTests {

    @Test
    void roleShouldHaveCorrectStructure() {

        Role entity = new Role();
        entity.setId(1L);
        entity.setAuthority("ROLE_MEMBER");

        Assertions.assertNotNull(entity.getClass());
        Assertions.assertNotNull(entity.getId());
        Assertions.assertEquals("ROLE_MEMBER", entity.getAuthority());
        Assertions.assertNotNull(entity.getAuthority());
        Assertions.assertEquals(1L, entity.getId());
    }


    @Test
    void equalsShouldReturnTrueWhenComparingSameObject() {
        Role role = new Role();
        role.setId(1L);
        role.setAuthority("ROLE_ADMIN");

        Assertions.assertEquals(role, role);
    }

    @Test
    void equalsShouldReturnFalseWhenComparingWithNull() {
        Role role = new Role();
        role.setId(1L);
        role.setAuthority("ROLE_ADMIN");

        Assertions.assertNotEquals(null, role);
    }

    @Test
    void equalsShouldReturnFalseWhenComparingDifferentClass() {
        Role role = new Role();
        role.setId(1L);
        role.setAuthority("ROLE_ADMIN");

        String differentClassObject = "Different Class Object";
        Assertions.assertNotEquals(differentClassObject, role);
    }

    @Test
    void equalsShouldReturnFalseWhenIdOrAuthorityAreDifferent() {
        Role role1 = new Role();
        role1.setId(1L);
        role1.setAuthority("ROLE_ADMIN");

        Role role2 = new Role();
        role2.setId(2L);
        role2.setAuthority("ROLE_ADMIN");

        Role role3 = new Role();
        role3.setId(1L);
        role3.setAuthority("ROLE_USER");

        Assertions.assertNotEquals(role1, role2);
        Assertions.assertNotEquals(role1, role3);
    }

    @Test
    void equalsShouldReturnTrueWhenIdAndAuthorityAreEqual() {
        Role role1 = new Role();
        role1.setId(1L);
        role1.setAuthority("ROLE_ADMIN");

        Role role2 = new Role();
        role2.setId(1L);
        role2.setAuthority("ROLE_ADMIN");

        Assertions.assertEquals(role1, role2);
    }
}