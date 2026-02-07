package com.gustavonascimento.stock.factories;

import com.gustavonascimento.stock.entities.Role;

public class RoleFactory {

    public static Role createUserRole() {
        Role role = new Role();
        role.setAuthority("ROLE_USER");
        return role;
    }

    public static Role createAdminRole() {
        Role role = new Role();
        role.setAuthority("ROLE_ADMIN");
        return role;
    }
}
